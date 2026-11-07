# Backend — Spasi Zalogaj

Spring Boot REST API sustava Spasi Zalogaj, spojen na PostgreSQL bazu.

## Preduvjeti

| Alat | Verzija | Napomena |
| --- | --- | --- |
| JDK | 21 ili noviji | Projekt se prevodi za Javu 21 (`pom.xml`) |
| PostgreSQL | 18 | Lokalno instaliran; servis mora biti pokrenut |
| Maven | — | Nije potreban, projekt ima Maven Wrapper (`mvnw`) |

Provjera Jave: `java -version`

## 1. Priprema baze (jednom)

Naredbe se pokreću iz **korijena repozitorija**. Ako `psql` nije u PATH-u, koristi punu putanju, npr. `"C:\Program Files\PostgreSQL\18\bin\psql.exe"`.

Stvori bazu:

```
psql -U postgres -c "CREATE DATABASE spasizalogaj;"
```

Pokreni shemu:

```
psql -U postgres -d spasizalogaj -f db/zalogajcici.sql
```

Provjeri da postoji 16 tablica:

```
psql -U postgres -d spasizalogaj -c "\dt"
```

Za ponovno stvaranje baze od nule: `DROP DATABASE spasizalogaj;` pa ponoviti korake.

## 2. Konfiguracija

Aplikacija čita pristupne podatke iz varijabli okruženja. **Lozinke se nikad ne upisuju u `application.properties` niti commitaju.**

| Varijabla | Obavezna | Zadana vrijednost | Opis |
| --- | --- | --- | --- |
| `DB_PASSWORD` | da | — | Lozinka PostgreSQL korisnika |
| `DB_USERNAME` | ne | `postgres` | PostgreSQL korisnik |
| `DB_URL` | ne | `jdbc:postgresql://localhost:5432/spasizalogaj` | JDBC adresa baze |

Shemu ne mijenja aplikacija (`spring.jpa.hibernate.ddl-auto=validate`), nego se primjenjuje skriptom iz koraka 1. Ako se entitet i tablica ne slažu, aplikacija se neće pokrenuti i javit će koji stupac ne odgovara.

## 3. Pokretanje

U mapi `backend`, jedna od varijanti ovisno o terminalu.

**cmd:**
```
set "DB_PASSWORD=tvoja_lozinka"
mvnw spring-boot:run
```

**PowerShell:**
```
$env:DB_PASSWORD="tvoja_lozinka"
.\mvnw spring-boot:run
```

**Git Bash / macOS / Linux:**
```
DB_PASSWORD=tvoja_lozinka ./mvnw spring-boot:run
```

Varijabla vrijedi samo u tom prozoru terminala.

**Iz IDE-a:**
- IntelliJ IDEA: otvoriti mapu `backend`, zatim Run → Edit Configurations → `BackendApplication` → Environment variables: `DB_PASSWORD=...`
- VS Code: najjednostavnije preko terminala kao gore. Za Spring Boot Dashboard varijablu treba dodati pod `"env"` u `.vscode/launch.json` (mapa je u `.gitignore`).

## 4. Provjera

U logu moraju biti:
- `HikariPool-1 - Start completed` — veza s bazom je uspostavljena
- `Started BackendApplication` — aplikacija radi

Otvoriti http://localhost:8080/ — odgovor je `Backend is running`.

Zaustavljanje: `Ctrl+C`.

## Česte greške

| Poruka | Uzrok i rješenje |
| --- | --- |
| `password authentication failed` | `DB_PASSWORD` nije postavljen ili je kriv |
| `database "spasizalogaj" does not exist` | Nije napravljen korak 1 |
| `Connection refused` | PostgreSQL ne radi; Windows: Services → `postgresql-x64-18` → Start |
| `Schema-validation: missing table/column` | Lokalna baza nije na zadnjoj verziji sheme; ponovno stvoriti bazu (korak 1) |
| `Port 8080 was already in use` | Već radi druga instanca; zaustaviti je ili pokrenuti s `--server.port=8081` |
| `The filename, directory name, or volume label syntax is incorrect` | PowerShell naredba upisana u cmd; koristiti `set` |

## Testovi

```
mvnw test
```

Testovi trenutačno traže pokrenutu lokalnu bazu i postavljen `DB_PASSWORD`.