# 2. Analiza zahtjeva

## 2.1 Funkcijski zahtjevi



| ID | Opis | Prioritet | Izvor | Kriteriji prihvaćanja |
| --- | --- | --- | --- | --- |
| F-001 | Sustav omogućuje neregistriranom korisniku registraciju računa i registriranom prijavu pomoću vanjskih servisa za autentifikaciju | Visok | Odobreni zadatak | Neregistrirani korisnici mogu se registrirati i napraviti račun u sustavu pomoću vanjskih servisa za autentifikaciju. Kod prve registracije omogućuje se odabir uloge (Kupac, humanitarna organizacija ili prodajno mjesto) i unos podataka profila (ime, email, kontakt za kupca i humanitarnu organizaciju, naziv, vrstu, adresu, kontakt za prodajno mjesto). Korisnicima koji imaju postojeći korisnički račun omogućena je prijava u isti. Registrirani korisnik nakon prijave dolazi na početnu stranicu svoje uloge, dok se blokiranom korisniku prijava odbija. |
| F-002 | Sustav omogućuje pregled svih ponuda hrane i paketa iznenađenja trenutno dostupnih kupcima (izuzev ponuda namijenjenih humanitarnim organizacijama). | Visok | Odobreni zadatak | Kupac na početnoj stranici može pregledavati ponude objavljene od strane prodajnih mjesta s prikazom naziva, cijene i roka vremena za preuzimanje, osim onih namijenjenih isključivo humanitarnim organizacijama. |
| F-003 | Sustav omogućuje prodajnim mjestima plaćanje članarine za korištenje platforme | Visok | Odobreni zadatak | Prodajna mjesta mogu plaćati mjesečnu ili godišnju članarinu za korištenje platforme putem PayPala ili kreditnih kartica korištenjem vanjskih servisa |
| F-004 | Sustav omogućuje prodajnim mjestima izradu javnih profila prodajnih mjesta i upravljajne istim | Srednji | Odobreni zadatak | Prodajna mjesta imaju javne profile na kojima su dostupne njihove osnovne informacije (naziv, adresa, kontakt, radno vrijeme, logo te popis trenutno dostupnih proizvoda) |
| F-005 | Sustav omogućuje prodajnim mjestima objavu ponude proizvoda. | Visok | Odobreni zadatak | Prodajna mjesta mogu objaviti ponudu hrane u kojoj je naveden naziv proizvoda, kategorija, količina, redovna i snižena cijena/informacija o doniranju, rok upotrebe i vrijeme do kojeg je moguće preuzimanje. Također je moguća objava fotografija proizvoda ili paketa. |
| F-006 | Sustav omogućuje prodajnim mjestima objavu ponude „Paketa iznenađenja“. | Srednji | Odobreni zadatak | Prodajna mjesta unose okvirni sadržaj, ukupnu vrijednost, prodajnu cijenu i količinu dostupnih paketa te vrijeme do kojeg je moguće preuzimanje, bez navođenja točnih artikala u paketu. |
| F-007 | Sustav kupcu prikazuje lokacije prodajnih mjesta i udaljenosti od korisnika | Srednji | Odobreni zadatak | Kupcima je vidljiva točna lokacija prodajnog mjesta te je izračunata udaljenost od istog. Ponude i prodajna mjesta prikazuju se kao oznake na karti (Google Maps) |
| F-008 | Sustav omogućuje kupcu rezervaciju i plaćanje proizvoda | Visok | Odobreni zadatak | Kupcima je omogućena rezervacija jednog ili više dostupnih proizvoda ili paketa te odabir i plaćanje proizvoda koje su rezervirali. Mogući načini plaćanja su PayPal, Kreditna kartica korištenjem vanjskih servisa za plaćanje. |
| F-009 | Sustav kupcu generira kod za preuzimanje i prodajnom mjestu omogućuje njegovu verifikaciju | Visok | Odobreni zadatak | Nakon uspješne rezervacije kupcima se na e-poštu šalje potvrda i jedinstveni kod koji omogućuje preuzimanje proizvoda. Prilikom dolaska kupca u prodajno mjesto s ciljem preuzimanja rezerviranih proizvoda, zaposlenici u istom mogu skeniranjem ili upisivanjem koda potvrditi preuzimanje. |
| F-010 | Sustav kupcu omogućuje otkazivanje rezervacije | Srednji | Odobreni zadatak |Za rezerviranu ponudu kupac može zatražiti otkazivanje rezervacije najkasnije jedan sat prije isteka roka preuzimanja. Sustav otkazuje rezervaciju i vraća artikle u dostupnu zalihu te obavještava prodajno mjesto, a kupcu se vraća novac|
| F-011 | Sustav kupcu omogućuje recenziranje prodajnih mjesta i prodajnim mjestima odgovor na recenzije | Srednji | Odobreni zadatak | Kupac nakon uspješnog preuzimanja proizvoda može ocijeniti (1-5) prodajno mjesto i ostaviti komentar. Prodajnim mjestima je omogućeno odgovoriti na komentare koje su kupci ostavili za njihove proizvode. |
| F-012 | Sustav omogućuje prodajnim mjestima označavanje proizvoda namijenjenih isključivo humanitarnim organizacijama | Visok | Odobreni zadatak | Prodajna mjesta za pojedini proizvod mogu odrediti da nije dostupan za kupnju građanima, nego isključivo za donaciju humanitarnim organizacijama. Takav proizvod ne prikazuje se kupcima (F-002), nego samo humanitarnim organizacijama (F-013). |
| F-013 | Sustav omogućuje humanitarnim organizacijama pregled ponuda i slanje zahtjeva za proizvode namijenjene donaciji | Visok | Odobreni zadatak | Registrirane humanitarne organizacije mogu pregledavati objavljene ponude proizvoda koji su namijenjeni za donaciju humanitarnim organizacijama, također, mogu poslati zahtjev za preuzimanje određene količine hrane objavljene od strane prodajnih mjesta.   |
| F-014 | Sustav omogućuje prodajnim mjestima prihvaćanje ili odbijanje zaprimljenih zahtjeva | Visok | Odobreni zadatak | Prodajna mjesta mogu pregledati zahtjeve zaprimljene od humanitarnih organizacija te pojedini zahtjev odobriti ili odbiti ovisno o dostupnosti i drugim faktorima. |
| F-015 | Sustav omogućava izradu javnog profila humanitarnim organizacijama | Visok | Odobreni zadatak | Humanitarne organizacije imaju javne profile u kojima su navedeni njihovi osnovni podaci (naziv organizacije, kontakt podaci i područje djelovanja) te postaje javno vidljiv nakon aktivacije (F-016) |
| F-016 | Sustav omogućava administratoru odobravanje aktivacije profila organizacije | Visok | Odobreni zadatak | Nakon izrade javnih profila humanitarnih organizacija administrator sustava odobrava ili odbija aktivaciju istih. Odobreni profili postaju javni i organizacije mogu slati zahtjeve za donacije i kreirati humanitarne akcije, dok odbijenim profilima to nije moguće. |
| F-017 | Sustav omogućuje prodajnim mjestima i humanitarnim organizacijama kreiranje akcija prikupljanja i doniranja hrane | Visok | Odobreni zadatak | Prodajna mjesta i humanitarne organizacije mogu kreirati humanitarne akcije za koje je potrebno definirati naziv, opis, lokaciju, datum početka i završetka te popis potrebnih vrsta proizvoda |
| F-018 | Sustav omogućuje kupcima prijavu za sudjelovanje u humanitarnim akcijama | Srednji | Odobreni zadatak | Kupac na stranici akcije koja još traje odabire prijavu, sustav ga evidentira kao sudionika i organizator ga vidi na popisu sudionika. Prijava nije moguća za akciju kojoj je prošao datum završetka, a isti kupac ne može se prijaviti dvaput. |
| F-019 | Sustav omogućuje kupcima evidentiranje proizvoda koje doniraju | Nizak | Odobreni zadatak | Sudionici (kupci) prijavljeni za humanitarnu akciju može odabrati proizvode koje planira donijeti s popisa vrsta potrebnih proizvoda. Humanitarna organizacija(organizator akcije) vidi te najave na pregledu akcija. |
| F-020 | Sustav omogućuje organizatorima humanitarnih akcija evidenciju zaprimljenih donacija | Srednji | Odobreni zadatak | Organizatori humanitarnih akcija nakon primitka mogu evidentirati proizvode koji su stvarno zaprimljeni u donaciji |
| F-021 | Sustav omogućuje kupcima pretplatu na obavijesti | Nizak | Odobreni zadatak | Kupci se mogu pretplatiti za dobivanje obavijesti o novim ponudama odabranih prodajnih mjesta ili kategorija putem e-pošte |
| F-022 | Sustav omogućuje administratoru određivanje cijene članarina  | Srednji | Odobreni zadatak | Administratori imaju ovlasti za postavljanje iznosa mjesečnih i godišnjih članarina. Nova cijena odnosi se na uplate nakon izmjene, već plaćena razdoblja se ne mijenjaju. |
| F-023 | Sustav omogućuje prijavljenim kupcima pregled vlastitih rezervacija | Nizak | Odluka tima | Prijavljenim kupcima omogućen je pregled vlastitih rezervacija sa stanjem i kodom |
| F-024 | Sustav omogućuje prodajnim mjestima uređivanje i brisanje ponuda | Nizak | Odluka tima | Prodajna mjesta za svaku vlastitu objavljenu ponudu imaju mogućnost brisanja ponude ili uređivanja informacija uz kriterij da već ne postoje rezervacije za istu. |
| F-025 | Sustav ne dozvoljava objavu ponuda prodajnim mjestima bez aktivnog članstva | Srednji | Odluka tima | Prodajnim mjestima koja nemaju aktivno članstvo (nisu uplatili članarinu za određeno razdoblje) nije omogućena objava ponuda. |
| F-026 | Sustav omogućuje administratoru upravljanje korisnicima i sadržajem | Nizak | Odobreni zadatak | Administratori imaju ovlasti za blokiranje i brisanje korisnika/profila kao i za uklanjanje neprimjerenih komentara, ponuda ili humanitarnih akcija. |
| F-027 | Sustav omogućuje kupcu filtriranje ponuda | Srednji | Odobreni zadatak |  Sve dostupne ponude kupci filtrirati prema kategoriji, udaljenosti od svoje lokacije,  rasponu cijene i vremenu preuzimanja ili prema kombinaciji više filtera. |

