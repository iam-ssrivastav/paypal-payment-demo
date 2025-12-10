package com.shivamsrivastav.payment.service;

import com.paypal.api.payments.Payment;
import com.shivamsrivastav.payment.dto.request.*;
import com.shivamsrivastav.payment.dto.response.PaymentResponse;
import com.shivamsrivastav.payment.entity.Order;
import com.shivamsrivastav.payment.entity.enums.OrderStatus;
import com.shivamsrivastav.payment.entity.enums.PaymentIntent;
import com.shivamsrivastav.payment.entity.enums.PaymentStatus;
import com.shivamsrivastav.payment.exception.PaymentException;
import com.shivamsrivastav.payment.repository.OrderRepository;
import com.shivamsrivastav.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Payment Service
 * 
 * Business logic layer for payment operations.
 * Orchestrates between PayPal service and database.
 * 
 * @author Shivam Srivastav
 */
@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PayPalService payPalService;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PayPalService payPalService, PaymentRepository paymentRepository,
            OrderRepository orderRepository) {
        this.payPalService = payPalService;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // ==================== CREATE PAYMENT ====================

    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest request) {
        log.info("Creating payment: amount={}, currency={}",
                request.getAmount(), request.getCurrency());

        // 1. Check idempotency
        if (request.getIdempotencyKey() != null) {
            var existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());
            if (existingPayment.isPresent()) {
                log.info("Idempotency hit: returning existing payment id={}",
                        existingPayment.get().getId());
                return toResponse(existingPayment.get(), "Payment already exists (idempotency)");
            }
        }

        // 2. Create payment with PayPal
        Payment paypalPayment = payPalService.createPayment(request);
        String approvalUrl = payPalService.getApprovalUrl(paypalPayment);

        // 3. Get order if specified
        Order order = null;
        if (request.getOrderId() != null) {
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new PaymentException("Order not found: " + request.getOrderId()));
        }

        // 4. Save payment to database
        com.shivamsrivastav.payment.entity.Payment payment = com.shivamsrivastav.payment.entity.Payment.builder()
                .paypalPaymentId(paypalPayment.getId())
                .order(order)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .description(request.getDescription())
                .paymentIntent(request.getPaymentIntent())
                .status(PaymentStatus.CREATED)
                .idempotencyKey(request.getIdempotencyKey() != null
                        ? request.getIdempotencyKey()
                        : UUID.randomUUID().toString())
                .approvalUrl(approvalUrl)
                .build();

        payment = paymentRepository.save(payment);

        log.info("Payment created successfully: id={}, paypalId={}",
                payment.getId(), payment.getPaypalPaymentId());

        return toResponse(payment, "Payment created. Redirect user to approvalUrl to complete payment.");
    }

    // ==================== EXECUTE PAYMENT ====================

    @Transactional
    public PaymentResponse executePayment(ExecutePaymentRequest request) {
        log.info("Executing payment: paymentId={}, payerId={}",
                request.getPaymentId(), request.getPayerId());

        // 1. Find payment in our database
        com.shivamsrivastav.payment.entity.Payment payment = paymentRepository
                .findByPaypalPaymentId(request.getPaymentId())
                .orElseThrow(() -> new PaymentException("Payment not found: " + request.getPaymentId()));

        // 2. Execute with PayPal
        Payment executedPayment = payPalService.executePayment(
                request.getPaymentId(), request.getPayerId());

        // 3. Update our database
        payment.setPaypalPayerId(request.getPayerId());
        payment.setStatus(PaymentStatus.APPROVED);

        // Check if it's a sale (immediate capture) or authorization
        if ("approved".equals(executedPayment.getState())) {
            if (payment.getPaymentIntent() == PaymentIntent.CAPTURE) {
                // Sale - funds captured immediately
                try {
                    String saleId = payPalService.getSaleId(executedPayment);
                    payment.setPaypalCaptureId(saleId);
                    payment.setStatus(PaymentStatus.CAPTURED);
                    payment.setCapturedAmount(payment.getAmount());
                    payment.setCompletedAt(LocalDateTime.now());
                } catch (Exception e) {
                    log.warn("Could not get sale ID, payment still approved");
                }
            } else {
                // Authorization - funds held, not captured
                try {
                    String authId = payPalService.getAuthorizationId(executedPayment);
                    payment.setPaypalAuthorizationId(authId);
                    payment.setStatus(PaymentStatus.AUTHORIZED);
                    payment.setAuthorizedAmount(payment.getAmount());
                } catch (Exception e) {
                    log.warn("Could not get authorization ID, payment still approved");
                }
            }
        }

        // Update payer info
        if (executedPayment.getPayer() != null &&
                executedPayment.getPayer().getPayerInfo() != null) {
            var payerInfo = executedPayment.getPayer().getPayerInfo();
            payment.setPayerEmail(payerInfo.getEmail());
            if (payerInfo.getFirstName() != null) {
                payment.setPayerName(payerInfo.getFirstName() + " " +
                        (payerInfo.getLastName() != null ? payerInfo.getLastName() : ""));
            }
        }

        // Update order status if linked
        if (payment.getOrder() != null) {
            payment.getOrder().setStatus(OrderStatus.PROCESSING);
            orderRepository.save(payment.getOrder());
        }

        payment = paymentRepository.save(payment);

        log.info("Payment executed successfully: id={}, status={}",
                payment.getId(), payment.getStatus());

        String message = payment.getStatus() == PaymentStatus.CAPTURED
                ? "Payment completed successfully!"
                : "Payment authorized. Call capture to charge the customer.";

        return toResponse(payment, message);
    }

    // ==================== CAPTURE AUTHORIZED PAYMENT ====================

    @Transactional
    public PaymentResponse capturePayment(CapturePaymentRequest request) {
        log.info("Capturing payment: authorizationId={}, amount={}",
                request.getAuthorizationId(), request.getAmount());

        // 1. Find payment by authorization ID
        com.shivamsrivastav.payment.entity.Payment payment = paymentRepository.findAll().stream()
                .filter(p -> request.getAuthorizationId().equals(p.getPaypalAuthorizationId()))
                .findFirst()
                .orElseThrow(() -> new PaymentException(
                        "Payment not found for authorization: " + request.getAuthorizationId()));

        // 2. Validate payment can be captured
        if (!payment.isCaptureable()) {
            throw new PaymentException("Payment cannot be captured. Status: " + payment.getStatus());
        }

        // 3. Capture with PayPal
        var capture = payPalService.captureAuthorization(
                request.getAuthorizationId(),
                request.getAmount(),
                request.getCurrency(),
                request.getIsFinalCapture());

        // 4. Update database
        payment.setPaypalCaptureId(capture.getId());
        payment.setCapturedAmount(payment.getCapturedAmount().add(request.getAmount()));

        if (request.getIsFinalCapture()) {
            payment.setStatus(PaymentStatus.CAPTURED);
            payment.setCompletedAt(LocalDateTime.now());
        }

        payment = paymentRepository.save(payment);

        log.info("Payment captured successfully: id={}, capturedAmount={}",
                payment.getId(), payment.getCapturedAmount());

        return toResponse(payment, "Payment captured successfully!");
    }

    // ==================== REFUND PAYMENT ====================

    @Transactional
    public PaymentResponse refundPayment(RefundRequest request) {
        log.info("Refunding payment: captureId={}, amount={}",
                request.getCaptureId(), request.getAmount());

        // 1. Find payment
        com.shivamsrivastav.payment.entity.Payment payment = paymentRepository.findAll().stream()
                .filter(p -> request.getCaptureId().equals(p.getPaypalCaptureId()))
                .findFirst()
                .orElseThrow(() -> new PaymentException(
                        "Payment not found for capture: " + request.getCaptureId()));

        // 2. Validate payment can be refunded
        if (!payment.isRefundable()) {
            throw new PaymentException("Payment cannot be refunded. Status: " + payment.getStatus());
        }

        BigDecimal refundAmount = request.getAmount();
        if (refundAmount == null) {
            refundAmount = payment.getRefundableAmount();
        }

        if (refundAmount.compareTo(payment.getRefundableAmount()) > 0) {
            throw new PaymentException("Refund amount exceeds refundable amount. Max: " +
                    payment.getRefundableAmount());
        }

        // 3. Refund with PayPal
        // Check if this was a sale or capture
        if (payment.getPaymentIntent() == PaymentIntent.CAPTURE) {
            // Direct sale - use sale refund
            payPalService.refundSale(request.getCaptureId(), request.getAmount(), request.getCurrency());
        } else {
            // Authorization then capture - use capture refund
            payPalService.refundCapture(request.getCaptureId(), request.getAmount(), request.getCurrency());
        }

        // 4. Update database
        payment.setRefundedAmount(payment.getRefundedAmount().add(refundAmount));
        payment.setRefundReason(request.getReason());

        if (payment.getRefundedAmount().compareTo(payment.getCapturedAmount()) >= 0) {
            payment.setStatus(PaymentStatus.REFUNDED);
        } else {
            payment.setStatus(PaymentStatus.PARTIALLY_REFUNDED);
        }

        // Update order status
        if (payment.getOrder() != null) {
            payment.getOrder().setStatus(OrderStatus.REFUNDED);
            orderRepository.save(payment.getOrder());
        }

        payment = paymentRepository.save(payment);

        log.info("Payment refunded successfully: id={}, refundedAmount={}",
                payment.getId(), payment.getRefundedAmount());

        String message = payment.getStatus() == PaymentStatus.REFUNDED
                ? "Full refund processed successfully!"
                : "Partial refund processed. Remaining: " + payment.getRefundableAmount();

        return toResponse(payment, message);
    }

    // ==================== GET PAYMENT ====================

    public PaymentResponse getPaymentById(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentException("Payment not found: " + id));
        return toResponse(payment, null);
    }

    public PaymentResponse getPaymentByPaypalId(String paypalPaymentId) {
        var payment = paymentRepository.findByPaypalPaymentId(paypalPaymentId)
                .orElseThrow(() -> new PaymentException("Payment not found: " + paypalPaymentId));
        return toResponse(payment, null);
    }

    // ==================== HELPER METHODS ====================

    private PaymentResponse toResponse(com.shivamsrivastav.payment.entity.Payment payment, String message) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .paypalPaymentId(payment.getPaypalPaymentId())
                .paypalOrderId(payment.getPaypalOrderId())
                .paypalCaptureId(payment.getPaypalCaptureId())
                .paypalAuthorizationId(payment.getPaypalAuthorizationId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .description(payment.getDescription())
                .paymentIntent(payment.getPaymentIntent())
                .status(payment.getStatus())
                .authorizedAmount(payment.getAuthorizedAmount())
                .capturedAmount(payment.getCapturedAmount())
                .refundedAmount(payment.getRefundedAmount())
                .payerEmail(payment.getPayerEmail())
                .payerName(payment.getPayerName())
                .approvalUrl(payment.getApprovalUrl())
                .createdAt(payment.getCreatedAt())
                .completedAt(payment.getCompletedAt())
                .message(message)
                .build();
    }
}
