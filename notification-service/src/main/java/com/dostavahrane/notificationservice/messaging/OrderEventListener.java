package com.dostavahrane.notificationservice.messaging;

import com.dostavahrane.notificationservice.config.RabbitMQConfig;
import com.dostavahrane.notificationservice.event.OrderCreatedEvent;
import com.dostavahrane.notificationservice.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        notificationService.createNotificationForOrder(event.getOrderId(), event.getUserId());
    }
}
