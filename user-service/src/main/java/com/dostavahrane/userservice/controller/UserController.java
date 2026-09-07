package com.dostavahrane.userservice.controller;

import com.dostavahrane.userservice.model.User;
import com.dostavahrane.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = @Controller + @ResponseBody u jednom.
// Znaci: sve sto metode vrate (npr. User objekat, List<User>) Spring ce SAM
// pretvoriti u JSON i vratiti kao telo HTTP odgovora. Mi ne pisemo rucno JSON.
@RestController
// Sve rute u ovoj klasi pocinju sa "/api/users" - ne moramo to da ponavljamo u svakoj metodi.
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users - vrati sve korisnike
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/5 - {id} iz putanje se automatski "upari" sa parametrom id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // POST /api/users - @RequestBody uzima JSON iz tela zahteva i pretvara ga u User objekat.
    // @Valid je KLJUCNO ovde - TEK sada anotacije @NotBlank/@Email iz User klase pocinju
    // stvarno da se proveravaju. Ako nesto ne prodje validaciju, Spring sam vrati 400 Bad Request.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 umesto podrazumevanog 200 - "napravljeno je nesto novo"
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT /api/users/5 - izmena postojeceg korisnika
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE /api/users/5
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 - "uspesno, ali nemam sta da ti vratim"
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
