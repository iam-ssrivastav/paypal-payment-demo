package com.shivamsrivastav.payment.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.shivamsrivastav.payment.dto.request.CreatePaymentRequest;
import com.shivamsrivastav.payment.exception.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * PayPal Service
 * 
 * Core service for interacting with PayPal REST API.
 * Handles all direct communication with PayPal.
 * 
 * @author Shivam Srivastav
 */
@Service
public class PayPalService {

    private static final Logger log = LoggerFactory.getLogger(PayPalService.class);

    @org.springframework.beans.factory.annotation.Value("${paypal.client.id}")
    private String clientId;

    private final APIContext apiContext;

    private boolean isMockMode() {
        return clientId == null || clientId.startsWith("YOUR_") || clientId.isEmpty();
    }

    private Payment mockCreatePayment(CreatePaymentRequest request) {
        log.warn("MOCK MODE: Creating fake payment response");
        Payment payment = new Payment();
        payment.setId("PAYID-MOCK-" + System.currentTimeMillis());
        payment.setState("created");
        payment.setIntent(request.getPaymentIntent().toString().toLowerCase());

        // Mock Transactions
        Amount amount = new Amount();
        amount.setCurrency(request.getCurrency());
        amount.setTotal(request.getAmount().setScale(2, RoundingMode.HALF_UP).toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        payment.setTransactions(transactions);

        // Mock Links
        List<Links> links = new ArrayList<>();
        Links approvalLink = new Links();
        approvalLink.setRel("approval_url");
        // Redirect to our own success endpoint with fake IDs
        approvalLink.setHref("http://localhost:8089/api/payments/success?paymentId=" + payment.getId()
                + "&PayerID=MOCK_PAYER_" + System.currentTimeMillis());
        links.add(approvalLink);
        payment.setLinks(links);

        return payment;
    }

    private Payment mockExecutePayment(String paymentId, String payerId) {
        log.warn("MOCK MODE: Executing fake payment");
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setState("approved");
        payment.setCart(payerId);

        // Mock Transaction
        Transaction transaction = new Transaction();
        Amount amount = new Amount();
        amount.setTotal("100.00"); // Simplified for mock
        amount.setCurrency("USD");
        transaction.setAmount(amount);

        // Mock Related Resources
        RelatedResources relatedResources = new RelatedResources();
        Sale sale = new Sale();
        sale.setId("SALE-" + System.currentTimeMillis());
        sale.setState("completed");
        relatedResources.setSale(sale);

        Authorization authorization = new Authorization();
        authorization.setId("AUTH-" + System.currentTimeMillis());
        authorization.setState("authorized");
        relatedResources.setAuthorization(authorization);

        transaction.setRelatedResources(List.of(relatedResources));
        payment.setTransactions(List.of(transaction));

        return payment;
    }

    public PayPalService(APIContext apiContext) {
        this.apiContext = apiContext;
    }

    /**
     * Create a payment in PayPal.
     * 
     * @param request Payment details
     * @return Created Payment object from PayPal
     */
    public Payment createPayment(CreatePaymentRequest request) {
        // 1. Set Amount
        Amount amount = new Amount();
        amount.setCurrency(request.getCurrency());
        amount.setTotal(request.getAmount().setScale(2, RoundingMode.HALF_UP).toString());

        // 2. Set Transaction
        Transaction transaction = new Transaction();
        transaction.setDescription(request.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // 3. Set Payer
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // 4. Set Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/api/payments/cancel");
        redirectUrls.setReturnUrl("http://localhost:8080/api/payments/success");

        // 5. Create Payment Object
        Payment payment = new Payment();
        payment.setIntent(request.getPaymentIntent().toString().toLowerCase());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        // 6. Execute API Call (Mock or Real)
        if (isMockMode()) {
            return mockCreatePayment(request);
        }

        try {
            return payment.create(apiContext);
        } catch (PayPalRESTException e) {
            log.error("Error creating payment: {}", e.getMessage(), e);
            throw new PaymentException("Failed to create payment", e);
        }
    }

    /**
     * Execute a payment after user approval.
     * 
     * @param paymentId PayPal Payment ID
     * @param payerId   PayPal Payer ID
     * @return Executed Payment
     */
    public Payment executePayment(String paymentId, String payerId) {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        if (isMockMode()) {
            return mockExecutePayment(paymentId, payerId);
        }

        try {
            return payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            log.error("Error executing payment: {}", e.getMessage(), e);
            throw new PaymentException("Failed to execute payment", e);
        }
    }

    /**
     * Capture an authorized payment.
     * 
     * @param authorizationId PayPal Authorization ID
     * @param amount          Amount to capture
     * @param currency        Currency code
     * @param isFinalCapture  Whether this is the final capture
     * @return Capture details
     */
    public Capture captureAuthorization(String authorizationId, BigDecimal amount, String currency,
            boolean isFinalCapture) {
        Authorization authorization = new Authorization();
        authorization.setId(authorizationId);

        Capture capture = new Capture();
        Amount captureAmount = new Amount();
        captureAmount.setCurrency(currency);
        captureAmount.setTotal(amount.setScale(2, RoundingMode.HALF_UP).toString());

        capture.setAmount(captureAmount);
        capture.setIsFinalCapture(isFinalCapture);

        try {
            return authorization.capture(apiContext, capture);
        } catch (PayPalRESTException e) {
            log.error("Error capturing authorization: {}", e.getMessage(), e);
            throw new PaymentException("Failed to capture authorization", e);
        }
    }

    /**
     * Void an authorization.
     * Releases held funds.
     */
    public void voidAuthorization(String authorizationId) {
        Authorization authorization = new Authorization();
        authorization.setId(authorizationId);

        try {
            authorization.doVoid(apiContext);
        } catch (PayPalRESTException e) {
            log.error("Error voiding authorization: {}", e.getMessage(), e);
            throw new PaymentException("Failed to void authorization", e);
        }
    }

    /**
     * Refund a captured payment (Sale).
     * 
     * @param saleId   PayPal Sale ID (Capture ID)
     * @param amount   Amount to refund (null for full refund)
     * @param currency Currency code
     * @return Refund details
     */
    public Refund refundSale(String saleId, BigDecimal amount, String currency) {
        Sale sale = new Sale();
        sale.setId(saleId);

        RefundRequest refundRequest = new RefundRequest();
        if (amount != null) {
            Amount refundAmount = new Amount();
            refundAmount.setCurrency(currency);
            refundAmount.setTotal(amount.setScale(2, RoundingMode.HALF_UP).toString());
            refundRequest.setAmount(refundAmount);
        }

        try {
            return sale.refund(apiContext, refundRequest);
        } catch (PayPalRESTException e) {
            log.error("Error refunding sale: {}", e.getMessage(), e);
            throw new PaymentException("Failed to refund sale", e);
        }
    }

    /**
     * Refund a captured payment (Capture).
     * Used when payment was authorized then captured.
     */
    public Refund refundCapture(String captureId, BigDecimal amount, String currency) {
        Capture capture = new Capture();
        capture.setId(captureId);

        RefundRequest refundRequest = new RefundRequest();
        if (amount != null) {
            Amount refundAmount = new Amount();
            refundAmount.setCurrency(currency);
            refundAmount.setTotal(amount.setScale(2, RoundingMode.HALF_UP).toString());
            refundRequest.setAmount(refundAmount);
        }

        try {
            return capture.refund(apiContext, refundRequest);
        } catch (PayPalRESTException e) {
            log.error("Error refunding capture: {}", e.getMessage(), e);
            throw new PaymentException("Failed to refund capture", e);
        }
    }

    /**
     * Get approval URL from created payment.
     */
    public String getApprovalUrl(Payment payment) {
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equalsIgnoreCase(link.getRel()))
                .findFirst()
                .map(Links::getHref)
                .orElseThrow(() -> new PaymentException("No approval URL found in payment response"));
    }

    /**
     * Get Sale ID from executed payment.
     */
    public String getSaleId(Payment payment) {
        return payment.getTransactions().get(0)
                .getRelatedResources().get(0)
                .getSale().getId();
    }

    /**
     * Get Authorization ID from executed payment.
     */
    public String getAuthorizationId(Payment payment) {
        return payment.getTransactions().get(0)
                .getRelatedResources().get(0)
                .getAuthorization().getId();
    }
}
