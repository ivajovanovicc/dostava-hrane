package com.dostavahrane.deliveryservice.controller;

import com.dostavahrane.deliveryservice.model.Courier;
import com.dostavahrane.deliveryservice.service.CourierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Kuriri", description = "Upravljanje kuririma")
@RestController
@RequestMapping("/api/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @Operation(summary = "Lista svih kurira")
    @GetMapping
    public List<Courier> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    @Operation(summary = "Kurir po ID-u")
    @ApiResponse(responseCode = "404", description = "Kurir nije pronađen")
    @GetMapping("/{id}")
    public Courier getCourierById(@PathVariable Long id) {
        return courierService.getCourierById(id);
    }

    @Operation(summary = "Kreiranje kurira")
    @ApiResponse(responseCode = "201", description = "Kurir kreiran")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier createCourier(@Valid @RequestBody Courier courier) {
        return courierService.createCourier(courier);
    }

    @Operation(summary = "Izmena kurira")
    @ApiResponse(responseCode = "404", description = "Kurir nije pronađen")
    @PutMapping("/{id}")
    public Courier updateCourier(@PathVariable Long id, @Valid @RequestBody Courier courier) {
        return courierService.updateCourier(id, courier);
    }

    @Operation(summary = "Brisanje kurira")
    @ApiResponse(responseCode = "404", description = "Kurir nije pronađen")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourier(@PathVariable Long id) {
        courierService.deleteCourier(id);
    }
}
