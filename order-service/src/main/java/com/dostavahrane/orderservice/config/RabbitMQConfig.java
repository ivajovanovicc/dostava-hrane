package com.dostavahrane.orderservice.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Ime Exchange-a - MORA se tacno poklapati sa imenom koje ce delivery-service
    // i notification-service koristiti kad se "prijave" (bind-uju) na njega.
    public static final String ORDER_CREATED_EXCHANGE = "order.created.exchange";

    // FanoutExchange = tip "razvodne table" koji IGNORISE routing key i
    // prosledjuje poruku SVAKOM redu koji je na njega prikacen. Ovo je tacno
    // "fan-out" mehanizam iz tvog originalnog plana - JEDNA poruka stize
    // istovremeno u red za delivery-service I u red za notification-service.
    @Bean
    public FanoutExchange orderCreatedExchange() {
        return new FanoutExchange(ORDER_CREATED_EXCHANGE);
    }

    // Podrazumevano, Spring AMQP bi poruke slao u "sirovom" Java binarnom
    // formatu (citljivo samo drugoj Java aplikaciji). Ovim biramo da se sve
    // pretvara u JSON pre slanja - citljivo je (vidices ga kao obican JSON
    // u RabbitMQ dashboard-u), i radilo bi cak i da neki drugi servis NIJE
    // pisan u Javi.
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
