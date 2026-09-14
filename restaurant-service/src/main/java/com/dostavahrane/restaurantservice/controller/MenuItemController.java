package com.dostavahrane.restaurantservice.controller;

import com.dostavahrane.restaurantservice.model.MenuItem;
import com.dostavahrane.restaurantservice.service.MenuItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Jela", description = "Pojedinačna jela iz jelovnika")
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @Operation(summary = "Jelo po ID-u")
    @ApiResponse(responseCode = "404", description = "Jelo nije pronađeno")
    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @Operation(summary = "Izmena jela")
    @ApiResponse(responseCode = "404", description = "Jelo nije pronađeno")
    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(id, menuItem);
    }

    @Operation(summary = "Brisanje jela")
    @ApiResponse(responseCode = "404", description = "Jelo nije pronađeno")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }
}