## 2.2 Nefunkcijski zahtjevi


| ID | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-01 | Vrijeme odziva sustava pri pretraživanju i dohvaćanju ponuda ne smije prelaziti 2 sekunde | Srednji | Korisničko iskustvo | Mjerenje vremena odziva od slanja upita do prikaza rezultata u pregledniku, za 95% zahtjeva uz do 100 ponuda u bazi. |
| NF-02 | Sva komunikacija između klijenta i poslužitelja mora biti šifrirana korištenjem HTTPS protokola | Visok | Sigurnost | HTTP zahtjev preusmjerava se na HTTPS , a preglednik prikazuje valjan certifikat. |
| NF-03 | Sustav ne smije pohraniti lozinke, nego se autentifikacija odvija putem OAuth 2.0 protokola | Visok | Odobreni zadatak | Pregled koda i baze podataka kako bismo potvrdili da se lozinke ne zapisuju lokalno. |
| NF-04 | Sučelje mora biti prilagođeno za prikaz na računalima i mobilnim uređajima | Visok | Korisničko iskustvo | Testni scenarij: Otvaranje aplikacije na mobilnom uređaju (do 360px) i računalu (1920px) radi provjere ispravnosti prikazivanja elemenata na različitim veličinama ekrana. |
| NF-05 | Korisničko sučelje mora biti na hrvatskom jeziku, uključujući poruke o pogreškama i potvrde radnji. | Srednji | Odluka tima | Pregled korisničkog sučelja i provjera svih tekstualnih elemenata i poruka. |
| NF-06 | Podaci o karticama i PayPal računima unose se isključivo na stranici vanjskih servisa za plaćanje (sustav ih ne prima niti sprema). Rezervacija se potvrđuje kad poslužitelj od servisa dobije potvrdu plaćanja. | Visok | Korisničko iskustvo | Pregled sheme baze kako bi se utvrdilo da se ne spremaju podaci kartica. U simuliranoj narudžbu (sandbox) provjeriti da rezervacija ostaje nepotvrđena kad plaćanje ne uspije ili se prekine te da se kupcu prikaže odgovarajuća poruka. |


