package com.dostavahrane.restaurantservice.service;

import com.dostavahrane.restaurantservice.exception.ResourceNotFoundException;
import com.dostavahrane.restaurantservice.model.Restaurant;
import com.dostavahrane.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restoran sa id=" + id + " ne postoji"));
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedData) {
        Restaurant existing = getRestaurantById(id);
        existing.setName(updatedData.getName());
        existing.setAddress(updatedData.getAddress());
        existing.setCuisineType(updatedData.getCuisineType());
        existing.setPhone(updatedData.getPhone());
        existing.setActive(updatedData.isActive());
        return restaurantRepository.save(existing);
    }

    public void deleteRestaurant(Long id) {
        Restaurant existing = getRestaurantById(id);
        restaurantRepository.delete(existing);
    }
}
