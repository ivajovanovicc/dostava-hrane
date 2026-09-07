package com.dostavahrane.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {

    @NotNull(message = "userId je obavezan")
    private Long userId;

    @NotNull(message = "restaurantId je obavezan")
    private Long restaurantId;

    @NotEmpty(message = "Porudzbina mora imati bar jednu stavku")
    @Valid // KLJUCNO: bez ovoga, @NotNull/@Positive UNUTAR OrderItemRequest-a
    // se ne bi proveravali - @Valid ovde kaze "udji i u SVAKI element liste
    // i proveri i NJEGOVE anotacije, ne samo da lista nije prazna".
    private List<OrderItemRequest> items;

    public OrderRequest() {
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

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
