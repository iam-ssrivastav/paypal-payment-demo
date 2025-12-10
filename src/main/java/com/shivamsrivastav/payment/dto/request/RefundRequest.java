package com.shivamsrivastav.payment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * Refund Request DTO
 * 
 * Request for refunding a captured payment.
 * 
 * @author Shivam Srivastav
 */
public class RefundRequest {

    /**
     * Capture ID to refund.
     * This is the ID returned when payment was captured.
     */
    @NotBlank(message = "Capture ID is required")
    private String captureId;

    /**
     * Amount to refund.
     * Leave null for full refund.
     * Set for partial refund.
     */
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Currency (must match original payment currency).
     */
    private String currency = "USD";

    /**
     * Reason for refund (optional, for your records).
     */
    private String reason;

    // ==================== Constructors ====================

    public RefundRequest() {
    }

    public RefundRequest(String captureId, BigDecimal amount, String currency, String reason) {
        this.captureId = captureId;
        this.amount = amount;
        this.currency = currency != null ? currency : "USD";
        this.reason = reason;
    }

    // ==================== Getters and Setters ====================

    public String getCaptureId() {
        return captureId;
    }

    public void setCaptureId(String captureId) {
        this.captureId = captureId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // ==================== Builder Pattern ====================

    public static RefundRequestBuilder builder() {
        return new RefundRequestBuilder();
    }

    public static class RefundRequestBuilder {
        private String captureId;
        private BigDecimal amount;
        private String currency = "USD";
        private String reason;

        RefundRequestBuilder() {
        }

        public RefundRequestBuilder captureId(String captureId) {
            this.captureId = captureId;
            return this;
        }

        public RefundRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public RefundRequestBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public RefundRequestBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public RefundRequest build() {
            return new RefundRequest(captureId, amount, currency, reason);
        }
    }
}
