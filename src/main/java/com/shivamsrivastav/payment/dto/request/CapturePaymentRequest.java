package com.shivamsrivastav.payment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Capture Payment Request DTO
 * 
 * Request for capturing an authorized payment.
 * 
 * @author Shivam Srivastav
 */
public class CapturePaymentRequest {

    /**
     * Authorization ID from PayPal.
     */
    @NotBlank(message = "Authorization ID is required")
    private String authorizationId;

    /**
     * Amount to capture.
     * Can be less than or equal to authorized amount.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Currency (must match authorization currency).
     */
    private String currency = "USD";

    /**
     * Whether this is the final capture.
     * If true, remaining authorized amount is released.
     */
    private Boolean isFinalCapture = true;

    // ==================== Constructors ====================

    public CapturePaymentRequest() {
    }

    public CapturePaymentRequest(String authorizationId, BigDecimal amount, String currency, Boolean isFinalCapture) {
        this.authorizationId = authorizationId;
        this.amount = amount;
        this.currency = currency != null ? currency : "USD";
        this.isFinalCapture = isFinalCapture != null ? isFinalCapture : true;
    }

    // ==================== Getters and Setters ====================

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
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

    public Boolean getIsFinalCapture() {
        return isFinalCapture;
    }

    public void setIsFinalCapture(Boolean isFinalCapture) {
        this.isFinalCapture = isFinalCapture;
    }

    // ==================== Builder Pattern ====================

    public static CapturePaymentRequestBuilder builder() {
        return new CapturePaymentRequestBuilder();
    }

    public static class CapturePaymentRequestBuilder {
        private String authorizationId;
        private BigDecimal amount;
        private String currency = "USD";
        private Boolean isFinalCapture = true;

        CapturePaymentRequestBuilder() {
        }

        public CapturePaymentRequestBuilder authorizationId(String authorizationId) {
            this.authorizationId = authorizationId;
            return this;
        }

        public CapturePaymentRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public CapturePaymentRequestBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public CapturePaymentRequestBuilder isFinalCapture(Boolean isFinalCapture) {
            this.isFinalCapture = isFinalCapture;
            return this;
        }

        public CapturePaymentRequest build() {
            return new CapturePaymentRequest(authorizationId, amount, currency, isFinalCapture);
        }
    }
}
