package com.dostavahrane.notificationservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "type je obavezan")
    private String type; // npr. "ORDER_CREATED"

    @NotNull(message = "recipientUserId je obavezan")
    private Long recipientUserId; // referenca ka user-service

    // DODATNO u odnosu na originalni plan (nije bilo u prvobitnom modelu):
    // referenca ka porudzbini zbog koje je obavestenje nastalo. Bez ovoga
    // ne bismo imale nacin da proverimo "da li sam vec napravila obavestenje
    // za ovu porudzbinu" - a to nam treba za IDEMPOTENTNOST (isti mehanizam
    // kao kod delivery-service, primenjen i ovde).
    private Long orderId;

    @NotBlank(message = "message je obavezan")
    private String message;

    private LocalDateTime createdAt;

    private boolean read = false;

    public Notification() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
