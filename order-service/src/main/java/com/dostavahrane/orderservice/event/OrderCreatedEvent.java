package com.dostavahrane.orderservice.event;

import java.math.BigDecimal;

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
