package com.dostavahrane.deliveryservice.event;

import java.math.BigDecimal;

// ISTOG oblika kao OrderCreatedEvent u order-service (svaki servis ima svoju
// kopiju - objasnjeno detaljno tamo). Jackson ce automatski upariti polja
// iz dolazeceg JSON-a sa ovim poljima po imenu.
public class OrderCreatedEvent {

    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private BigDecimal totalPrice;

    public OrderCreatedEvent() {
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
