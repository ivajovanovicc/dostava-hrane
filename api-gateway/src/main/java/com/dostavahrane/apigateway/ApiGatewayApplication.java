package com.dostavahrane.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Nema posebne anotacije ovde (za razliku od @EnableEurekaServer kod eureka-server) -
// samo prisustvo spring-cloud-starter-gateway-server-webflux zavisnosti je dovoljno
// da Spring Boot sam ukljuci ceo Gateway mehanizam.
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
