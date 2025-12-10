package com.shivamsrivastav.payment.repository;

import com.shivamsrivastav.payment.entity.Payment;
import com.shivamsrivastav.payment.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Payment Repository
 * 
 * @author Shivam Srivastav
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaypalPaymentId(String paypalPaymentId);

    Optional<Payment> findByPaypalOrderId(String paypalOrderId);

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByPayerEmail(String payerEmail);

    boolean existsByIdempotencyKey(String idempotencyKey);
}
