package com.dostavahrane.restaurantservice.repository;

import com.dostavahrane.restaurantservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Za sada nam ne treba nista posebno - save/findAll/findById/delete
    // dobijamo besplatno nasledjivanjem JpaRepository-ja.
}
