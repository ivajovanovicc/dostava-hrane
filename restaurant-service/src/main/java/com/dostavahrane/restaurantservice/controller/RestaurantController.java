package com.dostavahrane.restaurantservice.controller;

import com.dostavahrane.restaurantservice.model.MenuItem;
import com.dostavahrane.restaurantservice.model.Restaurant;
import com.dostavahrane.restaurantservice.service.MenuItemService;
import com.dostavahrane.restaurantservice.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    public RestaurantController(RestaurantService restaurantService, MenuItemService menuItemService) {
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }

    // --- Osnovni CRUD nad restoranima (isti obrazac kao UserController) ---

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @Valid @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }

    // --- Jelovnik konkretnog restorana ("ugnjezdeni" resurs) ---
    // Putanja sadrzi ID restorana jer jelo UVEK pripada tacno jednom restoranu -
    // logicki ima smisla da to bude deo URL-a: /api/restaurants/3/menu

    @GetMapping("/{id}/menu")
    public List<MenuItem> getMenu(@PathVariable Long id) {
        return menuItemService.getMenuForRestaurant(id);
    }

    @PostMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem addMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(id, menuItem);
    }
}
