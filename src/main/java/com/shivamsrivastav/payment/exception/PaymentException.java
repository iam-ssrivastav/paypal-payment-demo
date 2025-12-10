package com.shivamsrivastav.payment.exception;

/**
 * Custom Payment Exception
 * 
 * Thrown when payment operations fail or validation checks fail.
 * 
 * @author Shivam Srivastav
 */
public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