## 2.3 Dionici i akteri

Dionici: 

| ID dionika | Odnos prema sustavu | Relevantni zahtjevi |
| --- | --- | --- |
| D-01 | Kupci: žele pronaći hranu po sniženoj cijeni u blizini, rezervirati je i preuzeti do zadanog roka. Koriste aplikaciju kao akter A-01. | F-002: Pregled ponuda; F-028: Filtriranje ponuda; F-008: Rezervacija i plaćanje; F-009: Kod za preuzimanje; F-010: Otkazivanje rezervacije. |
| D-02 | Vlasnici prodajnih mjesta (trgovine, pekarnice, slastičarnice): žele prodati ili donirati višak hrane umjesto da je bace. Aplikaciju u njihovo ime koristi predstavnik prodajnog mjesta (A-02). | F-005: Objava ponude; F-006: Objava paketa iznenađenja; F-012: Označavanje ponuda za donaciju; F-003: Plaćanje članarine. |
| D-03 | Zaposlenici prodajnih mjesta: žele brzo potvrditi preuzimanje rezervacije. Nisu zaseban akter, nego koriste račun prodajnog mjesta. | F-009: Verifikacija koda za preuzimanje. |
| D-04 | Humanitarne organizacije: žele doći do hrane namijenjene donaciji i organizirati akcije prikupljanja. Aplikaciju u njihovo ime koristi predstavnik organizacije (A-03). | F-013: Pregled donacijskih ponuda i slanje zahtjeva; F-017: Kreiranje humanitarne akcije; F-020: Evidencija zaprimljenih donacija. |
| D-05 | Krajnji primatelji donirane hrane: imaju korist od hrane koju organizacije preuzmu. Ne koriste aplikaciju i nisu akteri. | F-012: Označavanje ponuda za donaciju; F-013: Pregled donacijskih ponuda i slanje zahtjeva. |
| D-06 | Vlasnik platforme: želi naplatiti članarine i održavati vjerodostojan sadržaj. Aplikaciju u njegovo ime koristi administrator (A-04). | F-022: Određivanje cijena članarina; F-016: Odobravanje profila organizacija; F-026: Upravljanje korisnicima i sadržajem. |
| D-07 | Agencija za zaštitu osobnih podataka (AZOP): zahtijeva obradu osobnih podataka u skladu s GDPR-om. Ne koristi aplikaciju, ali određuje ograničenja sustava. | NF-03: Bez pohrane lozinki; NF-06: Sigurno plaćanje. |

