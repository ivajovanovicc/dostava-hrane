package com.dostavahrane.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Ovo je oblik JEDNE stavke u telu zahteva kad klijent PRAVI porudzbinu.
// Primeti - nema "name" ni "price" ovde! Klijent salje SAMO id jela i
// kolicinu - cenu i naziv MI sami povlacimo sa restaurant-service, ne
// verujemo klijentu da nam posalje tacnu cenu (mogao bi da je izmeni i
// prevari nas).
public class OrderItemRequest {

    @NotNull(message = "menuItemId je obavezan")
    private Long menuItemId;

    @Positive(message = "Kolicina mora biti veca od nule")
    private int quantity;

    public OrderItemRequest() {
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
