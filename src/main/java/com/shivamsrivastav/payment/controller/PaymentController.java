package com.shivamsrivastav.payment.controller;

import com.shivamsrivastav.payment.dto.request.*;
import com.shivamsrivastav.payment.dto.response.PaymentResponse;
import com.shivamsrivastav.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Payment Controller
 * 
 * REST API for payment operations.
 * 
 * @author Shivam Srivastav
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ==================== CREATE PAYMENT ====================

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        log.info("API: Create payment request: amount={}, currency={}",
                request.getAmount(), request.getCurrency());

        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.ok(response);
    }

    // ==================== EXECUTE PAYMENT ====================

    @PostMapping("/execute")
    public ResponseEntity<PaymentResponse> executePayment(
            @Valid @RequestBody ExecutePaymentRequest request) {

        log.info("API: Execute payment request: paymentId={}", request.getPaymentId());

        PaymentResponse response = paymentService.executePayment(request);
        return ResponseEntity.ok(response);
    }

    // ==================== CAPTURE PAYMENT ====================

    @PostMapping("/capture")
    public ResponseEntity<PaymentResponse> capturePayment(
            @Valid @RequestBody CapturePaymentRequest request) {

        log.info("API: Capture payment request: authorizationId={}",
                request.getAuthorizationId());

        PaymentResponse response = paymentService.capturePayment(request);
        return ResponseEntity.ok(response);
    }

    // ==================== REFUND PAYMENT ====================

    @PostMapping("/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @Valid @RequestBody RefundRequest request) {

        log.info("API: Refund payment request: captureId={}, amount={}",
                request.getCaptureId(), request.getAmount());

        PaymentResponse response = paymentService.refundPayment(request);
        return ResponseEntity.ok(response);
    }

    // ==================== GET PAYMENT ====================

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        log.info("API: Get payment: id={}", id);
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paypal/{paypalId}")
    public ResponseEntity<PaymentResponse> getPaymentByPaypalId(@PathVariable String paypalId) {
        log.info("API: Get payment by PayPal ID: paypalId={}", paypalId);
        PaymentResponse response = paymentService.getPaymentByPaypalId(paypalId);
        return ResponseEntity.ok(response);
    }
}
