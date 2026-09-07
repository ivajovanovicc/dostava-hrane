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

    // MORA se tacno poklapati sa imenom exchange-a iz order-service - to je
    // "adresa" na koju se prijavljujemo.
    public static final String ORDER_CREATED_EXCHANGE = "order.created.exchange";

    // Ime NASEG reda - ovo je nas sopstveni "sanduce", RAZLICITO od reda
    // notification-service-a, iako oba slusaju ISTU poruku.
    public static final String DELIVERY_QUEUE = "delivery.order.created.queue";

    // Ponovo deklarisemo isti exchange (RabbitMQ to dozvoljava - ako vec
    // postoji sa istim podesavanjima, samo potvrdi da postoji, ne pravi
    // gresku). Svaki servis koji ucestvuje u komunikaciji preko ovog
    // exchange-a ga deklarise kod sebe.
    @Bean
    public FanoutExchange orderCreatedExchange() {
        return new FanoutExchange(ORDER_CREATED_EXCHANGE);
    }

    // "durable = true" znaci da red PREZIVI restart RabbitMQ-a (ne bi
    // trebalo da izgubimo porudzbine koje cekaju na obradu samo zato sto se
    // RabbitMQ restartovao).
    @Bean
    public Queue deliveryQueue() {
        return new Queue(DELIVERY_QUEUE, true);
    }

    // Binding = "spoji ovaj red sa ovim exchange-om" - tek posle ovoga,
    // poruke koje stignu na exchange STVARNO zavrsavaju u nasem redu.
    @Bean
    public Binding deliveryBinding(Queue deliveryQueue, FanoutExchange orderCreatedExchange) {
        return BindingBuilder.bind(deliveryQueue).to(orderCreatedExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
