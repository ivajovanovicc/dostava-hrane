package com.dostavahrane.deliveryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "orderId je obavezan")
    private Long orderId; // referenca ka porudzbini u order-service (druga baza, druga usluga)

    // NAPOMENA: courierId je NAMERNO obican Long, ne prava JPA veza (@ManyToOne)
    // ka Courier klasi - iako Courier ZAISTA zivi u istoj bazi (za razliku od
    // orderId). Ovo je uskladjeno sa originalnim planom i drzi model jednostavnim -
    // mogla je i da bude prava veza, ali za nas obim projekta nije neophodno.
    private Long courierId; // moze biti null dok kurir nije dodeljen

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime assignedAt;
    private LocalDateTime deliveredAt;

    public Delivery() {
    }

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = DeliveryStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
}
