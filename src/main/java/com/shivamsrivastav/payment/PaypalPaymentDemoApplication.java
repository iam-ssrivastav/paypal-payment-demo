package com.shivamsrivastav.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PayPal Payment Gateway Demo Application
 * 
 * A comprehensive Spring Boot application demonstrating PayPal payment
 * integration
 * with all essential payment concepts:
 * - One-time payments
 * - Authorization and Capture
 * - Refunds (full and partial)
 * - Subscriptions/Recurring payments
 * - Webhooks
 * - Idempotency
 * 
 * @author Shivam Srivastav
 */
@SpringBootApplication
public class PaypalPaymentDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaypalPaymentDemoApplication.class, args);
    }
}
