package com.shivamsrivastav.payment.dto.response;

import com.shivamsrivastav.payment.entity.enums.BillingCycle;
import com.shivamsrivastav.payment.entity.enums.SubscriptionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Subscription Response DTO
 * 
 * Standard response for subscription operations.
 * 
 * @author Shivam Srivastav
 */
public class SubscriptionResponse {

    private Long id;
    private String paypalSubscriptionId;
    private String paypalPlanId;
    private String name;
    private String description;
    private BigDecimal amount;
    private String currency;
    private BillingCycle billingCycle;
    private Integer billingInterval;
    private SubscriptionStatus status;
    private String subscriberEmail;
    private String subscriberName;
    private LocalDate startDate;
    private LocalDate nextBillingDate;
    private LocalDateTime createdAt;
    private String approvalUrl;
    private String message;

    // ==================== Constructors ====================

    public SubscriptionResponse() {
    }

    public SubscriptionResponse(Long id, String paypalSubscriptionId, String paypalPlanId, String name,
            String description, BigDecimal amount, String currency, BillingCycle billingCycle,
            Integer billingInterval, SubscriptionStatus status, String subscriberEmail,
            String subscriberName, LocalDate startDate, LocalDate nextBillingDate,
            LocalDateTime createdAt, String approvalUrl, String message) {
        this.id = id;
        this.paypalSubscriptionId = paypalSubscriptionId;
        this.paypalPlanId = paypalPlanId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.billingCycle = billingCycle;
        this.billingInterval = billingInterval;
        this.status = status;
        this.subscriberEmail = subscriberEmail;
        this.subscriberName = subscriberName;
        this.startDate = startDate;
        this.nextBillingDate = nextBillingDate;
        this.createdAt = createdAt;
        this.approvalUrl = approvalUrl;
        this.message = message;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalSubscriptionId() {
        return paypalSubscriptionId;
    }

    public void setPaypalSubscriptionId(String paypalSubscriptionId) {
        this.paypalSubscriptionId = paypalSubscriptionId;
    }

    public String getPaypalPlanId() {
        return paypalPlanId;
    }

    public void setPaypalPlanId(String paypalPlanId) {
        this.paypalPlanId = paypalPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public String getSubscriberEmail() {
        return subscriberEmail;
    }

    public void setSubscriberEmail(String subscriberEmail) {
        this.subscriberEmail = subscriberEmail;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getNextBillingDate() {
        return nextBillingDate;
    }

    public void setNextBillingDate(LocalDate nextBillingDate) {
        this.nextBillingDate = nextBillingDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // ==================== Builder Pattern ====================

    public static SubscriptionResponseBuilder builder() {
        return new SubscriptionResponseBuilder();
    }

    public static class SubscriptionResponseBuilder {
        private Long id;
        private String paypalSubscriptionId;
        private String paypalPlanId;
        private String name;
        private String description;
        private BigDecimal amount;
        private String currency;
        private BillingCycle billingCycle;
        private Integer billingInterval;
        private SubscriptionStatus status;
        private String subscriberEmail;
        private String subscriberName;
        private LocalDate startDate;
        private LocalDate nextBillingDate;
        private LocalDateTime createdAt;
        private String approvalUrl;
        private String message;

        SubscriptionResponseBuilder() {
        }

        public SubscriptionResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SubscriptionResponseBuilder paypalSubscriptionId(String paypalSubscriptionId) {
            this.paypalSubscriptionId = paypalSubscriptionId;
            return this;
        }

        public SubscriptionResponseBuilder paypalPlanId(String paypalPlanId) {
            this.paypalPlanId = paypalPlanId;
            return this;
        }

        public SubscriptionResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SubscriptionResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SubscriptionResponseBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public SubscriptionResponseBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public SubscriptionResponseBuilder billingCycle(BillingCycle billingCycle) {
            this.billingCycle = billingCycle;
            return this;
        }

        public SubscriptionResponseBuilder billingInterval(Integer billingInterval) {
            this.billingInterval = billingInterval;
            return this;
        }

        public SubscriptionResponseBuilder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public SubscriptionResponseBuilder subscriberEmail(String subscriberEmail) {
            this.subscriberEmail = subscriberEmail;
            return this;
        }

        public SubscriptionResponseBuilder subscriberName(String subscriberName) {
            this.subscriberName = subscriberName;
            return this;
        }

        public SubscriptionResponseBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public SubscriptionResponseBuilder nextBillingDate(LocalDate nextBillingDate) {
            this.nextBillingDate = nextBillingDate;
            return this;
        }

        public SubscriptionResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SubscriptionResponseBuilder approvalUrl(String approvalUrl) {
            this.approvalUrl = approvalUrl;
            return this;
        }

        public SubscriptionResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public SubscriptionResponse build() {
            return new SubscriptionResponse(id, paypalSubscriptionId, paypalPlanId, name, description,
                    amount, currency, billingCycle, billingInterval, status, subscriberEmail,
                    subscriberName, startDate, nextBillingDate, createdAt, approvalUrl, message);
        }
    }
}
