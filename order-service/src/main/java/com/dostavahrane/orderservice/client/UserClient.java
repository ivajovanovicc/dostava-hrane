package com.dostavahrane.orderservice.client;

import com.dostavahrane.orderservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Ovo je INTERFEJS, kao i UserRepository nekad davno - opet nema tela metoda,
// a opet ce raditi. Ovaj put ne Spring Data JPA nego OPENFEIGN generise
// pravu implementaciju iza kulisa.
//
// name = "user-service" MORA TACNO da se poklopi sa "spring.application.name"
// iz user-service application.yml-a. Feign ce pitati EUREKU "gde je trenutno
// user-service?" (moze biti bilo koji port, bilo koja masina) i sam sastaviti
// pravi HTTP poziv - MI NIGDE ne pisemo "localhost:8081".
@FeignClient(name = "user-service")
public interface UserClient {

    // Ova linija KOPIRA potpis endpointa koji vec postoji u UserController-u
    // (GET /api/users/{id}). Kad god pozovemo userClient.getUserById(5L) u
    // nasem kodu, Feign ce iza kulisa poslati pravi HTTP GET zahtev na
    // /api/users/5 ka trenutnoj adresi user-service-a, sacekati odgovor,
    // i automatski ga pretvoriti (Jackson-om) u UserDto objekat - izgleda
    // KAO OBICAN POZIV METODE, iako u pozadini ide preko mreze.
    @GetMapping("/api/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
