package com.dostavahrane.orderservice.dto;

import com.dostavahrane.orderservice.model.OrderItem;
import com.dostavahrane.orderservice.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Ovo je "oblik" odgovora za GET /api/orders/{id}/details - AGREGACIONI
// endpoint. Sadrzi podatke iz TRI izvora spojene u jedan odgovor:
// 1) sama porudzbina (order-service, njegova baza)
// 2) ime i adresa korisnika (user-service, preko Feign-a)
// 3) naziv restorana (restaurant-service, preko Feign-a)
public class OrderDetailsResponse {

    private Long orderId;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;

    private String customerName;
    private String deliveryAddress;

    private String restaurantName;

    private List<OrderItem> items;

    public OrderDetailsResponse() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
