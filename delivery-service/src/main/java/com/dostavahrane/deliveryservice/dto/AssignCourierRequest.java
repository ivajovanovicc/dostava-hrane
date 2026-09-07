package com.dostavahrane.deliveryservice.dto;

import jakarta.validation.constraints.NotNull;

public class AssignCourierRequest {

    @NotNull(message = "courierId je obavezan")
    private Long courierId;

    public AssignCourierRequest() {
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
}
