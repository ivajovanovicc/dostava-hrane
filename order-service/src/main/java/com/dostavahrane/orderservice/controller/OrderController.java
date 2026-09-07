package com.dostavahrane.orderservice.controller;

import com.dostavahrane.orderservice.dto.OrderDetailsResponse;
import com.dostavahrane.orderservice.dto.OrderRequest;
import com.dostavahrane.orderservice.dto.OrderStatusUpdateRequest;
import com.dostavahrane.orderservice.dto.OrderSummaryResponse;
import com.dostavahrane.orderservice.model.Order;
import com.dostavahrane.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // --- Osnovni CRUD ---

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequest request) {
        return orderService.updateOrderStatus(id, request.getStatus());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // --- AGREGACIONI ENDPOINTI ---

    // Spaja podatke iz OVOG servisa + user-service (preko Feign) +
    // restaurant-service (preko Feign) u jedan odgovor.
    @GetMapping("/{id}/details")
    public OrderDetailsResponse getOrderDetails(@PathVariable Long id) {
        return orderService.getOrderDetails(id);
    }

    // Putanja je /api/orders/user/{userId} (NE /api/users/{userId}/orders -
    // objasnjeno malopre zasto), sve porudzbine jednog korisnika, obogacene
    // nazivima restorana.
    @GetMapping("/user/{userId}")
    public List<OrderSummaryResponse> getOrdersForUser(@PathVariable Long userId) {
        return orderService.getOrderSummariesForUser(userId);
    }
}
