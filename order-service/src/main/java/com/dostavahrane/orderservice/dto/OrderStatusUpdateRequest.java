package com.dostavahrane.orderservice.dto;

import com.dostavahrane.orderservice.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

// Telo zahteva za PUT /api/orders/{id} - jedino sto realno menjamo na
// postojecoj porudzbini je njen STATUS (npr. CONFIRMED -> PREPARING).
// Ne dozvoljavamo menjanje stavki posle kreiranja - poslovno nema smisla
// (i komplikovalo bi ponovni obracun cene).
public class OrderStatusUpdateRequest {

    @NotNull(message = "status je obavezan")
    private OrderStatus status;

    public OrderStatusUpdateRequest() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
