package com.dostavahrane.deliveryservice.dto;

import jakarta.validation.constraints.NotNull;

public class CreateDeliveryRequest {

    @NotNull(message = "orderId je obavezan")
    private Long orderId;

    public CreateDeliveryRequest() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
