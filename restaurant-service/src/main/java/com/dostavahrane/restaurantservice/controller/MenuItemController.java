package com.dostavahrane.restaurantservice.controller;

import com.dostavahrane.restaurantservice.model.MenuItem;
import com.dostavahrane.restaurantservice.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// Zasto POSEBAN kontroler za jela, a ne sve u RestaurantController?
// Dodavanje jela ZAHTEVA restaurantId (ide kroz /api/restaurants/{id}/menu, iznad).
// Ali citanje/izmena/brisanje JEDNOG konkretnog jela vec ima svoj sopstveni ID
// i restoran mu vise nije neophodan u putanji - zato dobija svoj kraci URL:
// /api/menu-items/{itemId}
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(id, menuItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }
}
