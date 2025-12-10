package com.shivamsrivastav.payment.entity.enums;

/**
 * Payment Status Enum
 * 
 * Represents the lifecycle of a payment in the system.
 * 
 * Payment Flow:
 * CREATED → APPROVED → CAPTURED/AUTHORIZED → COMPLETED
 * → REFUNDED (partial/full)
 * → VOIDED (cancelled authorization)
 * → FAILED
 * 
 * @author Shivam Srivastav
 */
public enum PaymentStatus {

    /**
     * Payment has been created but not yet approved by the payer.
     * User has not yet been redirected to PayPal.
     */
    CREATED,

    /**
     * Payment has been approved by the payer on PayPal.
     * Waiting to be captured or authorized.
     */
    APPROVED,

    /**
     * Funds have been authorized (held) but not captured.
     * Used for delayed capture scenarios (hotels, rentals).
     */
    AUTHORIZED,

    /**
     * Funds have been captured (charged).
     * Money has been transferred from payer's account.
     */
    CAPTURED,

    /**
     * Payment has been successfully completed.
     * All funds captured and transferred.
     */
    COMPLETED,

    /**
     * Payment has been partially refunded.
     * Some amount returned to the payer.
     */
    PARTIALLY_REFUNDED,

    /**
     * Payment has been fully refunded.
     * All funds returned to the payer.
     */
    REFUNDED,

    /**
     * Authorization has been voided/cancelled.
     * Held funds released back to payer.
     */
    VOIDED,

    /**
     * Payment failed due to an error.
     * Could be insufficient funds, validation error, etc.
     */
    FAILED,

    /**
     * Payment was cancelled by the user.
     * User clicked cancel on PayPal checkout.
     */
    CANCELLED,

    /**
     * Payment is pending review or processing.
     * May require additional verification.
     */
    PENDING
}
