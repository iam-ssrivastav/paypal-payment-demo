package com.shivamsrivastav.payment.dto.response;

import com.shivamsrivastav.payment.entity.enums.PaymentIntent;
import com.shivamsrivastav.payment.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Payment Response DTO
 * 
 * Standard response for payment operations.
 * 
 * @author Shivam Srivastav
 */
public class PaymentResponse {

    private Long id;
    private String paypalPaymentId;
    private String paypalOrderId;
    private String paypalCaptureId;
    private String paypalAuthorizationId;

    private BigDecimal amount;
    private String currency;
    private String description;

    private PaymentIntent paymentIntent;
    private PaymentStatus status;

    private BigDecimal authorizedAmount;
    private BigDecimal capturedAmount;
    private BigDecimal refundedAmount;

    private String payerEmail;
    private String payerName;

    private String approvalUrl;

    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    private String message;

    // ==================== Constructors ====================

    public PaymentResponse() {
    }

    public PaymentResponse(Long id, String paypalPaymentId, String paypalOrderId, String paypalCaptureId,
            String paypalAuthorizationId, BigDecimal amount, String currency, String description,
            PaymentIntent paymentIntent, PaymentStatus status, BigDecimal authorizedAmount,
            BigDecimal capturedAmount, BigDecimal refundedAmount, String payerEmail,
            String payerName, String approvalUrl, LocalDateTime createdAt,
            LocalDateTime completedAt, String message) {
        this.id = id;
        this.paypalPaymentId = paypalPaymentId;
        this.paypalOrderId = paypalOrderId;
        this.paypalCaptureId = paypalCaptureId;
        this.paypalAuthorizationId = paypalAuthorizationId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.paymentIntent = paymentIntent;
        this.status = status;
        this.authorizedAmount = authorizedAmount;
        this.capturedAmount = capturedAmount;
        this.refundedAmount = refundedAmount;
        this.payerEmail = payerEmail;
        this.payerName = payerName;
        this.approvalUrl = approvalUrl;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.message = message;
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

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // ==================== Builder Pattern ====================

    public static PaymentResponseBuilder builder() {
        return new PaymentResponseBuilder();
    }

    public static class PaymentResponseBuilder {
        private Long id;
        private String paypalPaymentId;
        private String paypalOrderId;
        private String paypalCaptureId;
        private String paypalAuthorizationId;
        private BigDecimal amount;
        private String currency;
        private String description;
        private PaymentIntent paymentIntent;
        private PaymentStatus status;
        private BigDecimal authorizedAmount;
        private BigDecimal capturedAmount;
        private BigDecimal refundedAmount;
        private String payerEmail;
        private String payerName;
        private String approvalUrl;
        private LocalDateTime createdAt;
        private LocalDateTime completedAt;
        private String message;

        PaymentResponseBuilder() {
        }

        public PaymentResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PaymentResponseBuilder paypalPaymentId(String paypalPaymentId) {
            this.paypalPaymentId = paypalPaymentId;
            return this;
        }

        public PaymentResponseBuilder paypalOrderId(String paypalOrderId) {
            this.paypalOrderId = paypalOrderId;
            return this;
        }

        public PaymentResponseBuilder paypalCaptureId(String paypalCaptureId) {
            this.paypalCaptureId = paypalCaptureId;
            return this;
        }

        public PaymentResponseBuilder paypalAuthorizationId(String paypalAuthorizationId) {
            this.paypalAuthorizationId = paypalAuthorizationId;
            return this;
        }

        public PaymentResponseBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentResponseBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public PaymentResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PaymentResponseBuilder paymentIntent(PaymentIntent paymentIntent) {
            this.paymentIntent = paymentIntent;
            return this;
        }

        public PaymentResponseBuilder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public PaymentResponseBuilder authorizedAmount(BigDecimal authorizedAmount) {
            this.authorizedAmount = authorizedAmount;
            return this;
        }

        public PaymentResponseBuilder capturedAmount(BigDecimal capturedAmount) {
            this.capturedAmount = capturedAmount;
            return this;
        }

        public PaymentResponseBuilder refundedAmount(BigDecimal refundedAmount) {
            this.refundedAmount = refundedAmount;
            return this;
        }

        public PaymentResponseBuilder payerEmail(String payerEmail) {
            this.payerEmail = payerEmail;
            return this;
        }

        public PaymentResponseBuilder payerName(String payerName) {
            this.payerName = payerName;
            return this;
        }

        public PaymentResponseBuilder approvalUrl(String approvalUrl) {
            this.approvalUrl = approvalUrl;
            return this;
        }

        public PaymentResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PaymentResponseBuilder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public PaymentResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public PaymentResponse build() {
            return new PaymentResponse(id, paypalPaymentId, paypalOrderId, paypalCaptureId,
                    paypalAuthorizationId, amount, currency, description, paymentIntent, status,
                    authorizedAmount, capturedAmount, refundedAmount, payerEmail, payerName,
                    approvalUrl, createdAt, completedAt, message);
        }
    }
}
