package com.dostavahrane.deliveryservice.controller;

import com.dostavahrane.deliveryservice.dto.AssignCourierRequest;
import com.dostavahrane.deliveryservice.dto.CreateDeliveryRequest;
import com.dostavahrane.deliveryservice.dto.DeliveryStatusUpdateRequest;
import com.dostavahrane.deliveryservice.model.Delivery;
import com.dostavahrane.deliveryservice.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/{id}")
    public Delivery getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id);
    }

    // Rucno pravljenje dostave (npr. za testiranje) - u praksi ce ovo
    // najcesce raditi RabbitMQ "slusalac" automatski, ne ovaj endpoint.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery createDelivery(@Valid @RequestBody CreateDeliveryRequest request) {
        return deliveryService.createDeliveryForOrder(request.getOrderId());
    }

    @GetMapping("/by-order/{orderId}")
    public Delivery getDeliveryByOrderId(@PathVariable Long orderId) {
        return deliveryService.getDeliveryByOrderId(orderId);
    }

    @PutMapping("/{id}/status")
    public Delivery updateStatus(@PathVariable Long id, @Valid @RequestBody DeliveryStatusUpdateRequest request) {
        return deliveryService.updateStatus(id, request.getStatus());
    }

    @PutMapping("/{id}/assign-courier")
    public Delivery assignCourier(@PathVariable Long id, @Valid @RequestBody AssignCourierRequest request) {
        return deliveryService.assignCourier(id, request.getCourierId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
    }
}
