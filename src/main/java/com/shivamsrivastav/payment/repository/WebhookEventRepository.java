package com.shivamsrivastav.payment.repository;

import com.shivamsrivastav.payment.entity.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * WebhookEvent Repository
 * 
 * @author Shivam Srivastav
 */
@Repository
public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {

    Optional<WebhookEvent> findByEventId(String eventId);

    List<WebhookEvent> findByEventType(String eventType);

    List<WebhookEvent> findByProcessedFalse();

    boolean existsByEventId(String eventId);
}
