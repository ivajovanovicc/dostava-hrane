package com.dostavahrane.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

// @EnableConfigServer pretvara ovu obicnu Spring Boot aplikaciju u pravi
// Config Server - dobija REST API (npr. GET /order-service/default) koji
// vraca YAML/JSON sa podesavanjima za servis ciji naziv navedes u putanji.
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
