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

    // @RabbitListener = "kad god stigne poruka u ovaj red, automatski pozovi
    // ovu metodu sa tom porukom (vec pretvorenom iz JSON-a u OrderCreatedEvent
    // objekat, zahvaljujuci Jackson2JsonMessageConverter-u)". Ne pisemo mi
    // petlju koja "ceka" poruke - Spring to radi u pozadini, u posebnoj niti.
    @RabbitListener(queues = RabbitMQConfig.DELIVERY_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        // IDEMPOTENTNO - createDeliveryForOrder() vec proverava da li dostava
        // za ovaj orderId postoji pre nego sto napravi novu (videti DeliveryService).
        deliveryService.createDeliveryForOrder(event.getOrderId());
    }
}
