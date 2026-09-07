package com.dostavahrane.userservice.repository;

import com.dostavahrane.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Ovo je INTERFEJS, ne klasa - nema tela metoda, a opet ce sve raditi. Kako?
// Kad naslediš JpaRepository<User, Long> (User = tip entiteta, Long = tip njegovog ID-a),
// Spring Data JPA u pozadini SAM napravi pravu implementaciju ove interfejs-a (proxy klasu)
// i ubaci nam gotove metode: save(), findById(), findAll(), deleteById(), count()...
// Mi ne pisemo NIJEDAN SQL upit za ovo - Spring ga generise sam.
public interface UserRepository extends JpaRepository<User, Long> {

    // Ovo je "derived query" (izvedeni upit) - Spring PROCITA ime metode,
    // prepozna sablon "findBy" + ime polja (Email), i sam sastavi upit
    // (otprilike: SELECT * FROM users WHERE email = ?).
    // Optional<User> znaci "moze da vrati korisnika, a moze i da ne nadje nijednog - nemoj da puca, samo mi reci da li ga ima".
    Optional<User> findByEmail(String email);

}
