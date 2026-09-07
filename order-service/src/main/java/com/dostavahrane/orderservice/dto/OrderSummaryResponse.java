package com.dostavahrane.orderservice.dto;

import com.dostavahrane.orderservice.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// "Kraci" agregacioni oblik - za listu porudzbina jednog korisnika, gde
// ne treba SVAKI detalj (stavke, adresa...), samo dovoljno da se prepoznaju
// porudzbine u listi - obogaceno nazivom restorana (koji Order sam po sebi
// ne zna, samo restaurantId).
public class OrderSummaryResponse {

    private Long orderId;
    private String restaurantName;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    public OrderSummaryResponse() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
