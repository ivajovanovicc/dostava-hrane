package com.dostavahrane.restaurantservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne = "MNOGO MenuItem objekata pripada JEDNOM Restaurant-u".
    // @JoinColumn kaze tacno kako se ta veza zove u bazi - napravice se kolona
    // "restaurant_id" u tabeli menu_items, koja je strani kljuc (FK) ka restorana.
    //
    // PROMENA: bilo je fetch = FetchType.LAZY, promenjeno u EAGER.
    // Razlog: kad je LAZY, Hibernate umesto pravog Restaurant objekta stavi
    // "proxy" (privremenu zamenu koja pravi objekat tek kad joj stvarno zatreba).
    // Kad smo direktno vracale MenuItem kao JSON odgovor (GET /api/menu-items/{id}),
    // Jackson nije znao kako da pretvori taj proxy u JSON i pucao je sa
    // "Type definition error... ByteBuddyInterceptor". EAGER resava ovo -
    // odmah ucitava PRAVI Restaurant objekat, nema proxy-ja, Jackson ga
    // normalno serijalizuje. Cena (za ovako mali projekat): svaki put kad se
    // ucita MenuItem, ucita se i njegov Restaurant - zanemarljivo za nas obim.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    // VAZNA ODLUKA (i dobra tema za odbranu): veza je NAMERNO JEDNOSMERNA
    // (unidirectional) - MenuItem zna svoj Restaurant, ali Restaurant NE zna
    // svoju listu MenuItem-a. Da smo dodale "List<MenuItem> items" i u Restaurant,
    // dobile bismo DVOSMERNU vezu - a kad Jackson (biblioteka koja pretvara
    // objekte u JSON) pokusa da serijalizuje Restaurant -> lista MenuItem-a ->
    // svaki MenuItem opet ima Restaurant -> opet lista MenuItem-a... beskonacna
    // petlja i StackOverflowError. Postoje nacini da se to zakrpi (@JsonManagedReference
    // i slicno), ali je jednostavnije i sigurnije izbeci problem u korenu.
    //
    // Kad nam zatreba "sva jela jednog restorana", to cemo dobiti kroz
    // MenuItemRepository (findByRestaurantId), ne kroz navigaciju kroz Restaurant objekat.

    @NotBlank(message = "Naziv jela je obavezan")
    private String name;

    private String description;

    @NotNull(message = "Cena je obavezna")
    @Positive(message = "Cena mora biti veca od nule")
    private BigDecimal price;

    private boolean available = true;

    public MenuItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
