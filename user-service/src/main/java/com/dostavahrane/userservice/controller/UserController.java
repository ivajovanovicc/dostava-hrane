package com.dostavahrane.userservice.controller;

import com.dostavahrane.userservice.model.User;
import com.dostavahrane.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

// @RestController = @Controller + @ResponseBody u jednom.
// Znaci: sve sto metode vrate (npr. User objekat, List<User>) Spring ce SAM
// pretvoriti u JSON i vratiti kao telo HTTP odgovora. Mi ne pisemo rucno JSON.
@Tag(name = "Korisnici", description = "Upravljanje korisnicima")
@RestController
// Sve rute u ovoj klasi pocinju sa "/api/users" - ne moramo to da ponavljamo u svakoj metodi.
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users - vrati sve korisnike
    @Operation(summary = "Lista svih korisnika")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /api/users/5 - {id} iz putanje se automatski "upari" sa parametrom id
    @Operation(summary = "Korisnik po ID-u")
    @ApiResponse(responseCode = "404", description = "Korisnik nije pronađen")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // POST /api/users - @RequestBody uzima JSON iz tela zahteva i pretvara ga u User objekat.
    // @Valid je KLJUCNO ovde - TEK sada anotacije @NotBlank/@Email iz User klase pocinju
    // stvarno da se proveravaju. Ako nesto ne prodje validaciju, Spring sam vrati 400 Bad Request.
    @Operation(summary = "Kreiranje korisnika")
    @ApiResponse(responseCode = "201", description = "Korisnik kreiran")
    @ApiResponse(responseCode = "409", description = "Email već postoji")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 umesto podrazumevanog 200 - "napravljeno je nesto novo"
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT /api/users/5 - izmena postojeceg korisnika
    @Operation(summary = "Izmena korisnika")
    @ApiResponse(responseCode = "404", description = "Korisnik nije pronađen")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE /api/users/5
    @Operation(summary = "Brisanje korisnika")
    @ApiResponse(responseCode = "404", description = "Korisnik nije pronađen")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 - "uspesno, ali nemam sta da ti vratim"
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
