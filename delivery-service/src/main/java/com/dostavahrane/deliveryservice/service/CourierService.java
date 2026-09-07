package com.dostavahrane.deliveryservice.service;

import com.dostavahrane.deliveryservice.exception.ResourceNotFoundException;
import com.dostavahrane.deliveryservice.model.Courier;
import com.dostavahrane.deliveryservice.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {

    private final CourierRepository courierRepository;

    public CourierService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    public Courier getCourierById(Long id) {
        return courierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurir sa id=" + id + " ne postoji"));
    }

    public Courier createCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    public Courier updateCourier(Long id, Courier updatedData) {
        Courier existing = getCourierById(id);
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setPhone(updatedData.getPhone());
        existing.setAvailable(updatedData.isAvailable());
        return courierRepository.save(existing);
    }

    public void deleteCourier(Long id) {
        Courier existing = getCourierById(id);
        courierRepository.delete(existing);
    }
}
