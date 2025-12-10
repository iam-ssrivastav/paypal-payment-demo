package com.shivamsrivastav.payment.dto.request;

import com.shivamsrivastav.payment.entity.enums.PaymentIntent;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Create Payment Request DTO
 * 
 * Request body for creating a new payment.
 * 
 * @author Shivam Srivastav
 */
public class CreatePaymentRequest {

    /**
     * Payment amount.
     * Must be greater than 0.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Currency code (USD, EUR, GBP, etc.).
     */
    @NotBlank(message = "Currency is required")
    private String currency = "USD";

    /**
     * Payment description shown to customer.
     */
    @NotBlank(message = "Description is required")
    private String description;

    /**
     * Payment intent: CAPTURE (immediate) or AUTHORIZE (hold).
     */
    private PaymentIntent paymentIntent = PaymentIntent.CAPTURE;

    /**
     * Idempotency key to prevent duplicate payments.
     * Client should generate a unique key (UUID recommended).
     */
    private String idempotencyKey;

    /**
     * Optional order ID to link payment to an order.
     */
    private Long orderId;

    // ==================== Constructors ====================

    public CreatePaymentRequest() {
    }

    public CreatePaymentRequest(BigDecimal amount, String currency, String description,
            PaymentIntent paymentIntent, String idempotencyKey, Long orderId) {
        this.amount = amount;
        this.currency = currency != null ? currency : "USD";
        this.description = description;
        this.paymentIntent = paymentIntent != null ? paymentIntent : PaymentIntent.CAPTURE;
        this.idempotencyKey = idempotencyKey;
        this.orderId = orderId;
    }

    // ==================== Getters and Setters ====================

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

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    // ==================== Builder Pattern ====================

    public static CreatePaymentRequestBuilder builder() {
        return new CreatePaymentRequestBuilder();
    }

    public static class CreatePaymentRequestBuilder {
        private BigDecimal amount;
        private String currency = "USD";
        private String description;
        private PaymentIntent paymentIntent = PaymentIntent.CAPTURE;
        private String idempotencyKey;
        private Long orderId;

        CreatePaymentRequestBuilder() {
        }

        public CreatePaymentRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public CreatePaymentRequestBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public CreatePaymentRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreatePaymentRequestBuilder paymentIntent(PaymentIntent paymentIntent) {
            this.paymentIntent = paymentIntent;
            return this;
        }

        public CreatePaymentRequestBuilder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public CreatePaymentRequestBuilder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public CreatePaymentRequest build() {
            return new CreatePaymentRequest(amount, currency, description, paymentIntent, idempotencyKey, orderId);
        }
    }
}
