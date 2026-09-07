package com.dostavahrane.orderservice.client;

import com.dostavahrane.orderservice.dto.MenuItemDto;
import com.dostavahrane.orderservice.dto.RestaurantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Isti princip kao UserClient - "name" mora da se poklapa sa
// spring.application.name iz restaurant-service.
@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{id}")
    RestaurantDto getRestaurantById(@PathVariable("id") Long id);

    // Ovaj metod nam treba da bismo, kad pravimo porudzbinu, proverili da
    // jelo stvarno postoji i da povucemo NJEGOVU TRENUTNU cenu (koju cemo
    // onda "snapshotovati" u OrderItem, kao sto smo vec objasnile).
    @GetMapping("/api/menu-items/{id}")
    MenuItemDto getMenuItemById(@PathVariable("id") Long id);
}
