package com.dostavahrane.restaurantservice.controller;

import com.dostavahrane.restaurantservice.model.MenuItem;
import com.dostavahrane.restaurantservice.model.Restaurant;
import com.dostavahrane.restaurantservice.service.MenuItemService;
import com.dostavahrane.restaurantservice.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Restorani", description = "Upravljanje restoranima i jelovnicima")
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

    @Operation(summary = "Lista svih restorana")
    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @Operation(summary = "Restoran po ID-u")
    @ApiResponse(responseCode = "404", description = "Restoran nije pronađen")
    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @Operation(summary = "Kreiranje restorana")
    @ApiResponse(responseCode = "201", description = "Restoran kreiran")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @Operation(summary = "Izmena restorana")
    @ApiResponse(responseCode = "404", description = "Restoran nije pronađen")
    @PutMapping("/{id}")
    public Restaurant updateRestaurant(@PathVariable Long id, @Valid @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(id, restaurant);
    }

    @Operation(summary = "Brisanje restorana")
    @ApiResponse(responseCode = "404", description = "Restoran nije pronađen")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }

    // --- Jelovnik konkretnog restorana ("ugnjezdeni" resurs) ---
    // Putanja sadrzi ID restorana jer jelo UVEK pripada tacno jednom restoranu -
    // logicki ima smisla da to bude deo URL-a: /api/restaurants/3/menu

    @Operation(summary = "Jelovnik restorana")
    @ApiResponse(responseCode = "404", description = "Restoran nije pronađen")
    @GetMapping("/{id}/menu")
    public List<MenuItem> getMenu(@PathVariable Long id) {
        return menuItemService.getMenuForRestaurant(id);
    }

    @Operation(summary = "Dodavanje jela u jelovnik")
    @ApiResponse(responseCode = "201", description = "Jelo dodato")
    @ApiResponse(responseCode = "404", description = "Restoran nije pronađen")
    @PostMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem addMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem menuItem) {
        return menuItemService.addMenuItem(id, menuItem);
    }
}
