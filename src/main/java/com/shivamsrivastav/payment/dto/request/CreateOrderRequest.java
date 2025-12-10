package com.shivamsrivastav.payment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Create Order Request DTO
 * 
 * Request DTO for creating a new internal order.
 * 
 * @author Shivam Srivastav
 */
public class CreateOrderRequest {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    @NotBlank(message = "Customer Email is required")
    private String customerEmail;

    @NotBlank(message = "Customer Name is required")
    private String customerName;

    // ==================== Constructors ====================

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(String description, BigDecimal amount, String customerEmail, String customerName) {
        this.description = description;
        this.amount = amount;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
    }

    // ==================== Getters and Setters ====================

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // ==================== Builder ====================

    public static CreateOrderRequestBuilder builder() {
        return new CreateOrderRequestBuilder();
    }

    public static class CreateOrderRequestBuilder {
        private String description;
        private BigDecimal amount;
        private String customerEmail;
        private String customerName;

        public CreateOrderRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreateOrderRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public CreateOrderRequestBuilder customerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        public CreateOrderRequestBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public CreateOrderRequest build() {
            return new CreateOrderRequest(description, amount, customerEmail, customerName);
        }
    }
}
