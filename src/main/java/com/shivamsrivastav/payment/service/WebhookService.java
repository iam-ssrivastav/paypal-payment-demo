package com.shivamsrivastav.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shivamsrivastav.payment.entity.WebhookEvent;
import com.shivamsrivastav.payment.repository.WebhookEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Webhook Service
 * 
 * Handles incoming PayPal webhooks.
 * 
 * @author Shivam Srivastav
 */
@Service
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);

    private final WebhookEventRepository webhookEventRepository;
    private final ObjectMapper objectMapper;

    public WebhookService(WebhookEventRepository webhookEventRepository, ObjectMapper objectMapper) {
        this.webhookEventRepository = webhookEventRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Process incoming webhook payload.
     */
    @Transactional
    public void processWebhook(String payload) {
        try {
            JsonNode root = objectMapper.readTree(payload);
            String eventId = root.path("id").asText();
            String eventType = root.path("event_type").asText();
            String resourceType = root.path("resource_type").asText();

            // 1. Idempotency Check
            if (webhookEventRepository.findByEventId(eventId).isPresent()) {
                log.info("Webhook event already processed: {}", eventId);
                return;
            }

            String resourceId = "";
            if (root.has("resource") && root.path("resource").has("id")) {
                resourceId = root.path("resource").path("id").asText();
            }

            log.info("Processing webhook: eventId={}, type={}, resourceId={}",
                    eventId, eventType, resourceId);

            // 2. Save event log
            WebhookEvent event = WebhookEvent.builder()
                    .eventId(eventId)
                    .eventType(eventType)
                    .resourceType(resourceType)
                    .resourceId(resourceId)
                    .payload(payload)
                    .receivedAt(LocalDateTime.now())
                    .build();

            event = webhookEventRepository.save(event);

            // 3. Handle specific events
            handleEvent(eventType, root.path("resource"));

            // 4. Mark as processed
            event.setProcessed(true);
            event.setProcessedAt(LocalDateTime.now());
            webhookEventRepository.save(event);

        } catch (Exception e) {
            log.error("Error processing webhook payload: {}", e.getMessage(), e);
            throw new RuntimeException("Webhook processing failed", e);
        }
    }

    private void handleEvent(String eventType, JsonNode resource) {
        switch (eventType) {
            case "PAYMENT.CAPTURE.COMPLETED":
                handlePaymentCaptureCompleted(resource);
                break;
            case "PAYMENT.CAPTURE.REFUNDED":
                handlePaymentCaptureRefunded(resource);
                break;
            case "PAYMENT.AUTHORIZATION.VOIDED":
                handleAuthorizationVoided(resource);
                break;
            case "BILLING.SUBSCRIPTION.ACTIVATED":
                handleSubscriptionActivated(resource);
                break;
            case "BILLING.SUBSCRIPTION.CANCELLED":
                handleSubscriptionCancelled(resource);
                break;
            default:
                log.info("Unhandled webhook event type: {}", eventType);
        }
    }

    private void handlePaymentCaptureCompleted(JsonNode resource) {
        String captureId = resource.path("id").asText();
        String amount = resource.path("amount").path("total").asText();
        log.info("Payment captured via webhook: id={}, amount={}", captureId, amount);
        // Logic to update payment status in DB if not already updated
    }

    private void handlePaymentCaptureRefunded(JsonNode resource) {
        String refundId = resource.path("id").asText();
        String captureId = resource.path("capture_id").asText();
        log.info("Payment refunded via webhook: refundId={}, captureId={}", refundId, captureId);
        // Logic to update payment status
    }

    private void handleAuthorizationVoided(JsonNode resource) {
        String authId = resource.path("id").asText();
        log.info("Authorization voided via webhook: id={}", authId);
        // Logic to update payment status
    }

    private void handleSubscriptionActivated(JsonNode resource) {
        String subId = resource.path("id").asText();
        log.info("Subscription activated: id={}", subId);
        // Logic to activate subscription
    }

    private void handleSubscriptionCancelled(JsonNode resource) {
        String subId = resource.path("id").asText();
        log.info("Subscription cancelled: id={}", subId);
        // Logic to cancel subscription
    }
}
