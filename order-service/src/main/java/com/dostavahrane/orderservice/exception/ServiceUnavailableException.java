package com.dostavahrane.orderservice.exception;

// Specificno za order-service - kad DRUGI servis (npr. restaurant-service)
// ne odgovori, i Resilience4j fallback odluci da NE moze da nastavi (videti
// ExternalDataService.getMenuItemFallback). 503 (Service Unavailable) je
// ispravniji HTTP status za ovo nego generican 400 - govori klijentu "problem
// NIJE u tvom zahtevu, problem je sto trenutno ne mogu da dobijem podatke
// koji su mi potrebni od drugog servisa, probaj ponovo malo kasnije".
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
