package com.dostavahrane.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
// "ORDER" je rezervisana rec u SQL-u (koristi se u "ORDER BY") - isti razlog
// kao kod User -> users, moramo eksplicitno da damo drugo ime tabeli.
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "userId je obavezan")
    private Long userId; // samo referenca (Long) ka user-service, NE ceo User objekat - drugi servis, druga baza

    @NotNull(message = "restaurantId je obavezan")
    private Long restaurantId; // isto - referenca ka restaurant-service

    @Enumerated(EnumType.STRING) // upisuje se kao tekst ("CREATED") u bazu, ne kao broj (0,1,2...) - citljivije
    private OrderStatus status;

    private BigDecimal totalPrice; // racuna se na serveru, ne prima se od klijenta

    private LocalDateTime createdAt;

    // OVDE, za razliku od Restaurant->MenuItem, NAMERNO pravimo DVOSMERNU vezu.
    // Zasto sad drugacije? Kod restorana nismo zelele da GET /api/restaurants/{id}
    // automatski vuce SVA jela (nepotrebno, posebno pitamo za jelovnik zasebno).
    // Ovde je suprotno - kad vratimo porudzbinu, PRIRODNO je da odmah vidimo
    // i njene stavke (items) u istom odgovoru, ne zelimo dodatan poziv za to.
    //
    // Problem beskonacne petlje (Order -> items -> svaki item ima Order -> items...)
    // i dalje postoji, ali ga ovaj put resavamo DRUGACIJE - pogledaj @JsonIgnore
    // u OrderItem klasi ispod, na polju "order". To prekida petlju na TOJ strani,
    // dok ovde items I DALJE normalno idu u JSON odgovor.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = OrderStatus.CREATED;
        }
    }

    // Pomocna metoda - kad dodajes stavku porudzbini, OVA metoda odmah postavi
    // i "items.add(item)" I "item.setOrder(this)" u ISTOM koraku. Bez ove metode,
    // lako je zaboraviti da postavis obe strane veze (cest izvor bagova kod
    // dvosmernih JPA veza - jedna strana "zna" za vezu, druga ne).
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
