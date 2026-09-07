package com.dostavahrane.orderservice.model;

// Enum = fiksan, zatvoren skup mogucih vrednosti. Status porudzbine ne moze
// biti bilo sta (npr. "možda", "nekako") - samo jedno od ovih tacno definisanih stanja.
public enum OrderStatus {
    CREATED,
    CONFIRMED,
    PREPARING,
    IN_DELIVERY,
    DELIVERED,
    CANCELLED
}
