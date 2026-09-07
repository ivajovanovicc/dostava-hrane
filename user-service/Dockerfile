# === FAZA 1: "Build" faza - PRAVI .jar fajl od izvornog koda ===
# Koristimo sliku koja vec ima i Maven i Javu 21 instalirane - ne moramo
# mi da ih instaliramo, Docker to preuzme umesto nas.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Prvo kopiramo SAMO pom.xml i preuzmemo zavisnosti - Docker ume da "zapamti"
# ovaj korak (kesira ga) ako se pom.xml ne promeni, pa sledeci build bude brzi
# (ne mora ponovo da skida sve biblioteke ako smo promenile samo Java kod).
COPY pom.xml .
RUN mvn dependency:go-offline

# Tek sada kopiramo izvorni kod i pravimo .jar. "-DskipTests" preskace
# testove pri build-u (mi ionako nemamo napisane testove, pa bi ovo
# nepotrebno usporavalo svaki build).
COPY src ./src
RUN mvn clean package -DskipTests

# === FAZA 2: "Run" faza - MANJA slika, samo za POKRETANJE (bez Mavena) ===
# Ne treba nam Maven da BI aplikacija radila, samo da bi se IZGRADILA (Faza 1).
# Zato u finalnoj slici koristimo mnogo manju sliku - samo Java (JRE), bez
# Mavena i ostalih alata za razvoj. Manja slika = brze preuzimanje/pokretanje.
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Kopira SAMO gotov .jar fajl iz Faze 1 (ne ceo izvorni kod, ne Maven cache) -
# finalna slika je zato mala i cista.
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

# Komanda koja se izvrsava kad se kontejner pokrene - isto sto i kad bi
# ti rucno pokrenula "java -jar app.jar" u terminalu.
ENTRYPOINT ["java", "-jar", "app.jar"]
