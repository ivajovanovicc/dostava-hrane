package com.dostavahrane.restaurantservice.repository;

import com.dostavahrane.restaurantservice.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // Malo napredniji "derived query" nego findByEmail od ranije: MenuItem NEMA
    // direktno polje "restaurantId" (Long), vec polje "restaurant" (ceo Restaurant
    // objekat), koji IMA polje "id". Spring Data JPA je dovoljno pametan da
    // "RestaurantId" protumaci kao "idi u polje restaurant, pa uzmi njegov id" i
    // sam sastavi upit koji cita direktno kolonu restaurant_id (bez dodatnog JOIN-a,
    // jer je to bas ta FK kolona koju smo definisale sa @JoinColumn).
    List<MenuItem> findByRestaurantId(Long restaurantId);
}
