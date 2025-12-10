package com.shivamsrivastav.payment.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Webhook Event Entity
 * 
 * Stores received webhook events for audit and idempotency.
 * 
 * @author Shivam Srivastav
 */
@Entity
@Table(name = "webhook_events")
public class WebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", unique = true, nullable = false)
    private String eventId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "processed")
    private boolean processed = false;

    @Column(name = "received_at")
    private LocalDateTime receivedAt = LocalDateTime.now();

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    // ==================== Constructors ====================

    public WebhookEvent() {
    }

    public WebhookEvent(Long id, String eventId, String eventType, String resourceType,
            String resourceId, String payload, boolean processed,
            LocalDateTime receivedAt, LocalDateTime processedAt) {
        this.id = id;
        this.eventId = eventId;
        this.eventType = eventType;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.payload = payload;
        this.processed = processed;
        this.receivedAt = receivedAt != null ? receivedAt : LocalDateTime.now();
        this.processedAt = processedAt;
    }

    // ==================== Getters and Setters ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    // ==================== Builder Pattern ====================

    public static WebhookEventBuilder builder() {
        return new WebhookEventBuilder();
    }

    public static class WebhookEventBuilder {
        private Long id;
        private String eventId;
        private String eventType;
        private String resourceType;
        private String resourceId;
        private String payload;
        private boolean processed = false;
        private LocalDateTime receivedAt = LocalDateTime.now();
        private LocalDateTime processedAt;

        WebhookEventBuilder() {
        }

        public WebhookEventBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public WebhookEventBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public WebhookEventBuilder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public WebhookEventBuilder resourceType(String resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public WebhookEventBuilder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public WebhookEventBuilder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public WebhookEventBuilder processed(boolean processed) {
            this.processed = processed;
            return this;
        }

        public WebhookEventBuilder receivedAt(LocalDateTime receivedAt) {
            this.receivedAt = receivedAt;
            return this;
        }

        public WebhookEventBuilder processedAt(LocalDateTime processedAt) {
            this.processedAt = processedAt;
            return this;
        }

        public WebhookEvent build() {
            return new WebhookEvent(id, eventId, eventType, resourceType, resourceId, payload,
                    processed, receivedAt, processedAt);
        }
    }
}
