package com.dostavahrane.deliveryservice.controller;

import com.dostavahrane.deliveryservice.model.Courier;
import com.dostavahrane.deliveryservice.service.CourierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping
    public List<Courier> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    @GetMapping("/{id}")
    public Courier getCourierById(@PathVariable Long id) {
        return courierService.getCourierById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier createCourier(@Valid @RequestBody Courier courier) {
        return courierService.createCourier(courier);
    }

    @PutMapping("/{id}")
    public Courier updateCourier(@PathVariable Long id, @Valid @RequestBody Courier courier) {
        return courierService.updateCourier(id, courier);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourier(@PathVariable Long id) {
        courierService.deleteCourier(id);
    }
}
