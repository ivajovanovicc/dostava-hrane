package com.dostavahrane.deliveryservice.dto;

import com.dostavahrane.deliveryservice.model.DeliveryStatus;
import jakarta.validation.constraints.NotNull;

public class DeliveryStatusUpdateRequest {

    @NotNull(message = "status je obavezan")
    private DeliveryStatus status;

    public DeliveryStatusUpdateRequest() {
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
