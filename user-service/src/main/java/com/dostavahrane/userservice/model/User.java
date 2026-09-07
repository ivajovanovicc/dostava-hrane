package com.dostavahrane.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

// @Entity kaze Hibernate-u: "ova klasa predstavlja tabelu u bazi, napravi je automatski".
@Entity
// Bez @Table bi Hibernate tabelu nazvao "USER" po imenu klase - a to je REZERVISANA rec
// u H2 bazi (koristi se u SQL komandama), pa bi kreiranje tabele puklo. Zato eksplicitno
// dajemo drugo ime.
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // baza sama dodeljuje sledeci broj (1, 2, 3...)
    private Long id;

    @NotBlank(message = "Ime je obavezno")
    private String firstName;

    @NotBlank(message = "Prezime je obavezno")
    private String lastName;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email nije u ispravnom formatu")
    @Column(unique = true) // baza ce odbiti dva ista email-a
    private String email;

    @NotBlank(message = "Telefon je obavezan")
    private String phone;

    @NotBlank(message = "Adresa je obavezna")
    private String address; // adresa za dostavu

    private LocalDateTime createdAt;

    // JPA OBAVEZNO trazi prazan konstruktor bez argumenata - Hibernate njega koristi
    // "iza kulisa" da napravi prazan objekat, pa tek onda preko reflection-a popuni polja.
    // Mi ga ne pozivamo rucno, ali mora da postoji.
    public User() {
    }

    // @PrePersist = "pozovi ovu metodu automatski, tacno pre nego sto se objekat prvi put
    // upise u bazu". Zato ne moramo rucno da pisemo setCreatedAt(...) svaki put kad pravimo korisnika.
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- Getteri i setteri ---
    // Namerno su napisani rucno (ne koristimo Lombok biblioteku koja bi ovo generisala
    // automatski) - da ti bude vidljivo i jasno sta tacno postoji u klasi, bez "magije".
    // Hibernate-u ovi getteri/setteri trebaju da bi mogao da cita i upisuje vrednosti polja.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
