package com.dostavahrane.orderservice.client;

import com.dostavahrane.orderservice.dto.MenuItemDto;
import com.dostavahrane.orderservice.dto.RestaurantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service")
public interface RestaurantClient {

    @GetMapping("/api/restaurants/{id}")
    RestaurantDto getRestaurantById(@PathVariable("id") Long id);

    @GetMapping("/api/menu-items/{id}")
    MenuItemDto getMenuItemById(@PathVariable("id") Long id);
}
