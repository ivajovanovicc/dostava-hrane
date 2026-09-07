package com.dostavahrane.restaurantservice.service;

import com.dostavahrane.restaurantservice.exception.ResourceNotFoundException;
import com.dostavahrane.restaurantservice.model.MenuItem;
import com.dostavahrane.restaurantservice.model.Restaurant;
import com.dostavahrane.restaurantservice.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantService = restaurantService;
    }

    public List<MenuItem> getMenuForRestaurant(Long restaurantId) {
        restaurantService.getRestaurantById(restaurantId);
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public MenuItem addMenuItem(Long restaurantId, MenuItem menuItem) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        menuItem.setRestaurant(restaurant);
        return menuItemRepository.save(menuItem);
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jelo sa id=" + id + " ne postoji"));
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedData) {
        MenuItem existing = getMenuItemById(id);
        existing.setName(updatedData.getName());
        existing.setDescription(updatedData.getDescription());
        existing.setPrice(updatedData.getPrice());
        existing.setAvailable(updatedData.isAvailable());
        return menuItemRepository.save(existing);
    }

    public void deleteMenuItem(Long id) {
        MenuItem existing = getMenuItemById(id);
        menuItemRepository.delete(existing);
    }
}
