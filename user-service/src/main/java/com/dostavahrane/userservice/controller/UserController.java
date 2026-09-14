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

@Tag(name = "Korisnici", description = "Upravljanje korisnicima")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Lista svih korisnika")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Korisnik po ID-u")
    @ApiResponse(responseCode = "404", description = "Korisnik nije pronađen")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Kreiranje korisnika")
    @ApiResponse(responseCode = "201", description = "Korisnik kreiran")
    @ApiResponse(responseCode = "409", description = "Email već postoji")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 umesto podrazumevanog 200 - "napravljeno je nesto novo"
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Izmena korisnika")
    @ApiResponse(responseCode = "404", description = "Korisnik nije pronađen")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @Operation(summary = "Brisanje korisnika")
    @ApiResponse(responseCode = "404", description = "Korisnik nije pronađen")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 - "uspesno, ali nemam sta da ti vratim"
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
