package com.dostavahrane.orderservice.repository;

import com.dostavahrane.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Za agregacioni endpoint "sve porudzbine jednog korisnika"
    List<Order> findByUserId(Long userId);
}
