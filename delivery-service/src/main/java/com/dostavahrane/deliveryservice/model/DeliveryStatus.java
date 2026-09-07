package com.dostavahrane.deliveryservice.model;

public enum DeliveryStatus {
    PENDING,     // porudzbina primljena, jos nema dodeljenog kurira
    ASSIGNED,    // kurir dodeljen, jos nije krenuo
    PICKED_UP,   // kurir preuzeo hranu iz restorana
    DELIVERED    // isporuceno
}
