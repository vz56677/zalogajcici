
# SpasiZalogaj

Web-aplikacija koja povezuje prodajna mjesta (trgovine, pekare, restorane...) s kupcima i humanitarnim organizacijama, kako bi se višak hrane donirao ili prodao po sniženoj cijeni umjesto bacanja.

**Projekt na kolegiju:** Ovu je aplikaciju razvio studentski tim u sklopu kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu (FER).

## Opis aplikacije
U prodajnim mjestima svakodnevno se baca hrana koja je još sigurna za konzumaciju. Aplikacija SpasiZalogaj prodajnim mjestima omogućuje objavu viška hrane i paketa iznenađenja, kupcima rezervaciju i preuzimanje po sniženoj cijeni, a humanitarnim organizacijama preuzimanje donacija. Registracija i prijava moguća je isključivo preko Google računa (OAuth 2.0).

**Ključne funkcionalnosti**
- Prijava i registracija putem Google(OAuth 2.0) računa uz odabir uloge kupac/prodajno mjesto
- Objava ponude proizvoda (prodavač) i pregled aktivnih ponuda iz baze
- Kreiranje rezervacije uz provjeru i smanjenje zalihe

Za potpuni opseg, zahtjeve, oblikovanje i dokaze ispitivanja pogledajte [docs](docs/Home.md).

<a id="deployment"></a>
## Postavljanje (engl. deployment)

**Javno dostupna aplikacija:** https://spasizalogaj.onrender.com/ 

**Isprobajte:** Otvorite aplikaciju preko gore navedene adrese. Prijavite se Google računom i odaberite ulogu "Kupac" te pregledajte dostupne ponude!

**Demonstracija:** Za prijavu je potreban Google račun, no dok je aplikacija u testnom načinu rada, prijava je moguća samo za račune koji su dodani kao testni korisnici (članovi tima i asistentica). Za pristup se javite voditeljici tima.

**Ograničenja:** Aplikacija je na besplatnom Render planu (nakon duže neaktivnosti se uspava), pa prvo učitavanje može trajati neko vrijeme (do oko minutu).
Plaćanje nije implementirano zbog čega rezervacija ostaje u statusu "ČEKA UPLATU" i ne može se dobiti kod za preuzimanje.

