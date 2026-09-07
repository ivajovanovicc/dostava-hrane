package com.dostavahrane.orderservice.event;

import java.math.BigDecimal;

// Ovo je oblik PORUKE koja putuje kroz RabbitMQ - slicno DTO-u, ali za
// asinhronu komunikaciju umesto REST poziva. VAZNO: order-service, delivery-
// service i notification-service NEMAJU zajednicki kod - svaki od njih ima
// SVOJU kopiju ove klase (istog oblika). U vecim/pravim sistemima bi ovo
// bilo definisano u zajednickoj biblioteci ili "event shemi" da se ne
// duplira, ali za nas obim je najjednostavnije da svaki servis ima sopstvenu
// kopiju.
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private BigDecimal totalPrice;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, Long userId, Long restaurantId, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
