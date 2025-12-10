package com.shivamsrivastav.payment.entity;

import com.shivamsrivastav.payment.entity.enums.BillingCycle;
import com.shivamsrivastav.payment.entity.enums.SubscriptionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Subscription Entity
 * 
 * Represents a recurring payment subscription.
 * 
 * @author Shivam Srivastav
 */
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paypal_subscription_id", unique = true)
    private String paypalSubscriptionId;

    @Column(name = "paypal_plan_id")
    private String paypalPlanId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency = "USD";

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_cycle")
    private BillingCycle billingCycle;

    @Column(name = "billing_interval")
    private Integer billingInterval = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SubscriptionStatus status = SubscriptionStatus.PENDING;

    @Column(name = "subscriber_email")
    private String subscriberEmail;

    @Column(name = "subscriber_name")
    private String subscriberName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "next_billing_date")
    private LocalDate nextBillingDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==================== Constructors ====================

    public Subscription() {
    }

    public Subscription(Long id, String paypalSubscriptionId, String paypalPlanId, String name,
            String description, BigDecimal amount, String currency, BillingCycle billingCycle,
            Integer billingInterval, SubscriptionStatus status, String subscriberEmail,
            String subscriberName, LocalDate startDate, LocalDate nextBillingDate,
            LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime cancelledAt) {
        this.id = id;
        this.paypalSubscriptionId = paypalSubscriptionId;
        this.paypalPlanId = paypalPlanId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.currency = currency != null ? currency : "USD";
        this.billingCycle = billingCycle;
        this.billingInterval = billingInterval != null ? billingInterval : 1;
        this.status = status != null ? status : SubscriptionStatus.PENDING;
        this.subscriberEmail = subscriberEmail;
        this.subscriberName = subscriberName;
        this.startDate = startDate;
        this.nextBillingDate = nextBillingDate;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
        this.cancelledAt = cancelledAt;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    // ==================== Builder Pattern ====================

    public static SubscriptionBuilder builder() {
        return new SubscriptionBuilder();
    }

    public static class SubscriptionBuilder {
        private Long id;
        private String paypalSubscriptionId;
        private String paypalPlanId;
        private String name;
        private String description;
        private BigDecimal amount;
        private String currency = "USD";
        private BillingCycle billingCycle;
        private Integer billingInterval = 1;
        private SubscriptionStatus status = SubscriptionStatus.PENDING;
        private String subscriberEmail;
        private String subscriberName;
        private LocalDate startDate;
        private LocalDate nextBillingDate;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();
        private LocalDateTime cancelledAt;

        SubscriptionBuilder() {
        }

        public SubscriptionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SubscriptionBuilder paypalSubscriptionId(String paypalSubscriptionId) {
            this.paypalSubscriptionId = paypalSubscriptionId;
            return this;
        }

        public SubscriptionBuilder paypalPlanId(String paypalPlanId) {
            this.paypalPlanId = paypalPlanId;
            return this;
        }

        public SubscriptionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SubscriptionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SubscriptionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public SubscriptionBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public SubscriptionBuilder billingCycle(BillingCycle billingCycle) {
            this.billingCycle = billingCycle;
            return this;
        }

        public SubscriptionBuilder billingInterval(Integer billingInterval) {
            this.billingInterval = billingInterval;
            return this;
        }

        public SubscriptionBuilder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public SubscriptionBuilder subscriberEmail(String subscriberEmail) {
            this.subscriberEmail = subscriberEmail;
            return this;
        }

        public SubscriptionBuilder subscriberName(String subscriberName) {
            this.subscriberName = subscriberName;
            return this;
        }

        public SubscriptionBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public SubscriptionBuilder nextBillingDate(LocalDate nextBillingDate) {
            this.nextBillingDate = nextBillingDate;
            return this;
        }

        public SubscriptionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SubscriptionBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public SubscriptionBuilder cancelledAt(LocalDateTime cancelledAt) {
            this.cancelledAt = cancelledAt;
            return this;
        }

        public Subscription build() {
            return new Subscription(id, paypalSubscriptionId, paypalPlanId, name, description, amount,
                    currency, billingCycle, billingInterval, status, subscriberEmail, subscriberName,
                    startDate, nextBillingDate, createdAt, updatedAt, cancelledAt);
        }
    }
}
