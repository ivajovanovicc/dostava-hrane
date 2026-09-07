package com.dostavahrane.deliveryservice.repository;

import com.dostavahrane.deliveryservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    // Za endpoint "dostava za datu porudzbinu" - GET /api/deliveries/by-order/{orderId}
    Optional<Delivery> findByOrderId(Long orderId);
}
