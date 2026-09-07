# Dostava hrane — mikroservisna aplikacija

Projekat iz predmeta **PDS (Projektovanje distribuiranih sistema)**. Sistem za naručivanje hrane iz restorana, razvijen kao mikroservisna arhitektura u Spring Boot-u i Spring Cloud-u.

## Opis projekta

Korisnik bira restoran i jela, pravi porudžbinu, a sistem automatski:
- proverava da korisnik i restoran/jela stvarno postoje (preko Feign poziva ka drugim servisima),
- računa ukupnu cenu (uključujući i dostavnu taksu povučenu sa Config Server-a),
- obaveštava (asinhrono, preko RabbitMQ-a) servise za dostavu i notifikacije da je nova porudžbina stigla.

## Arhitektura — servisi

| Servis | Uloga | Port |
|---|---|---|
| `config-server` | Centralizovana konfiguracija | 8888 |
| `eureka-server` | Registar servisa (service discovery) | 8761 |
| `api-gateway` | Jedina ulazna tačka za klijenta | 8090 |
| `user-service` | Korisnici | 8081 |
| `restaurant-service` | Restorani i jelovnici | 8082 |
| `order-service` | Porudžbine (agregator, Feign, Resilience4j) | 8083 |
| `delivery-service` | Kuriri i dostave | 8084 |
| `notification-service` | Obaveštenja | 8085 |
| RabbitMQ | Broker poruka | 5672 (AMQP), 15672 (web konzola) |

Klijent (Postman, browser) **uvek** gađa samo Gateway, na portu **8090** — pojedinačni portovi servisa se koriste samo direktno za Swagger/Actuator tokom razvoja.

## Tehnologije

**Obavezne:**
Eureka · Spring Cloud Gateway · Spring Data JPA + H2 · Bean Validation · OpenFeign · Resilience4j (Circuit Breaker + Retry) · agregacioni endpoint · Spring Boot Actuator · OpenAPI/Swagger (springdoc)

**Opcione (izabrane 3 od 4):**
- **Config Server** — `delivery.default-fee` (dostavna taksa) učitana sa centralnog mesta, ne iz lokalnog `application.yml`
- **RabbitMQ** — asinhroni `OrderCreated` događaj, fan-out ka `delivery-service` i `notification-service`, sa idempotentnošću na obe strane
- **Docker Compose** — ceo sistem (8 aplikacija + RabbitMQ) diže se jednom komandom

**Bonus:**
- Globalni exception handling (`@RestControllerAdvice`) na svih 5 poslovnih servisa — čisti JSON odgovori (404/409/503) umesto generičkih 500 grešaka
- Druga instanca `restaurant-service`-a (demonstracija load balancing-a preko Eureke i Gateway-a)

## Kako pokrenuti

### Opcija A — Docker Compose (preporučeno, jedna komanda)

Potreban je instaliran [Docker Desktop](https://www.docker.com/products/docker-desktop/).

```
docker compose up --build
```

Prvo pokretanje traje duže (gradi 8 slika). Kad se smiri, sistem je dostupan na `http://localhost:8090`.

Gašenje:
```
docker compose down
```

### Opcija B — Lokalno kroz IntelliJ (za razvoj)

1. Pokreni samo RabbitMQ kroz Docker (ne treba ceo sistem):
   ```
   docker compose up -d rabbitmq
   ```
2. U IntelliJ-u pokreni servise **ovim redosledom**, svaki sačekaj da se podigne pre sledećeg:
   `config-server` → `eureka-server` → `user-service` → `restaurant-service` → `order-service` → `delivery-service` → `notification-service` → `api-gateway`

> Napomena: IntelliJ i Docker Compose se nikad ne pokreću istovremeno (koriste iste portove).

## Testiranje — osnovni tok

Svi zahtevi idu kroz Gateway (`localhost:8090`):

```
POST /api/users              → napravi korisnika
POST /api/restaurants        → napravi restoran
POST /api/restaurants/{id}/menu → dodaj jelo restoranu
POST /api/orders             → napravi porudžbinu
GET  /api/orders/{id}/details → agregacioni prikaz (korisnik + restoran + stavke)
```

Nakon uspešne porudžbine, `delivery-service` i `notification-service` automatski (preko RabbitMQ-a) kreiraju svoje zapise — proveri sa `GET /api/deliveries` i `GET /api/notifications`.

## Pregled endpointa po servisu

| Servis | Endpointi |
|---|---|
| user-service | CRUD `/api/users` |
| restaurant-service | CRUD `/api/restaurants`, `/api/restaurants/{id}/menu`, CRUD `/api/menu-items` |
| order-service | CRUD `/api/orders`, `/api/orders/{id}/details`, `/api/orders/user/{userId}` |
| delivery-service | CRUD `/api/couriers`, CRUD `/api/deliveries`, `/api/deliveries/by-order/{orderId}` |
| notification-service | `/api/notifications`, `/api/notifications/user/{userId}` |

Detaljna, interaktivna dokumentacija svakog servisa: `http://localhost:<port>/swagger-ui.html`

## Monitoring

Svaki servis izlaže Actuator endpointe: `/actuator/health`, `/actuator/info`, `/actuator/metrics`.

RabbitMQ web konzola: `http://localhost:15672` (guest/guest)

Eureka dashboard: `http://localhost:8761`

## Skrinšotovi

_(Ovde dodati: Eureka dashboard sa svim registrovanim servisima, Swagger UI jednog servisa, RabbitMQ konzola sa redovima poruka.)_

## Autor

Iva Jovanović — PDS projekat, 2025/2026.
