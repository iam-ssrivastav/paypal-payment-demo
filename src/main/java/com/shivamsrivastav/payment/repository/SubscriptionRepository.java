package com.shivamsrivastav.payment.repository;

import com.shivamsrivastav.payment.entity.Subscription;
import com.shivamsrivastav.payment.entity.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Subscription Repository
 * 
 * @author Shivam Srivastav
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByPaypalSubscriptionId(String paypalSubscriptionId);

    List<Subscription> findByStatus(SubscriptionStatus status);

    List<Subscription> findBySubscriberEmail(String subscriberEmail);
}
