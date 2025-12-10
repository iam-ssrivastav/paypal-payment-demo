package com.shivamsrivastav.payment.dto.request;

import com.shivamsrivastav.payment.entity.enums.BillingCycle;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Create Subscription Request DTO
 * 
 * Request for creating a new subscription.
 * 
 * @author Shivam Srivastav
 */
public class CreateSubscriptionRequest {

    /**
     * Name of the subscription plan.
     * e.g., "Pro Monthly", "Enterprise Annual"
     */
    @NotBlank(message = "Plan name is required")
    private String planName;

    /**
     * Description of what the subscription includes.
     */
    private String description;

    /**
     * Amount to charge per billing cycle.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Currency code.
     */
    private String currency = "USD";

    /**
     * Billing frequency.
     */
    private BillingCycle billingCycle = BillingCycle.MONTHLY;

    /**
     * Billing interval multiplier.
     * e.g., billingCycle=MONTHLY, billingInterval=3 = every 3 months
     */
    @Min(value = 1, message = "Billing interval must be at least 1")
    private Integer billingInterval = 1;

    /**
     * Total number of billing cycles (null = infinite).
     */
    private Integer totalCycles;

    // ==================== Constructors ====================

    public CreateSubscriptionRequest() {
    }

    public CreateSubscriptionRequest(String planName, String description, BigDecimal amount,
            String currency, BillingCycle billingCycle, Integer billingInterval,
            Integer totalCycles) {
        this.planName = planName;
        this.description = description;
        this.amount = amount;
        this.currency = currency != null ? currency : "USD";
        this.billingCycle = billingCycle != null ? billingCycle : BillingCycle.MONTHLY;
        this.billingInterval = billingInterval != null ? billingInterval : 1;
        this.totalCycles = totalCycles;
    }

    // ==================== Getters and Setters ====================

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public Integer getBillingInterval() {
        return billingInterval;
    }

    public void setBillingInterval(Integer billingInterval) {
        this.billingInterval = billingInterval;
    }

    public Integer getTotalCycles() {
        return totalCycles;
    }

    public void setTotalCycles(Integer totalCycles) {
        this.totalCycles = totalCycles;
    }

    // ==================== Builder Pattern ====================

    public static CreateSubscriptionRequestBuilder builder() {
        return new CreateSubscriptionRequestBuilder();
    }

    public static class CreateSubscriptionRequestBuilder {
        private String planName;
        private String description;
        private BigDecimal amount;
        private String currency = "USD";
        private BillingCycle billingCycle = BillingCycle.MONTHLY;
        private Integer billingInterval = 1;
        private Integer totalCycles;

        CreateSubscriptionRequestBuilder() {
        }

        public CreateSubscriptionRequestBuilder planName(String planName) {
            this.planName = planName;
            return this;
        }

        public CreateSubscriptionRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreateSubscriptionRequestBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public CreateSubscriptionRequestBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public CreateSubscriptionRequestBuilder billingCycle(BillingCycle billingCycle) {
            this.billingCycle = billingCycle;
            return this;
        }

        public CreateSubscriptionRequestBuilder billingInterval(Integer billingInterval) {
            this.billingInterval = billingInterval;
            return this;
        }

        public CreateSubscriptionRequestBuilder totalCycles(Integer totalCycles) {
            this.totalCycles = totalCycles;
            return this;
        }

        public CreateSubscriptionRequest build() {
            return new CreateSubscriptionRequest(planName, description, amount, currency,
                    billingCycle, billingInterval, totalCycles);
        }
    }
}
