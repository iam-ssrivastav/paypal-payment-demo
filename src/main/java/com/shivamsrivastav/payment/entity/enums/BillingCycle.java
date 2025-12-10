package com.shivamsrivastav.payment.entity.enums;

/**
 * Billing Cycle Enum
 * 
 * Defines the frequency of recurring payments.
 * 
 * @author Shivam Srivastav
 */
public enum BillingCycle {

    /**
     * Bill every day.
     */
    DAILY,

    /**
     * Bill every week.
     */
    WEEKLY,

    /**
     * Bill every month.
     */
    MONTHLY,

    /**
     * Bill every quarter (3 months).
     */
    QUARTERLY,

    /**
     * Bill every 6 months.
     */
    SEMI_ANNUALLY,

    /**
     * Bill every year.
     */
    YEARLY
}