Akteri: 

| ID aktera | Uloga | Obuhvaćeni funkcijski zahtjevi |
| --- | --- | --- |
| A-01 | Kupac | F-001: Registracija i prijava; F-027: Odabir uloge i unos podataka profila; F-002: Pregled ponuda; F-028: Filtriranje ponuda; F-007: Prikaz lokacije i udaljenosti; F-008: Rezervacija i plaćanje; F-009: Primanje koda za preuzimanje; F-010: Otkazivanje rezervacije; F-011: Recenziranje prodajnog mjesta; F-018: Prijava u humanitarnu akciju; F-019: Evidentiranje planiranih donacija; F-021: Pretplata na obavijesti; F-023: Pregled vlastitih rezervacija. |
| A-02 | Predstavnik prodajnog mjesta | F-001: Registracija i prijava; F-027: Odabir uloge i unos podataka profila; F-003: Plaćanje članarine; F-004: Javni profil prodajnog mjesta; F-005: Objava ponude; F-006: Objava paketa iznenađenja; F-009: Verifikacija koda za preuzimanje; F-011: Odgovor na recenziju; F-012: Označavanje ponuda za donaciju; F-014: Prihvaćanje ili odbijanje zahtjeva; F-017: Kreiranje humanitarne akcije; F-020: Evidencija zaprimljenih donacija; F-024: Uređivanje i brisanje ponuda. |
| A-03 | Predstavnik humanitarne organizacije | F-001: Registracija i prijava; F-027: Odabir uloge i unos podataka profila; F-015: Javni profil organizacije; F-013: Pregled donacijskih ponuda i slanje zahtjeva; F-017: Kreiranje humanitarne akcije; F-020: Evidencija zaprimljenih donacija. |
| A-04 | Administrator sustava | F-016: Odobravanje profila organizacija; F-022: Određivanje cijena članarina; F-026: Upravljanje korisnicima i sadržajem. |
| A-05 | Pružatelj autentifikacije (Google, OAuth 2.0) | F-001: Potvrda identiteta pri registraciji i prijavi. |
| A-06 | Servis za plaćanje (PayPal) | F-003: Naplata članarine; F-008: Naplata rezervacije; F-010: Povrat novca pri otkazivanju. |
| A-07 | Servis za karte (Google Maps) | F-007: Prikaz karte i lokacija prodajnih mjesta. |
| A-08 | Servis za slanje e-pošte (SendGrid) | F-009: Slanje potvrde rezervacije i koda za preuzimanje; F-021: Slanje obavijesti o novim ponudama pretplaćenim kupcima. |



