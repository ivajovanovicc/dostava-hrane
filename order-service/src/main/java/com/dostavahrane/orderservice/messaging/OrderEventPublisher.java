package com.dostavahrane.orderservice.messaging;

import com.dostavahrane.orderservice.config.RabbitMQConfig;
import com.dostavahrane.orderservice.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreated(OrderCreatedEvent event) {
        // convertAndSend(exchange, routingKey, poruka). Kod FanoutExchange-a,
        // routingKey se IGNORISE (zato prosledjujemo prazan string "") - poruka
        // ionako ide SVIMA koji su prikaceni na ovaj exchange, bez obzira na kljuc.
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_CREATED_EXCHANGE, "", event);
    }
}
