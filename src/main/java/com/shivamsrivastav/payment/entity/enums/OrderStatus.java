package com.shivamsrivastav.payment.entity.enums;

/**
 * Order Status Enum
 * 
 * Tracks the lifecycle of an order in the system.
 * 
 * @author Shivam Srivastav
 */
public enum OrderStatus {

    /**
     * Order created, awaiting payment.
     */
    PENDING,

    /**
     * Payment received, order being processed.
     */
    PROCESSING,

    /**
     * Order has been shipped/fulfilled.
     */
    SHIPPED,

    /**
     * Order delivered to customer.
     */
    DELIVERED,

    /**
     * Order completed successfully.
     */
    COMPLETED,

    /**
     * Order cancelled by customer or admin.
     */
    CANCELLED,

    /**
     * Order refunded.
     */
    REFUNDED
}