Aplikacija je postavljena na [Render](https://render.com) kao jedna Docker web-usluga. Spring Boot poslužuje i REST API i web-klijent, a podaci se nalaze u upravljanoj PostgreSQL bazi na Neonu. Potrebne varijable okruženja navedene su u [`.env.example`](.env.example), a lokalno pokretanje opisano je u [backend/README.md](backend/README.md).

<a id="quick-start"></a>
## Brzi početak / instalacija
**Preduvjeti:**

| Alat | Verzija | Napomena |
| --- | --- | --- |
| JDK | 21 ili noviji | Provjera: `java -version` |
| PostgreSQL | 16 ili noviji | Lokalno instaliran; servis mora biti pokrenut |
| Git | — | |
| Maven | — | Nije potreban, projekt ima Maven Wrapper (`mvnw`) |


```bash
git clone https://github.com/vz56677/zalogajcici.git
cd zalogajcici
psql -U postgres -c "CREATE DATABASE spasizalogaj;"
cd backend
DB_PASSWORD=... GOOGLE_CLIENT_ID=... GOOGLE_CLIENT_SECRET=... ./mvnw spring-boot:run
```

Nije potrebno tablice stvarati ručno, Flyway ih stvara prilikom prvog pokretanja aplikacije iz migracija u `backend/src/main/resources/db/migration`.
Testni podaci mogu se učitati iz korijena repozitorija: `psql -U postgres -d spasizalogaj -f db/seed.sql` **nakon** prvog pokretanja aplikacije. 

**Adresa:** http://localhost:8080

**Konfiguracija:** Popis varijabli okruženja nalazi se u [`.env.example`](.env.example). Stvarne vrijednosti nikad se ne upisuju u repozitorij.

| Varijabla | Obavezna | Zadana vrijednost | Opis |
| --- | --- | --- | --- |
| `DB_PASSWORD` | da | — | Lozinka PostgreSQL korisnika |
| `GOOGLE_CLIENT_ID` | da | — | ID OAuth klijenta iz Google Cloud Consolea |
| `GOOGLE_CLIENT_SECRET` | da | — | Tajni ključ OAuth klijenta |
| `DB_USERNAME` | ne | `postgres` | PostgreSQL korisnik |
| `DB_URL` | ne | `jdbc:postgresql://localhost:5432/spasizalogaj` | JDBC adresa baze |
| `PORT` | ne | `8080` | Port poslužitelja |

Naredbe za PowerShell i cmd te postavke za IntelliJ opisane su u [backend/README.md](backend/README.md).

**Provjera:** http://localhost:8080/api/health vraća `Backend is running`, a nakon prijave na http://localhost:8080/oauth2/authorization/google adresa http://localhost:8080/api/me vraća podatke prijavljenog korisnika.

## Tehnologije

| Tehnologija ili alat | Uloga u ovom projektu | Razlog uporabe ili važno ograničenje |
| --- | --- | --- |
| Spring Boot 4 (Java 21) | REST API, poslovna pravila i posluživanje web-klijenta | Jedna aplikacija poslužuje i API i statički klijent, pa nema zasebnog postavljanja klijenta. |
| Spring Security OAuth2 Client | Prijava Google računom | Lozinke se ne pohranjuju (NF-03); aplikacija ovisi o dostupnosti Googlea. |
| PostgreSQL + Flyway | Pohrana korisnika, ponuda i rezervacija; verzionirane migracije sheme | Relacijska ograničenja štite dosljednost zalihe i rezervacija; svaka promjena sheme ide kao nova migracija. |
| JavaScript (ES moduli), HTML, CSS | Web-klijent prilagođen računalu i mobitelu | Bez build koraka i dodatnih okvira; svi pozivi API-ja nalaze se u `api.js`. |
| Docker + Render | Javno postavljanje aplikacije | Ograničenje: besplatni plan se uspava nakon neaktivnosti. |

<a id="team-members"></a>
## Članovi tima

| Član | Glavna uloga ili doprinos |
| --- | --- |
| Lucija Perica | Voditeljica tima; web-klijent |
| Ena Špoler | Ponude i zaliha (backend); organizacija zadataka |
| Domagoj Radman | Prijava putem Googlea i uloge korisnika |
| Gabrijel Dumbović | Inicijalizacija backenda; arhitektura |
| Karlo List | Model podataka i baza |
| Viktor Žigić | Obrasci uporabe; web-klijent |
| Klara Ferišak | Rezervacije (backend); postavljanje aplikacije |

Uloge se tijekom projekta mogu mijenjati. Repozitorij i dogovoreni alat za praćenje zadataka (engl. task tracker) služe kao dodatni dokazi timskog rada.

## Doprinos projektu

Zadaci se evidentiraju kao [GitHub Issues](https://github.com/vz56677/zalogajcici/issues) raspoređeni po milestoneima (M1, M2, M3). Svaki zadatak razvija se na vlastitoj grani i u `master` ulazi putem pull requesta.

<a id="license"></a>

**Status licence:** Projekt (izvorni kod i dokumentacija) objavljen je pod licencom [Creative Commons Imenovanje-Nekomercijalno-Dijeli pod istim uvjetima 4.0 međunarodna (CC BY-NC-SA 4.0)](LICENSE). Dopušteno je dijeljenje i prerada uz navođenje autora, samo u nekomercijalne svrhe i pod istom licencom.

Dokumentacijski predložak kolegija sadrži otvorene obrazovne sadržaje (engl. Open Educational Resources, OER) i licenciran je licencom Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International. Ta licenca **ne određuje automatski licencu studentske aplikacije**; tim treba jasno navesti licencu svojeg projekta u prethodnom retku i datoteci `LICENSE`, ako je primjenjivo.

Kod trećih strana, skupovi podataka, slike, ikone i drugi sadržaji zadržavaju vlastite licence i uvjete uporabe. 

<a id="ai-usage"></a>
## Uporaba umjetne inteligencije [![AI Usage: Disclosed](https://img.shields.io/badge/AI%20Usage-Disclosed-blue.svg)](https://www.fer.unizg.hr/_download/repository/Policy%20on%20the%20appropriate%20use%20of%20artificial%20intelligence%20at%20the%20faculty%20of%20electrical%20engineering%20and%20computing%5B1%5D.pdf)

Ovaj projekt slijedi FER-ova [pravila o primjerenoj uporabi umjetne inteligencije](https://www.fer.unizg.hr/_download/repository/Policy%20on%20the%20appropriate%20use%20of%20artificial%20intelligence%20at%20the%20faculty%20of%20electrical%20engineering%20and%20computing%5B1%5D.pdf).

**Izjava:** Članovi tima koristili su Claude (Anthropic) za pregled dokumentacije i koda, objašnjenja i prijedloge ispravaka. Sav kod i tekst napisali su ili pregledali članovi tima te mogu objasniti vlastiti doprinos.

<a id="code-of-conduct-and-support"></a>
## Kodeks ponašanja i podrška [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)

Od članova tima očekuje se pridržavanje **Kodeksa ponašanja studenata** Fakulteta elektrotehnike i računarstva Sveučilišta u Zagrebu, pravila timskog rada na kolegiju i [Etičkog kodeksa IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html). 

---

Struktura dokumentacije prilagođena je predlošku kolegija Programsko inženjerstvo FER-a autora [Vlade Sruka](https://www.fer.unizg.hr/vlado.sruk), licenciranom pod [CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/). Aplikaciju i njezin sadržaj izradio je gore navedeni studentski tim.
