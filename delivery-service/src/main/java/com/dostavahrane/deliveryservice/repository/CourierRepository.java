package com.dostavahrane.deliveryservice.repository;

import com.dostavahrane.deliveryservice.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
