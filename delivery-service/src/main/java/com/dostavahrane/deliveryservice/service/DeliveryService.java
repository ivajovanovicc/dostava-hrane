package com.dostavahrane.deliveryservice.service;

import com.dostavahrane.deliveryservice.exception.ResourceNotFoundException;
import com.dostavahrane.deliveryservice.model.Delivery;
import com.dostavahrane.deliveryservice.model.DeliveryStatus;
import com.dostavahrane.deliveryservice.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery createDeliveryForOrder(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseGet(() -> {
                    Delivery delivery = new Delivery();
                    delivery.setOrderId(orderId);
                    return deliveryRepository.save(delivery);
                });
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dostava sa id=" + id + " ne postoji"));
    }

    public Delivery getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Dostava za porudzbinu id=" + orderId + " ne postoji"));
    }

    public Delivery updateStatus(Long id, DeliveryStatus newStatus) {
        Delivery delivery = getDeliveryById(id);
        delivery.setStatus(newStatus);
        if (newStatus == DeliveryStatus.DELIVERED) {
            delivery.setDeliveredAt(LocalDateTime.now());
        }
        return deliveryRepository.save(delivery);
    }

    public Delivery assignCourier(Long id, Long courierId) {
        Delivery delivery = getDeliveryById(id);
        delivery.setCourierId(courierId);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setAssignedAt(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        Delivery delivery = getDeliveryById(id);
        deliveryRepository.delete(delivery);
    }
}
