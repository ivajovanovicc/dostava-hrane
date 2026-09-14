package com.dostavahrane.deliveryservice.messaging;

import com.dostavahrane.deliveryservice.config.RabbitMQConfig;
import com.dostavahrane.deliveryservice.event.OrderCreatedEvent;
import com.dostavahrane.deliveryservice.service.DeliveryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final DeliveryService deliveryService;

    public OrderEventListener(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RabbitListener(queues = RabbitMQConfig.DELIVERY_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        deliveryService.createDeliveryForOrder(event.getOrderId());
    }
}
