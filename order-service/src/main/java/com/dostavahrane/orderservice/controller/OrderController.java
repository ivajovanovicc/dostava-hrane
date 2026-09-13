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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Porudžbine", description = "Kreiranje i pregled porudžbina (agregacija podataka)")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // --- Osnovni CRUD ---

    @Operation(summary = "Kreiranje porudžbine")
    @ApiResponse(responseCode = "201", description = "Porudžbina kreirana")
    @ApiResponse(responseCode = "503", description = "Zavisni servis trenutno nedostupan")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(summary = "Lista svih porudžbina")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Porudžbina po ID-u")
    @ApiResponse(responseCode = "404", description = "Porudžbina nije pronađena")
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Izmena statusa porudžbine")
    @ApiResponse(responseCode = "404", description = "Porudžbina nije pronađena")
    @PutMapping("/{id}")
    public Order updateOrderStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequest request) {
        return orderService.updateOrderStatus(id, request.getStatus());
    }

    @Operation(summary = "Brisanje porudžbine")
    @ApiResponse(responseCode = "404", description = "Porudžbina nije pronađena")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // --- AGREGACIONI ENDPOINTI ---

    // Spaja podatke iz OVOG servisa + user-service (preko Feign) +
    // restaurant-service (preko Feign) u jedan odgovor.
    @Operation(summary = "Detalji porudžbine (korisnik + restoran + stavke)")
    @ApiResponse(responseCode = "404", description = "Porudžbina nije pronađena")
    @ApiResponse(responseCode = "503", description = "Zavisni servis trenutno nedostupan")
    @GetMapping("/{id}/details")
    public OrderDetailsResponse getOrderDetails(@PathVariable Long id) {
        return orderService.getOrderDetails(id);
    }

    // Putanja je /api/orders/user/{userId} (NE /api/users/{userId}/orders -
    // objasnjeno malopre zasto), sve porudzbine jednog korisnika, obogacene
    // nazivima restorana.
    @Operation(summary = "Porudžbine jednog korisnika")
    @GetMapping("/user/{userId}")
    public List<OrderSummaryResponse> getOrdersForUser(@PathVariable Long userId) {
        return orderService.getOrderSummariesForUser(userId);
    }
}
