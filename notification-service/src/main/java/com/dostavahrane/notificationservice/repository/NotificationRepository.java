package com.dostavahrane.notificationservice.repository;

import com.dostavahrane.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientUserId(Long recipientUserId);

    Optional<Notification> findByOrderId(Long orderId);
}
