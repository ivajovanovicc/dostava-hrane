package com.dostavahrane.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    // @JsonIgnore: kad Jackson pretvara OVAJ objekat u JSON, PRESKOCI ovo polje
    // potpuno. Ovo je ono sto prekida beskonacnu petlju - Order sme da prikaze
    // svoje items, ali kad se prikazuje item, on NECE ponovo prikazati svoj
    // order (koji bi opet prikazao items, pa opet order...).
    @JsonIgnore
    private Order order;

    @NotNull(message = "menuItemId je obavezan")
    private Long menuItemId; // referenca ka jelu iz restaurant-service (druga baza)

    // "Snapshot" polja - kopija naziva i cene U TRENUTKU narudzbine, ne
    // "live" veza ka restaurant-service. Objasnjeno detaljnije kad budemo
    // pisale servis: ako restoran sutra promeni cenu jela, ova porudzbina
    // OSTAJE sa cenom kakva je bila kad je poručena.
    private String menuItemName;
    private BigDecimal price;

    @Positive(message = "Kolicina mora biti veca od nule")
    private int quantity;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
