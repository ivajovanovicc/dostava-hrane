package com.dostavahrane.orderservice.service;

import com.dostavahrane.orderservice.client.RestaurantClient;
import com.dostavahrane.orderservice.client.UserClient;
import com.dostavahrane.orderservice.dto.MenuItemDto;
import com.dostavahrane.orderservice.dto.RestaurantDto;
import com.dostavahrane.orderservice.dto.UserDto;
import com.dostavahrane.orderservice.exception.ResourceNotFoundException;
import com.dostavahrane.orderservice.exception.ServiceUnavailableException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

// OVA klasa postoji SAMO da bi @CircuitBreaker/@Retry anotacije stvarno
// radile. Da su ove metode ostale unutar OrderService-a, i da ih
// OrderService poziva "sam sebe" (this.getRestaurant(...)), Spring-ov
// AOP proxy bi se ZAOBISAO - to se zove "self-invocation problem",
// vrlo cest Spring bag/gotcha. Kad je poziv IZMEDJU DVA RAZLICITA
// bean-a (OrderService -> ExternalDataService), proxy ispravno
// presrece poziv i anotacije rade kako treba.
@Service
public class ExternalDataService {

    private final UserClient userClient;
    private final RestaurantClient restaurantClient;

    public ExternalDataService(UserClient userClient, RestaurantClient restaurantClient) {
        this.userClient = userClient;
        this.restaurantClient = restaurantClient;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retry(name = "userService")
    public UserDto getUser(Long userId) {
        return userClient.getUserById(userId);
    }

    private UserDto getUserFallback(Long userId, Throwable t) {
        if (isNotFound(t)) {
            throw new ResourceNotFoundException("Korisnik sa id=" + userId + " ne postoji");
        }
        throw new ServiceUnavailableException(
                "user-service je trenutno nedostupan - ne mogu da potvrdim korisnika id=" + userId);
    }

    @CircuitBreaker(name = "restaurantService", fallbackMethod = "getRestaurantFallback")
    @Retry(name = "restaurantService")
    public RestaurantDto getRestaurant(Long restaurantId) {
        return restaurantClient.getRestaurantById(restaurantId);
    }

    private RestaurantDto getRestaurantFallback(Long restaurantId, Throwable t) {
        if (isNotFound(t)) {
            throw new ResourceNotFoundException("Restoran sa id=" + restaurantId + " ne postoji");
        }
        throw new ServiceUnavailableException(
                "restaurant-service je trenutno nedostupan - ne mogu da potvrdim restoran id=" + restaurantId);
    }

    @CircuitBreaker(name = "restaurantService", fallbackMethod = "getMenuItemFallback")
    @Retry(name = "restaurantService")
    public MenuItemDto getMenuItem(Long menuItemId) {
        return restaurantClient.getMenuItemById(menuItemId);
    }

    // PROMENA: baca sad ServiceUnavailableException (nasa, specificna) umesto
    // gole RuntimeException - GlobalExceptionHandler ce ovo prepoznati i
    // vratiti 503 Service Unavailable, ispravniji status za "drugi servis
    // trenutno ne odgovara" nego generican 400/500.
    private MenuItemDto getMenuItemFallback(Long menuItemId, Throwable t) {
        if (isNotFound(t)) {
            throw new ResourceNotFoundException("Jelo sa id=" + menuItemId + " ne postoji");
        }
        throw new ServiceUnavailableException(
                "restaurant-service je trenutno nedostupan - podaci o jelu id=" + menuItemId + " trenutno nisu dostupni");
    }

    private boolean isNotFound(Throwable t) {
        while (t != null) {
            if (t instanceof FeignException.NotFound) {
                return true;
            }
            t = t.getCause();
        }
        return false;
    }
}
