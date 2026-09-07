package com.dostavahrane.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// @EnableEurekaServer je KLJUCNA anotacija - ona ovu obicnu Spring Boot aplikaciju
// pretvara u Eureka server: ukljucuje ugradjeni dashboard (web stranicu) i REST API
// na koji ce nam se ostali servisi "prijavljivati".
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
