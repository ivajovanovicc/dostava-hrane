package com.dostavahrane.deliveryservice.controller;

import com.dostavahrane.deliveryservice.dto.AssignCourierRequest;
import com.dostavahrane.deliveryservice.dto.CreateDeliveryRequest;
import com.dostavahrane.deliveryservice.dto.DeliveryStatusUpdateRequest;
import com.dostavahrane.deliveryservice.model.Delivery;
import com.dostavahrane.deliveryservice.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Dostave", description = "Upravljanje dostavama")
@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Lista svih dostava")
    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @Operation(summary = "Dostava po ID-u")
    @ApiResponse(responseCode = "404", description = "Dostava nije pronađena")
    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id);
    }

    @Operation(summary = "Ručno kreiranje dostave za porudžbinu")
    @ApiResponse(responseCode = "201", description = "Dostava kreirana")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery createDelivery(@Valid @RequestBody CreateDeliveryRequest request) {
        return deliveryService.createDeliveryForOrder(request.getOrderId());
    }

    @Operation(summary = "Dostava po ID-u porudžbine")
    @ApiResponse(responseCode = "404", description = "Dostava nije pronađena")
    @GetMapping("/by-order/{orderId}")
    public Delivery getDeliveryByOrderId(@PathVariable Long orderId) {
        return deliveryService.getDeliveryByOrderId(orderId);
    }

    @Operation(summary = "Izmena statusa dostave")
    @ApiResponse(responseCode = "404", description = "Dostava nije pronađena")
    @PutMapping("/{id}/status")
    public Delivery updateStatus(@PathVariable Long id, @Valid @RequestBody DeliveryStatusUpdateRequest request) {
        return deliveryService.updateStatus(id, request.getStatus());
    }

    @Operation(summary = "Dodela kurira dostavi")
    @ApiResponse(responseCode = "404", description = "Dostava nije pronađena")
    @PutMapping("/{id}/assign-courier")
    public Delivery assignCourier(@PathVariable Long id, @Valid @RequestBody AssignCourierRequest request) {
        return deliveryService.assignCourier(id, request.getCourierId());
    }

    @Operation(summary = "Brisanje dostave")
    @ApiResponse(responseCode = "404", description = "Dostava nije pronađena")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
    }
}
