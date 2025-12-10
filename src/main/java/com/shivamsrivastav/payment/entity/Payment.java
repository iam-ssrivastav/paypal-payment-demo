package com.shivamsrivastav.payment.entity;

import com.shivamsrivastav.payment.entity.enums.PaymentIntent;
import com.shivamsrivastav.payment.entity.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payment Entity
 * 
 * Represents a payment transaction in the system.
 * This is the core entity that tracks all payment operations.
 * 
 * Key Payment Concepts Demonstrated:
 * 
 * 1. PAYMENT INTENT (CAPTURE vs AUTHORIZE)
 * - CAPTURE: Immediately charge the customer
 * - AUTHORIZE: Hold funds for later capture
 * 
 * 2. IDEMPOTENCY KEY
 * - Unique identifier to prevent duplicate payments
 * - Client generates this key for each payment attempt
 * 
 * 3. AMOUNT TRACKING
 * - amount: Original payment amount
 * - authorizedAmount: Amount authorized (held)
 * - capturedAmount: Amount actually charged
 * - refundedAmount: Amount refunded back
 * 
 * 4. PAYMENT STATUS LIFECYCLE
 * CREATED → APPROVED → CAPTURED/AUTHORIZED → COMPLETED/REFUNDED
 * 
 * @author Shivam Srivastav
 */
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==================== PayPal Identifiers ====================

    /**
     * PayPal's unique payment ID.
     * Format: PAYID-XXXXXXXX
     */
    @Column(name = "paypal_payment_id", unique = true)
    private String paypalPaymentId;

    /**
     * PayPal payer ID (who is paying).
     */
    @Column(name = "paypal_payer_id")
    private String paypalPayerId;

    /**
     * PayPal order ID (for Orders API v2).
     */
    @Column(name = "paypal_order_id")
    private String paypalOrderId;

    /**
     * PayPal capture ID (after funds are captured).
     */
    @Column(name = "paypal_capture_id")
    private String paypalCaptureId;

    /**
     * PayPal authorization ID (when funds are authorized but not captured).
     */
    @Column(name = "paypal_authorization_id")
    private String paypalAuthorizationId;

    // ==================== Order Link ====================

    /**
     * Reference to the order being paid for.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    // ==================== Payment Details ====================

    /**
     * Original payment amount.
     */
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * Currency code (USD, EUR, etc.).
     */
    @Column(name = "currency", length = 3)
    private String currency = "USD";

    /**
     * Payment description shown to customer.
     */
    @Column(name = "description", length = 500)
    private String description;

    // ==================== Payment Intent ====================

    /**
     * How to process the payment:
     * - CAPTURE: Charge immediately
     * - AUTHORIZE: Hold funds for later capture
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_intent", length = 20)
    private PaymentIntent paymentIntent = PaymentIntent.CAPTURE;

    // ==================== Payment Status ====================

    /**
     * Current status of the payment.
     * Tracks the entire payment lifecycle.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private PaymentStatus status = PaymentStatus.CREATED;

    // ==================== Amount Tracking ====================

    /**
     * Amount that was authorized (held).
     * Only applicable for AUTHORIZE intent.
     */
    @Column(name = "authorized_amount", precision = 10, scale = 2)
    private BigDecimal authorizedAmount;

    /**
     * Amount that has been captured (charged).
     * Can be less than authorized amount (partial capture).
     */
    @Column(name = "captured_amount", precision = 10, scale = 2)
    private BigDecimal capturedAmount = BigDecimal.ZERO;

    /**
     * Amount that has been refunded.
     * Can be built up through multiple partial refunds.
     */
    @Column(name = "refunded_amount", precision = 10, scale = 2)
    private BigDecimal refundedAmount = BigDecimal.ZERO;

    /**
     * Reason for refund (if refunded).
     */
    @Column(name = "refund_reason", length = 500)
    private String refundReason;

    // ==================== Idempotency ====================

    /**
     * Unique key to prevent duplicate payments.
     * 
     * CONCEPT: Idempotency
     * - Client generates a unique key for each payment attempt
     * - If same key is sent twice, second request is rejected
     * - Prevents double-charging due to network issues, retries, etc.
     * 
     * Best Practice: Use UUID or combination of user ID + timestamp
     */
    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    // ==================== Payer Information ====================

    /**
     * Payer's email address from PayPal.
     */
    @Column(name = "payer_email")
    private String payerEmail;

    /**
     * Payer's full name from PayPal.
     */
    @Column(name = "payer_name")
    private String payerName;

    // ==================== URLs ====================

    /**
     * PayPal approval URL where user is redirected.
     */
    @Column(name = "approval_url", length = 1000)
    private String approvalUrl;

    // ==================== Timestamps ====================

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * When the payment was completed.
     */
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==================== Constructors ====================

    public Payment() {
    }

    public Payment(Long id, String paypalPaymentId, String paypalPayerId, String paypalOrderId,
            String paypalCaptureId, String paypalAuthorizationId, Order order, BigDecimal amount,
            String currency, String description, PaymentIntent paymentIntent, PaymentStatus status,
            BigDecimal authorizedAmount, BigDecimal capturedAmount, BigDecimal refundedAmount,
            String refundReason, String idempotencyKey, String payerEmail, String payerName,
            String approvalUrl, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime completedAt) {
        this.id = id;
        this.paypalPaymentId = paypalPaymentId;
        this.paypalPayerId = paypalPayerId;
        this.paypalOrderId = paypalOrderId;
        this.paypalCaptureId = paypalCaptureId;
        this.paypalAuthorizationId = paypalAuthorizationId;
        this.order = order;
        this.amount = amount;
        this.currency = currency != null ? currency : "USD";
        this.description = description;
        this.paymentIntent = paymentIntent != null ? paymentIntent : PaymentIntent.CAPTURE;
        this.status = status != null ? status : PaymentStatus.CREATED;
        this.authorizedAmount = authorizedAmount;
        this.capturedAmount = capturedAmount != null ? capturedAmount : BigDecimal.ZERO;
        this.refundedAmount = refundedAmount != null ? refundedAmount : BigDecimal.ZERO;
        this.refundReason = refundReason;
        this.idempotencyKey = idempotencyKey;
        this.payerEmail = payerEmail;
        this.payerName = payerName;
        this.approvalUrl = approvalUrl;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
        this.completedAt = completedAt;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalPaymentId() {
        return paypalPaymentId;
    }

    public void setPaypalPaymentId(String paypalPaymentId) {
        this.paypalPaymentId = paypalPaymentId;
    }

    public String getPaypalPayerId() {
        return paypalPayerId;
    }

    public void setPaypalPayerId(String paypalPayerId) {
        this.paypalPayerId = paypalPayerId;
    }

    public String getPaypalOrderId() {
        return paypalOrderId;
    }

    public void setPaypalOrderId(String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }

    public String getPaypalCaptureId() {
        return paypalCaptureId;
    }

    public void setPaypalCaptureId(String paypalCaptureId) {
        this.paypalCaptureId = paypalCaptureId;
    }

    public String getPaypalAuthorizationId() {
        return paypalAuthorizationId;
    }

    public void setPaypalAuthorizationId(String paypalAuthorizationId) {
        this.paypalAuthorizationId = paypalAuthorizationId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentIntent getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(PaymentIntent paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getAuthorizedAmount() {
        return authorizedAmount;
    }

    public void setAuthorizedAmount(BigDecimal authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }

    public BigDecimal getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(BigDecimal capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public BigDecimal getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(BigDecimal refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    // ==================== Helper Methods ====================

    /**
     * Check if payment can be refunded.
     * Only CAPTURED, COMPLETED, or PARTIALLY_REFUNDED payments can be refunded.
     */
    public boolean isRefundable() {
        return status == PaymentStatus.CAPTURED
                || status == PaymentStatus.COMPLETED
                || status == PaymentStatus.PARTIALLY_REFUNDED;
    }

    /**
     * Get remaining amount that can be refunded.
     */
    public BigDecimal getRefundableAmount() {
        return capturedAmount.subtract(refundedAmount);
    }

    /**
     * Check if payment is authorized and can be captured.
     */
    public boolean isCaptureable() {
        return status == PaymentStatus.AUTHORIZED;
    }

    /**
     * Get remaining authorized amount that can be captured.
     */
    public BigDecimal getCaptureableAmount() {
        if (authorizedAmount == null)
            return BigDecimal.ZERO;
        return authorizedAmount.subtract(capturedAmount);
    }

    // ==================== Builder Pattern ====================

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {
        private Long id;
        private String paypalPaymentId;
        private String paypalPayerId;
        private String paypalOrderId;
        private String paypalCaptureId;
        private String paypalAuthorizationId;
        private Order order;
        private BigDecimal amount;
        private String currency = "USD";
        private String description;
        private PaymentIntent paymentIntent = PaymentIntent.CAPTURE;
        private PaymentStatus status = PaymentStatus.CREATED;
        private BigDecimal authorizedAmount;
        private BigDecimal capturedAmount = BigDecimal.ZERO;
        private BigDecimal refundedAmount = BigDecimal.ZERO;
        private String refundReason;
        private String idempotencyKey;
        private String payerEmail;
        private String payerName;
        private String approvalUrl;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();
        private LocalDateTime completedAt;

        PaymentBuilder() {
        }

        public PaymentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder paypalPaymentId(String paypalPaymentId) {
            this.paypalPaymentId = paypalPaymentId;
            return this;
        }

        public PaymentBuilder paypalPayerId(String paypalPayerId) {
            this.paypalPayerId = paypalPayerId;
            return this;
        }

        public PaymentBuilder paypalOrderId(String paypalOrderId) {
            this.paypalOrderId = paypalOrderId;
            return this;
        }

        public PaymentBuilder paypalCaptureId(String paypalCaptureId) {
            this.paypalCaptureId = paypalCaptureId;
            return this;
        }

        public PaymentBuilder paypalAuthorizationId(String paypalAuthorizationId) {
            this.paypalAuthorizationId = paypalAuthorizationId;
            return this;
        }

        public PaymentBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public PaymentBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public PaymentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PaymentBuilder paymentIntent(PaymentIntent paymentIntent) {
            this.paymentIntent = paymentIntent;
            return this;
        }

        public PaymentBuilder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public PaymentBuilder authorizedAmount(BigDecimal authorizedAmount) {
            this.authorizedAmount = authorizedAmount;
            return this;
        }

        public PaymentBuilder capturedAmount(BigDecimal capturedAmount) {
            this.capturedAmount = capturedAmount;
            return this;
        }

        public PaymentBuilder refundedAmount(BigDecimal refundedAmount) {
            this.refundedAmount = refundedAmount;
            return this;
        }

        public PaymentBuilder refundReason(String refundReason) {
            this.refundReason = refundReason;
            return this;
        }

        public PaymentBuilder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public PaymentBuilder payerEmail(String payerEmail) {
            this.payerEmail = payerEmail;
            return this;
        }

        public PaymentBuilder payerName(String payerName) {
            this.payerName = payerName;
            return this;
        }

        public PaymentBuilder approvalUrl(String approvalUrl) {
            this.approvalUrl = approvalUrl;
            return this;
        }

        public PaymentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PaymentBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PaymentBuilder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Payment build() {
            return new Payment(id, paypalPaymentId, paypalPayerId, paypalOrderId, paypalCaptureId,
                    paypalAuthorizationId, order, amount, currency, description, paymentIntent, status,
                    authorizedAmount, capturedAmount, refundedAmount, refundReason, idempotencyKey,
                    payerEmail, payerName, approvalUrl, createdAt, updatedAt, completedAt);
        }
    }
}
