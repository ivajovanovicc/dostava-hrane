package com.dostavahrane.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @EnableFeignClients je OBAVEZNA da bi Feign interfejsi koje cemo praviti
// (u client/ paketu) uopste bili prepoznati i pretvoreni u prave HTTP pozive.
// Bez ove anotacije, Feign interfejsi bi samo "stajali" tu, neaktivni.
@EnableFeignClients
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
