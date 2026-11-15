# Backend — Spasi Zalogaj

Spring Boot REST API sustava Spasi Zalogaj (Java 21, PostgreSQL, Flyway, Google OAuth 2.0). Kratke upute u [glavnom README-u](../README.md#quick-start)

## Preduvjeti

| Alat | Verzija | Napomena |
| --- | --- | --- |
| JDK | 21 ili noviji | Provjera: `java -version` |
| PostgreSQL | 18 | Lokalno instaliran; servis mora biti pokrenut |
| Maven | — | Nije potreban, projekt ima Maven Wrapper (`mvnw`) |


## 1. Priprema baze (jednom)

Naredbe se pokreću iz **korijena repozitorija**. Ako `psql` nije u PATH-u, koristi punu putanju, npr. `"C:\Program Files\PostgreSQL\18\bin\psql.exe"`.

Stvori bazu:

```
psql -U postgres -c "CREATE DATABASE spasizalogaj;"
```

Tablice se ne stvaraju ručno, **Flyway** ih stvara pri prvom pokretanju aplikacije iz migracija u `src/main/resources/db/migration`. Svaka promjena sheme dodaje se kao nova migracija (`V2__opis.sql`, ...), a postojeće se ne mijenjaju.

## 2. Konfiguracija

Aplikacija čita pristupne podatke iz varijabli okruženja (popis u  [`.env.example`](../.env.example))). **Lozinke se nikad ne upisuju u `application.properties` niti commitaju.**

| Varijabla | Obavezna | Zadana vrijednost | Opis |
| --- | --- | --- | --- |
| `DB_PASSWORD` | da | — | Lozinka PostgreSQL korisnika |
| `GOOGLE_CLIENT_ID` | da | — | ID OAuth klijenta iz Google Cloud Consolea |
| `GOOGLE_CLIENT_SECRET` | da | — | Tajni ključ OAuth klijenta |
| `DB_USERNAME` | ne | `postgres` | PostgreSQL korisnik |
| `DB_URL` | ne | `jdbc:postgresql://localhost:5432/spasizalogaj` | JDBC adresa baze |
| `PORT` | ne | `8080` | Port poslužitelja |


## 3. Pokretanje

U mapi `backend`, jedna od varijanti ovisno o terminalu.

**PowerShell:**
```
$env:DB_PASSWORD="..."; $env:GOOGLE_CLIENT_ID="..."; $env:GOOGLE_CLIENT_SECRET="..."
.\mvnw spring-boot:run
```

**cmd:**
```
set "DB_PASSWORD=..." & set "GOOGLE_CLIENT_ID=..." & set "GOOGLE_CLIENT_SECRET=..."
mvnw spring-boot:run
```

**Git Bash / macOS / Linux:**
```
DB_PASSWORD=... GOOGLE_CLIENT_ID=... GOOGLE_CLIENT_SECRET=... ./mvnw spring-boot:run
```

Varijable vrijede samo u tom prozoru terminala.

**Iz IDE-a:**
- IntelliJ IDEA: otvoriti mapu `backend`, zatim *Run → Edit Configurations → BackendApplication → Environment variables*: `DB_PASSWORD=...;GOOGLE_CLIENT_ID=...;GOOGLE_CLIENT_SECRET=...`
- VS Code: najjednostavnije preko terminala kao gore. Za Spring Boot Dashboard varijable se dodaju pod `"env"` u `.vscode/launch.json` (mapa je u `.gitignore`).

## 4. Testni podaci

Nakon **prvog** pokretanja (kad Flyway stvori tablice), iz korijena repozitorija:

```
psql -U postgres -d spasizalogaj -f db/seed.sql
```

Napomena: Datumi ponuda u seedu računaju se od dana učitavanja, dio ponuda je namjerno istekao, a prodajne ponude 9006–9009 vrijede nekoliko dana.

## 5. Provjera

U logu moraju biti:
- `HikariPool-1 - Start completed` — veza s bazom je uspostavljena
- `Started BackendApplication` — aplikacija radi

Otvoriti http://localhost:8080/api/health — odgovor je `Backend is running`.
Otvaranje http://localhost:8080/oauth2/authorization/google vodi na Google prijavu.
Nakon prijave http://localhost:8080/api/me vraća podatke prijavljenog korisnika.

Adresa http://localhost:8080/ lokalno vraća 404 jer se web-klijent ne poslužuje, lokalno se pokreće samo API. Web-klijent se uz backend poslužuje samo u Docker buildu (produkcija); za lokalni pregled sučelja otvorite `frontend/frontend/index.html` preko Live Servera.

Zaustavljanje: `Ctrl+C`.

## Česte greške

| Poruka | Uzrok i rješenje |
| --- | --- |
| `password authentication failed for user "postgres"` | `DB_PASSWORD` nije postavljen u tom prozoru terminala ili je pogrešan |
| `database "spasizalogaj" does not exist` | Nije napravljen korak 1 |
| `Connection refused` | PostgreSQL ne radi; Windows: *Services* → `postgresql-x64-<verzija>` → *Start* |
| `Found non-empty schema(s) "public" but no schema history table` | Baza je napravljena ručno prije uvođenja Flywaya; obrišite je (`DROP DATABASE spasizalogaj;`) i ponovite korak 1 |
| `Schema-validation: missing table/column` | Entitet i migracije se ne slažu; provjerite je li dodana nova migracija |
| `redirect_uri_mismatch` pri prijavi | U Google Cloud Consoleu nije dodan lokalni redirect URI |
| `Port 8080 was already in use` | Već radi druga instanca; zaustavite je ili pokrenite s `--server.port=8081` |
| `The filename, directory name, or volume label syntax is incorrect` | PowerShell naredba upisana u cmd; koristite `set` 

## Testovi

```
mvnw test
```

Testovi trenutačno traže pokrenutu lokalnu bazu i postavljen `DB_PASSWORD`.
