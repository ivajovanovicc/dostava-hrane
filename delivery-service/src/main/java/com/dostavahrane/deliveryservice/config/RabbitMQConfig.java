package com.dostavahrane.deliveryservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_CREATED_EXCHANGE = "order.created.exchange";
    public static final String DELIVERY_QUEUE = "delivery.order.created.queue";

    @Bean
    public FanoutExchange orderCreatedExchange() {
        return new FanoutExchange(ORDER_CREATED_EXCHANGE);
    }

    @Bean
    public Queue deliveryQueue() {
        return new Queue(DELIVERY_QUEUE, true);
    }

    @Bean
    public Binding deliveryBinding(Queue deliveryQueue, FanoutExchange orderCreatedExchange) {
        return BindingBuilder.bind(deliveryQueue).to(orderCreatedExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
