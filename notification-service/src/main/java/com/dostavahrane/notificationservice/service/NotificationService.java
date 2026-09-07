package com.dostavahrane.notificationservice.service;

import com.dostavahrane.notificationservice.exception.ResourceNotFoundException;
import com.dostavahrane.notificationservice.model.Notification;
import com.dostavahrane.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotificationForOrder(Long orderId, Long userId) {
        return notificationRepository.findByOrderId(orderId)
                .orElseGet(() -> {
                    Notification notification = new Notification();
                    notification.setType("ORDER_CREATED");
                    notification.setOrderId(orderId);
                    notification.setRecipientUserId(userId);
                    notification.setMessage("Vasa porudzbina #" + orderId + " je uspesno primljena.");
                    return notificationRepository.save(notification);
                });
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByRecipientUserId(userId);
    }

    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Obavestenje sa id=" + id + " ne postoji"));
        notification.setRead(true);
        return notificationRepository.save(notification);
    }
}