---

## 2.4 Ograničenja i pretpostavke

**Cilj:** Zabilježiti projektno specifične granice i pretpostavke koje utječu na zahtjeve ili oblikovanje, zajedno s njihovim izvorom i praktičnom posljedicom. Ovo je njihovo mjerodavno mjesto u dokumentaciji.

| ID | Tip | Opis ograničenja ili pretpostavke | Izvor | Inženjerska posljedica / Provjera |
| --- | --- | --- | --- | --- |
| C-01 | Ograničenje | Odobreni zadatak zahtijeva registraciju i prijavu računa pomoću vanjskih OAuth servisa za autentifikaciju. Sustav ne pohranjuje lozinke.  | Odobreni zadatak | Prijava ide preko Googlea: korisnik se prijavi na Googleovoj stranici, Google vrati backendu njegov jedinstveni ID (sub) i e-poštu, a backend po tom ID-ju pronađe korisnika u bazi ili ga kreira i preusmjeri na odabir uloge. Tablica korisnika nema stupac za lozinku, nego sprema ID kod Googlea, e-poštu, ulogu i status profila. Provjera: testnim zahtjevima potvrditi da prijavljeni korisnik može izvršiti radnje svoje uloge, a da se zahtjevi bez prijave ili izvan ovlasti odbijaju. Pregledom sheme baze potvrditi da tablica korisnika ne sadrži lozinke. |
| C-02 | Ograničenje | Plaćanje se obavlja putem vanjskih servisa. | Odobreni zadatak |  Sustav ne obrađuje i ne pohranjuje podatke o karticama, nego samo identifikator i status transakcije. Uspješnost plaćanja potvrđuje se na poslužitelju, a ne samo na klijentu. Neuspjelo plaćanje vodi rezervaciju u definirano stanje (NF-06). Provjera: uspješno, odbijeno i prekinuto plaćanje testirati PayPal testnim (sandbox) računima i testnim karticama. |
| C-03 | Ograničenje | Sva komunikacija između klijenta i poslužitelja odvija se preko HTTPS-a. | Sigurnost |  Sustav se postavlja na platformu koja automatski osigurava HTTPS. Adrese za preusmjeravanje (redirect URI) OAuth-a i servisa za plaćanje moraju biti HTTPS adrese. Provjera pregledom mrežnog prometa.|
| C-04 | Ograničenje | Sustav je web aplikacija koja se koristi u pregledniku na računalima i mobilnim uređajima (NF-04)| Odobreni zadatak | Potreban je responzivan raspored sučelja. Zaposlenik prodajnog mjesta QR kod skenira kamerom mobitela izravno u pregledniku, pomoću gotove JavaScript biblioteke za čitanje QR kodova. Preglednik dopušta pristup kameri samo preko HTTPS-a i uz dozvolu korisnika. Ako kamera nije dostupna ili skeniranje ne uspije, zaposlenik kod upisuje ručno. Provjera: aplikaciju otvoriti na mobitelu i računalu te na mobitelu skenirati i ručno upisati kod za preuzimanje. |
| C-05 | Ograničenje | Ponuda se može uređivati ili brisati samo ako za nju ne postoje rezervacije. | Odluka tima | Postojanje rezervacija provjerava se na poslužitelju u istoj transakciji kao izmjena. U sučelju se akcije za takve ponude onemogućuju. |
| AS-01 | Pretpostavka | Kartično plaćanje i povrat novca mogu se obaviti putem istog vanjskog servisa. | Pretpostavaka o vanjskim servisima | Provjeriti u sandbox okruženju plaćanje karticom bez PayPal računa i povrat za otkazanu rezervaciju. |
| AS-02 | Pretpostavka | Kupci dopuštaju pregledniku pristup lokaciji radi izračuna udaljenosti. | Pretpostavaka o okolini | Testirati scenarij odbijanja dozvole. Tada ponuditi ručni unos lokacije ili prikaz ponuda bez udaljenosti. |
| AS-03 | Pretpostavka | Zaposlenici prodajnih mjesta koriste račun predstavnika prodajnog mjesta i nisu zaseban akter u sustavu. | Pretpostavaka o okolini | Potvrditi s asistenticom. Ako su potrebni zasebni računi zaposlenika, dodati podulogu s ovlašću samo za verifikaciju koda. |
| AS-04 | Pretpostavka | Administrator vjerodostojnost humanitarne organizacije provjerava izvan sustava (npr. uvidom u Registar udruga). Sustav se ne povezuje s javnim registrima. | Procjena tima | Potvrditi s asistenticom. U profil organizacije uključiti podatke potrebne za ručnu provjeru (npr. OIB). |


