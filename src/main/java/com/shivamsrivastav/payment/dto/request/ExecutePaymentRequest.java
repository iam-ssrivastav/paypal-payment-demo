package com.shivamsrivastav.payment.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Execute Payment Request DTO
 * 
 * Request for executing a payment after user approval.
 * 
 * @author Shivam Srivastav
 */
public class ExecutePaymentRequest {

    /**
     * PayPal payment ID from the redirect URL.
     */
    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    /**
     * PayPal payer ID from the redirect URL.
     */
    @NotBlank(message = "Payer ID is required")
    private String payerId;

    // ==================== Constructors ====================

    public ExecutePaymentRequest() {
    }

    public ExecutePaymentRequest(String paymentId, String payerId) {
        this.paymentId = paymentId;
        this.payerId = payerId;
    }

    // ==================== Getters and Setters ====================

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    // ==================== Builder Pattern ====================

    public static ExecutePaymentRequestBuilder builder() {
        return new ExecutePaymentRequestBuilder();
    }

    public static class ExecutePaymentRequestBuilder {
        private String paymentId;
        private String payerId;

        ExecutePaymentRequestBuilder() {
        }

        public ExecutePaymentRequestBuilder paymentId(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public ExecutePaymentRequestBuilder payerId(String payerId) {
            this.payerId = payerId;
            return this;
        }

        public ExecutePaymentRequest build() {
            return new ExecutePaymentRequest(paymentId, payerId);
        }
    }
}
