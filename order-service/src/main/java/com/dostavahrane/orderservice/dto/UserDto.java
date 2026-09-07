package com.dostavahrane.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// DTO (Data Transfer Object) = obicna klasa BEZ @Entity, samo za prenosenje
// podataka preko mreze. Ovo NIJE isti User koji postoji u user-service (tamo
// je pravi JPA entitet, ovde je samo "kopija oblika" njegovog JSON odgovora).
// order-service NEMA pristup bazi user-service-a niti njegovim klasama -
// jedini nacin da sazna nesto o korisniku je HTTP poziv, a odgovor mora
// negde da "sleti" - upravo u ovu klasu.
//
// @JsonIgnoreProperties(ignoreUnknown = true) - ako user-service ikad doda
// NOVO polje u svoj odgovor (npr. "phoneVerified") koje mi ovde nismo
// predvidele, Jackson NECE da baci gresku - samo ce ignorisati ono sto ne
// prepoznaje. Bez ovoga, dodavanje bilo kog polja na DRUGOM servisu bi
// poremetilo NAS servis - lose povezivanje izmedju timova/servisa.
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    public UserDto() {
    }

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
}