---

## 2.5 Značajne promjene zahtjeva

**Cilj:** Sačuvati razlog promjena koje mijenjaju dogovoreni opseg, prioritet ili kriterije prihvaćanja, uz istodobno održavanje prethodnih zahtjeva ažurnima.

| Datum | ID zahtjeva | Promjena i razlog | Dogovoreno s / dokaz |
| --- | --- | --- | --- |
| 30.10.2026. | F-008 (prvotno F-030) | Uklonjena opcija plaćanja gotovinom pri preuzimanju, ostaje samo opcija plaćanja online (PayPal ili kartično). Opcija nije potrebna za ostvarenje cilja sustava. | Odluka tima nakon razgovora s asistenticom na laboratorijskoj vježbi. |
| 30.10.2026. | F-001, F-002, F-008, F-009, F-011, F-013, F-026 | Spojeni zahtjevi koji opisuju dijelove iste korisničke radnje. Smanjen broj zahtjeva s 35 na 26. Zbog preglednije liste bez preklapanja | Razgovor s asistenticom na laboratorijskoj vježbi. |

---

## 2.6 Sljedivost zahtjeva

**Cilj:** Osigurati dvosmjernu sljedivost — povezati zahtjeve s opisanim ponašanjem, dijelovima sustava koji ih ostvaruju te ispitivanjem ili drugom provjerom.

