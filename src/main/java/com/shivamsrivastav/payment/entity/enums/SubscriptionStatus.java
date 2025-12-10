package com.shivamsrivastav.payment.entity.enums;

/**
 * Subscription Status Enum
 * 
 * Tracks the lifecycle of a recurring payment subscription.
 * 
 * @author Shivam Srivastav
 */
public enum SubscriptionStatus {

    /**
     * Subscription created but not yet activated.
     */
    PENDING,

    /**
     * Subscription is active and billing.
     */
    ACTIVE,

    /**
     * Subscription temporarily paused.
     * Billing suspended but can be resumed.
     */
    SUSPENDED,

    /**
     * Subscription cancelled by user or admin.
     * No future billing will occur.
     */
    CANCELLED,

    /**
     * Subscription has naturally expired.
     * End of billing period reached.
     */
    EXPIRED,

    /**
     * Subscription activation failed.
     */
    FAILED
}
