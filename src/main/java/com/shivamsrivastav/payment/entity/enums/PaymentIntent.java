package com.shivamsrivastav.payment.entity.enums;

/**
 * Payment Intent Enum
 * 
 * Defines how the payment should be processed.
 * 
 * CAPTURE vs AUTHORIZE:
 * - CAPTURE: Immediately charge the customer (most common)
 * - AUTHORIZE: Hold funds for later capture (hotels, rentals, pre-orders)
 * 
 * @author Shivam Srivastav
 */
public enum PaymentIntent {

    /**
     * Capture payment immediately.
     * Funds are charged right away when payment is approved.
     * 
     * Use cases:
     * - E-commerce checkout
     * - Digital goods
     * - Immediate delivery services
     */
    CAPTURE,

    /**
     * Authorize payment for later capture.
     * Funds are held (reserved) but not charged.
     * Must be captured within 3-29 days (PayPal limit).
     * 
     * Use cases:
     * - Hotel reservations (capture at checkout)
     * - Car rentals (capture after return)
     * - Pre-orders (capture when shipped)
     * - Custom orders (capture after completion)
     */
    AUTHORIZE
}