| ID zahtjeva | Obrazac uporabe / modul | Arhitekturna realizacija | Ispitivanje ili provjera |
| --- | --- | --- | --- |
| F-001 | UC-001 | Spring Security OAuth2 (Google); korisnik (Korisnik, KorisnikRepository) | na čekanju: #40 i scenarij 401 iz #43|
| F-002 | UC-002 | ponuda (Ponuda, PonudaRepository); GET /api/ponude | PonudaRepositoryTest |
| F-003 | UC-007 | Vanjski servis za plaćanje (A-06) | na čekanju (nije u opsegu M1) |
| F-004 | UC-001 (korak 4) | prodajnomjesto (ProdajnoMjesto) | Na čekanju |
| F-005 | UC-008 | ponuda; POST /api/ponude | Na čekanju: scenarij objave iz #43 |
| F-006 | UC-008 | ponuda (VrstaPonude) | Na čekanju |
| F-007 | UC-002 | Web-klijent i Google Maps (A-07) | Na čekanju |
| F-008 | UC-003 | rezervacija (Rezervacija, StavkaRezervacije); zaliha (ZalihaService) | RezervacijaTest, ZalihaServiceTest, ZalihaIstodobnostTest |
| F-009 | UC-004 | rezervacija | Na čekanju |
| F-010 | UC-005 | Rezervacija, status OTKAZANA | RezervacijaTest (prijelazi stanja) |
| F-011 | UC-006 | Na čekanju: modul nije započet | Na čekanju |
| F-012 | UC-008 | ponuda (NamjenaPonude) | Na čekanju |
| F-013 | UC-009 | Na čekanju | Na čekanju |
| F-014 | UC-010 | Na čekanju | Na čekanju |
| F-015 | UC-001, UC-011 | korisnik (StatusOdobrenja)	| Na čekanju |
| F-016 | UC-011 | korisnik (StatusOdobrenja) | Na čekanju |
| F-017 | UC-012 | Na čekanju | Na čekanju |
| F-018 | UC-013 | Na čekanju | Na čekanju |
| F-019 | UC-013 | Na čekanju | Na čekanju |
| F-020 | UC-012 | Na čekanju | Na čekanju |
| F-021 | Nema zasebnog obrasca (3.3) | Na čekanju | Na čekanju |
| F-022 | UC-014 | Na čekanju | Na čekanju |
| F-023 | UC-005 | rezervacija; GET /api/rezervacije/moje | Na čekanju |
| F-024 | UC-008 | ponuda | Na čekanju |
| F-025 | UC-007, UC-008 | Provjera članstva pri objavi ponude | Na čekanju |
| F-026 | UC-014 | Na čekanju | Na čekanju |
| F-027 | UC-002 | ponuda; GET /api/ponude (parametri filtera) | Na čekanju |
| NF-01 | UC-002 | GET /api/ponude; upiti u PonudaRepository | Na čekanju: mjerenje vremena odziva na postavljenoj aplikaciji |
| NF-02 | Nema zasebnog obrasca | Hosting s HTTPS-om (#42) | Na čekanju |
| NF-03 | UC-001 | OAuth2 prijava; tablica korisnika bez stupca za lozinku | Pregled sheme db/zalogajcici.sql |
| NF-04 | Svi obrasci uporabe sa sučeljem | Web-klijent: prilagodljivi CSS (src/assets/app.css), donja traka na mobitelu i bočna na računalu (components/ui.js) | Na čekanju: ručna provjera na mobitelu i računalu |
| NF-05 | Svi obrasci uporabe sa sučeljem | Tekstovi u web-klijentu; poruke grešaka iz backenda na hrvatskom | Na čekanju: ručna provjera sučelja i poruka grešaka |
| NF-06 | UC-003, UC-007 | Vanjski servis za plaćanje (A-06); aplikacija ne pohranjuje podatke kartica | Na čekanju (nije u opsegu M1) |


---

## 2.7 Pregled zahtjeva

**Cilj:** Zabilježiti važne povratne informacije o zahtjevima i odluke koje iz njih proizlaze na dvije formalne kontrolne točke projekta, u 7. i 14. tjednu.

| Kontrolna točka | Povratna informacija ili nalaz | Odluka o zahtjevu ili opsegu |
| --- | --- | --- |
| Iteracija 1 | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |
| Demo 2 | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |
| Završni pregled | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |

