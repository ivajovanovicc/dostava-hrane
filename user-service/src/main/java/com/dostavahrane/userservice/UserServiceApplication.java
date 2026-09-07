package com.dostavahrane.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication kaze Spring-u: "ovo je pocetna tacka, skeniraj ovaj paket
// i sve njegove podpakete i pronadji sve moje @Component/@Service/@Repository/@RestController klase".
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
