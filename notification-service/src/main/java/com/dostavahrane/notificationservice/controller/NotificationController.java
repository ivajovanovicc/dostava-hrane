package com.dostavahrane.notificationservice.controller;

import com.dostavahrane.notificationservice.model.Notification;
import com.dostavahrane.notificationservice.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Obaveštenja", description = "Pregled obaveštenja (kreiraju se automatski preko RabbitMQ događaja)")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Lista svih obaveštenja")
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @Operation(summary = "Obaveštenja jednog korisnika")
    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsForUser(@PathVariable Long userId) {
        return notificationService.getNotificationsForUser(userId);
    }

    @Operation(summary = "Označavanje obaveštenja kao pročitanog")
    @ApiResponse(responseCode = "404", description = "Obaveštenje nije pronađeno")
    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }
}
