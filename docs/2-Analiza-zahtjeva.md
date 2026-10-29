# 2. Analiza zahtjeva

## 2.1 Funkcijski zahtjevi



| ID | Opis | Prioritet | Izvor | Kriteriji prihvaćanja |
| --- | --- | --- | --- | --- |
| F-001 | Sustav omogućuje korisniku registraciju računa pomoću vanjskih servisa za autentifikaciju | Visok | Odobreni zadatak | *Given* neregistrirani korisnik<br>*When* odabere registraciju putem OAuth servisa<br>*Then* sustav kreira profil |
| F-002 | Sustav omogućuje odabir uloge korisnika prilikom prve registracije | Visok | Odobreni zadatak | Korisnik prilikom prve registracije odabire ulogu (Kupac, humanitarna organizacija ili prodajno mjesto) te unosi podatke za profil (za organizaciju se profil šalje na odobrenje administratoru). |
| F-003 | Sustav omogućuje pregled svih ponuda hrane i paketa iznenađenja trenutno dostupnih kupcima (izuzev ponuda namijenjenih humanitarnim organizacijama). | Visok | Odobreni zadatak | Korisnik na početnoj stranici može pregledavati ponude objavljene od strane prodajnih mjesta s prikazom naziva, cijene i roka vremena za preuzimanje, osim onih namijenjenih isključivo humanitarnim akcijama. |
| F-004 | Sustav omogućuje plaćanje članarine za korištenje platforme | Visok | Odobreni zadatak | Prodajna mjesta mogu plaćati mjesečnu ili godišnju članarinu za korištenje platforme putem PayPala ili kreditnih kartica korištenjem vanjskih servisa |
| F-005 | Sustav omogućuje izradu javnih profila prodajnih mjesta i upravljajne istim | Srednji | Odobreni zadatak | Prodajna mjesta imaju javne profile na kojima su dostupne njihove osnovne informacije (naziv, adresa, kontakt, radno vrijeme, logo te popis trenutno dostupnih proizvoda) |
| F-006 | Sustav omogućuje prodajnim mjestima objavu ponude proizvoda. | Visok | Odobreni zadatak | Prodajna mjesta mogu objaviti ponudu hrane u kojoj je naveden naziv proizvoda, kategorija, količina, redovna i snižena cijena/informacija o doniranju, rok upotrebe i vrijeme do kojeg je moguće preuzimanje. Također je moguća objava fotografija proizvoda ili paketa. |
| F-007 | Sustav omogućuje prodajnim mjestima objavu ponude „Paketa iznenađenja“. | Srednji | Odobreni zadatak | Prodajna mjesta unose okvirni sadržaj, ukupnu vrijednost, prodajnu cijenu i količinu dostupnih paketa te vrijeme do kojeg je moguće preuzimanje, bez navođenja točnih artikala u paketu. |
| F-008 | Sustav omogućuje filtriranje dostupnih ponuda. | Srednji | Odobreni zadatak | Kupcima se omogućuje pretraživanje objavljenih ponuda hrane prema kategoriji, lokaciji, cijeni ili vremenu preuzimanja. |
| F-009 | Sustav prikazuje lokacije prodajnih mjesta i udaljenosti od korisnika | Srednji | Odobreni zadatak | Kupcima je vidljiva točna lokacija prodajnog mjesta te je izračunata udaljenost od istog. Ponude i prodajna mjesta prikazuju se kao oznake na karti (Google Maps) |
| F-010 | Sustav omogućuje rezervaciju dostupnih proizvoda i paketa | Visok | Odobreni zadatak | Kupcima je omogućena rezervacija jednog ili više dostupnih proizvoda ili paketa |
| F-011 | Sustav omogućuje odabir načina plaćanja i obradu transakcije rezerviranih proizvoda | Visok | Odobreni zadatak | Kupcima je omogućen odabir i plaćanje proizvoda koje su rezervirali. Mogući načini plaćanja su PayPal, Kreditna kartica korištenjem vanjskih servisa za plaćanje.|
| F-012 | Sustav generira i šalje korisniku potvrdu i kod za preuzimanje | Visok | Odobreni zadatak | Nakon uspješne rezervacije korisnicima se na e-poštu šalje potvrda i jedinstveni kod koji omogućuje preuzimanje proizvoda. |
| F-013 | Sustav omogućuje prodajnom mjestu verifikaciju koda za preuzimanje | Visok | Odobreni zadatak | Prilikom dolaska kupca u prodajno mjesto s ciljem preuzimanja rezerviranih proizvoda, zaposlenici u istom mogu skeniranjem ili upisivanjem koda potvrditi preuzimanje. |
| F-014 | Sustav omogućuje otkazivanje rezervacije | Srednji | Odobreni zadatak | *Given* rezervirana ponuda<br>*When* korisnik zatraži otkazivanje rezervacije najkasnije jedan sat prije isteka roka preuzimanja<br>*Then* sustav otkazuje rezervaciju i vraća artikle u dostupnu zalihu te obavještava prodajno mjesto, a kupcu se vraća novac|
| F-015 | Sustav omogućuje recenziranje prodajnih mjesta | Srednji | Odobreni zadatak | Kupac nakon uspješnog preuzimanja proizvoda može ocijeniti (1-5) prodajno mjesto i ostaviti komentar. |
| F-016 | Sustav omogućuje prodajnim mjestima odgovor na recenzije | Nizak | Odobreni zadatak | Prodajnim mjestima je omogućeno odgovoriti na komentare koje su korisnici ostavili za njihove proizvode. |
| F-017 | Sustav omogućuje prodajnim mjestima označavanje proizvoda namijenjenih isključivo humanitarnim organizacijama | Visok | Odobreni zadatak | Prodajna mjesta za pojedini proizvod mogu odrediti da nije dostupan za kupnju građanima, nego isključivo za donaciju humanitarnim organizacijama. |
| F-018 | Sustav omogućuje humanitarnim organizacijama pregled ponuda proizvoda namijenjenih donaciji | Visok | Odobreni zadatak | Registrirane humanitarne organizacije mogu pregledavati objavljene ponude proizvoda koji su namijenjeni za donaciju humanitarnim organizacijama |
| F-019 | Sustav omogućuje humanitarnim organizacijama slanje zahtjeva za preuzimanje hrane | Visok | Odobreni zadatak | Registrirane humanitarne organizacije mogu poslati zahtjev za preuzimanje određene količine hrane objavljene od strane prodajnih mjesta |
| F-020 | Sustav omogućuje prodajnim mjestima prihvaćanje ili odbijanje zaprimljenih zahtjeva | Visok | Odobreni zadatak | Prodajna mjesta mogu pregledati zahtjeve zaprimljene od humanitarnih organizacija te pojedini zahtjev odobriti ili odbiti ovisno o dostupnosti i drugim faktorima. |
| F-021 | Sustav omogućava izradu javnog profila humanitarnim organizacijama uz odobrenje administratora | Visok | Odobreni zadatak | Humanitarne organizacije imaju javne profile u kojima su navedeni njihovi osnovni podaci (naziv organizacije, kontakt podaci i područje djelovanja) čiju aktivaciju odobrava administrator |
| F-022 | Sustav omogućava administratoru odobravanje aktivacije profila organizacije | Visok | Odobreni zadatak | Nakon izrade javnih profila humanitarnih organizacija administrator sustava odobrava aktivaciju istih |
| F-023 | Sustav omogućuje prodajnim mjestima i humanitarnim organizacijama kreiranje akcija prikupljanja i doniranja hrane | Visok | Odobreni zadatak | Prodajna mjesta i humanitarne organizacije mogu kreirati humanitarne akcije za koje je potrebno definirati naziv, opis, lokaciju, datum početka i završetka te popis potrebnih vrsta proizvoda |
| F-024 | Sustav omogućuje kupcima prijavu za sudjelovanje u humanitarnim akcijama | Srednji | Odobreni zadatak | Kupci se mogu prijaviti za sudjelovanje u humanitarnim akcijama |
| F-025 | Sustav omogućuje kupcima evidentiranje proizvoda koje doniraju | Nizak | Odobreni zadatak | Kupci mogu označiti proizvode koje planiraju donirati humanitarnim akcijama. |
| F-026 | Sustav omogućuje organizatorima humanitarnih akcija evidenciju zaprimljenih donacija | Srednji | Odobreni zadatak | Organizatori humanitarnih akcija nakon primitka mogu evidentirati proizvode koji su stvarno zaprimljeni u donaciji |
| F-027 | Sustav omogućuje korisnicima pretplatu na obavijesti | Nizak | Odobreni zadatak | Kupci se mogu pretplatiti za dobivanje obavijesti o novim ponudama odabranih prodajnih mjesta ili kategorija putem e-pošte |
| F-028 | Sustav omogućuje administratoru određivanje cijene članarina  | Srednji | Odobreni zadatak | Administratori imaju ovlasti za izmjenu iznosa članarina. |
| F-029 | Sustav omogućuje korisnicima prijavu u profil korištenjem vanjskih servisa za autentifikaciju | Visok | Odobreni zadatak | Korisnicima koji imaju postojeći profil omogućena je prijava u svoj korisnički profil pomoću vanjskih servisa za autentifikaciju. |
| F-030 | Dodatna opcija plaćanja gotovinom prilikom preuzimanja | Nizak | Odluka tima - odobreno od asistentice | Korisnicima je uz prije navedene metode plaćanja omogućen odabir plaćanja gotovinom prilikom preuzimanja narudžbe u poslovnici. | 
| F-031 | Sustav omogućuje prijavljenim kupcima pregled vlastitih rezervacija | Nizak | Odluka tima | Prijavljenim korisnicima omogućen je pregled vlastitih rezervacija sa stanjem i kodom |
| F-032 | Sustav omogućuje prodajnim mjestima uređivanje i brisanje ponuda | Nizak | Odluka tima | Prodajna mjesta za svaku vlastitu objavljenu ponudu imaju mogućnost brisanja ponude ili uređivanja informacija uz kriterij da već ne postoje rezervacije za istu. |
| F-033 | Sustav ne dozvoljava objavu ponuda prodajnim mjestima bez aktivnog članstva | Srednji | Odluka tima | Prodajnim mjestima koja nemaju aktivno članstvo (nisu uplatili članarinu za određeno razdoblje) nije omogućena objava ponuda. |
| F-034 | Sustav omogućuje administratoru upravljanje korisnicima | Nizak | Odobreni zadatak | Administratori imaju ovlasti za blokiranje i brisanje korisnika/profila. |
| F-035 | Sustav omogućuje administratoru uklanjanje neprimjerenog sadržaja | Nizak | Odobreni zadatak | Administratori imaju ovlasti za uklanjanje neprimjerenih komentara, ponuda ili humanitarnih akcija. |

## 2.2 Nefunkcijski zahtjevi


| ID | Opis | Prioritet | Izvor | Način provjere |
| --- | --- | --- | --- | --- |
| NF-01 | Vrijeme odziva sustava pri pretraživanju i dohvaćanju ponuda ne smije prelaziti 2 sekunde | Srednji | Korisničko iskustvo | Mjerenje vremena odziva od slanja upita do prikaza rezultata u pregledniku |
| NF-02 | Sva komunikacija između klijenta i poslužitelja mora biti šifrirana korištenjem HTTPS protokola | Visok | Sigurnost | Pregled mrežnog prometa. |
| NF-03 | Sustav ne smije pohraniti lozinke, nego se autentifikacija odvija putem OAuth 2.0 protokola | Visok | Odobreni zadatak | Pregled koda i baze podataka kako bismo potvrdili da se lozinke ne zapisuju lokalno. |
| NF-04 | Sučelje mora biti prilagođeno za prikaz na računalima i mobilnim uređajima | Visok | Korisničko iskustvo | Testni scenarij: Otvaranje aplikacije na mobilnom uređaju i računalu radi provjere ispravnosti prikazivanja elemenata na različitim veličinama ekrana. |
| NF-05 | Pristup osobnim podacima mora biti ograničen samo na ovlaštene korisnike i uloge. | Visok | Sigurnost | Ručni pregled profila i provjera da osjetljivi podaci nisu izloženi. |
| NF-06 | Korisničko sučelje mora biti na hrvatskom jeziku, uključujući poruke o pogreškama i potvrde radnji. | Srednji | Odluka tima | Pregled korisničkog sučelja i provjera svih tekstualnih elemenata i poruka. |
| NF-07 | Sustav mora očuvati dosljednost podataka o dostupnim količinama hrane kako bi se onemogućila rezervacija količine veće od raspoložive | Visok | Korisničko iskustvo | Simulacija istovremenih rezervacija za zadnje dostupne količine i provjera da ukupno rezervirana ne premaši raspoloživu |
| NF-08 | Sustav mora osigurati da se korisnicima prikazuju samo trenutno aktivne ponude (kojima nije istekao rok za preuzimanje ili su rasprodane) | Visok | Pravila sustava | Testiranje prikaza ponuda i provjera da se ne prikazuju ponude koje više nisu dostupne |
| NF-09 | Sustav mora osigurati da se u slučaju neuspješnog plaćanja, nedostupnosti vanjskog servisa ili neuspješnosti pri dohvaćanju podataka prikaže poruka o pogrešci i da se omogući nastavak rada kada je to moguće | Visok | Korisničko iskustvo | Simulacija neuspješnog plaćanja te provjera prikazuje li se odgovarajuća poruka |
| NF-10 | Sustav mora spriječiti korisnike u izvršavanju radnji za koje nemaju ovlasti. | Visok | Sigurnost | Testni scenarij pokušaja izvršavanja administratorskih radnji korisničkim računom kupca. |


## 2.3 Dionici i akteri

Dionici: 

| ID dionika | Odnos prema sustavu | Relevantni zahtjevi |
| --- | --- | --- |
| D-01 | Kupci | F-001: Registracija računa, F-002: Odabir uloge, F-003: Pregled ponuda, F-008: Filtriranje ponuda, F-009: Prikaz lokacije i udaljenosti, F-010: Rezervacija, F-011: Odabir načina plaćanja i plaćanje, F-012: Potvrda i kod za preuzimanje, F-014: Otkazivanje, F-015: Recenziranje prodajnih mjesta, F-024: Prijava u humanitarne akcije, F-025: Evidentiranje donacija, F-027: Pretplata na obavijesti, F-029: Prijava u profil, F-030: Plaćanje gotovinom, F-031: Pregled vlastitih rezervacija |
| D-02 | Prodajna mjesta | F-001: Registracija računa, F-002: Odabir uloge, F-004: Plaćanje članarine, F-005: Javni profil prodajnog mjesta, F-006: Objava ponuda, F-007: Objava paketa iznenađenja, F-013: Verifikacija koda za preuzimanje, F-016: Odgovor na recenzije, F-017: Označavanje ponuda za donaciju, F-020: Prihvaćanje ili odbijanje zahtjeva, F-023: Kreiranje humanitarnih akcija, F-029: Prijava u profil, F-032: Uređivanje i brisanje ponuda, F-033: Onemogućena objava ponuda bez članstva  |
| D-03 | Humanitarne organizacije |  F-001: Registracija računa, F-002: Odabir uloge, F-018: Pregled donacijskih ponuda, F-019: Zahtjev za preuzimanje hrane, F-021: Javni profil humanitarne organizacije, F-023: Kreiranje humanitarnih akcija, F-026: Evidencija zaprimljenih donacija, F-029: Prijava u profil |
| D-04 | Administratori sustava | F-022: Odobravanje profila organizacija, F-028: Određivanje cijena članarina, F-034: Upravljanje korisnicima, F-035: Uklanjanje neprimjerenog sadržaja |
| D-05 | Pružatelji vanjskih usluga | F-001: Registracija računa, F-004: Plaćanje članarine, F-009: Prikaz lokacije i udaljenosti, F-011: Odabir načina plaćanja, F-029: Prijava u profil |
| D-06 | Zaposlenici prodajnih mjesta | F-013: Verifikacija koda za preuzimanje |

Akteri: 

| ID aktera | Odnos prema sustavu | Relevantni zahtjevi |
| --- | --- | --- | 
| A-01 | Kupac | F-001: Registracija računa, F-002: Odabir uloge, F-003: Pregled ponuda, F-008: Filtriranje ponuda, F-009: Prikaz lokacije i udaljenosti, F-010: Rezervacija, F-011: Odabir načina plaćanja i plaćanje, F-012: Potvrda i kod za preuzimanje, F-014: Otkazivanje, F-015: Recenziranje prodajnih mjesta, F-024: Prijava u humanitarne akcije, F-025: Evidentiranje donacija, F-027: Pretplata na obavijesti, F-029: Prijava u profil, F-030: Plaćanje gotovinom, F-031: Pregled vlastitih rezervacija |
| A-02 | Predstavnik prodajnog mjesta | F-001: Registracija računa, F-002: Odabir uloge, F-004: Plaćanje članarine, F-005: Javni profil prodajnog mjesta, F-006: Objava ponuda, F-007: Objava paketa iznenađenja, F-013: Verifikacija koda za preuzimanje, F-016: Odgovor na recenzije, F-017: Označavanje ponuda za donaciju, F-020: Prihvaćanje ili odbijanje zahtjeva, F-023: Kreiranje humanitarnih akcija, F-029: Prijava u profil, F-032: Uređivanje i brisanje ponuda, F-033: Onemogućena objava ponuda bez članstva |
| A-03 | Predstavnik humanitarne organizacije | F-001: Registracija računa, F-002: Odabir uloge, F-018: Pregled donacijskih ponuda, F-019: Zahtjev za preuzimanje hrane, F-021: Javni profil humanitarne organizacije, F-023: Kreiranje humanitarnih akcija, F-026: Evidencija zaprimljenih donacija, F-029: Prijava u profil |
| A-04 | Administrator sustava | F-022: Odobravanje profila organizacija, F-028: Određivanje cijena članarina, F-034: Upravljanje korisnicima, F-035: Uklanjanje neprimjerenog sadržaja |
| A-05 | Pružatelj autentifikacije (OAuth 2.0) | F-001: Registracija računa, F-029: Prijava u profil |
| A-06 | Servis za plaćanje (Paypal) | F-004: Plaćanje članarine, F-011: Odabir načina plaćanja i plaćanje |
| A-07 | Servis za karte (Google Maps) | F-009: Prikaz lokacije i udaljenosti



---

## 2.4 Ograničenja i pretpostavke

**Cilj:** Zabilježiti projektno specifične granice i pretpostavke koje utječu na zahtjeve ili oblikovanje, zajedno s njihovim izvorom i praktičnom posljedicom. Ovo je njihovo mjerodavno mjesto u dokumentaciji.

| ID | Tip | Opis ograničenja ili pretpostavke | Izvor | Inženjerska posljedica / Provjera |
| --- | --- | --- | --- | --- |
| C-01 | Ograničenje | Odobreni zadatak zahtijeva registraciju i prijavu računa pomoću vanjskih OAuth servisa za autentifikaciju. Sustav ne pohranjuje lozinke.  | Odobreni zadatak | Prijava ide preko Googlea: korisnik se prijavi na Googleovoj stranici, Google vrati backendu njegov jedinstveni ID (sub) i e-poštu, a backend po tom ID-ju pronađe korisnika u bazi ili ga kreira i preusmjeri na odabir uloge. Tablica korisnika nema stupac za lozinku, nego sprema ID kod Googlea, e-poštu, ulogu i status profila. Provjera: testnim zahtjevima potvrditi da prijavljeni korisnik može izvršiti radnje svoje uloge, a da se zahtjevi bez prijave ili izvan ovlasti odbijaju. Pregledom sheme baze potvrditi da tablica korisnika ne sadrži lozinke. |
| C-02 | Ograničenje | Plaćanje se obavlja putem vanjskih servisa. | Odobreni zadatak |  Sustav ne obrađuje i ne pohranjuje podatke o karticama, nego samo identifikator i status transakcije. Uspješnost plaćanja potvrđuje se na poslužitelju, a ne samo na klijentu. Neuspjelo plaćanje vodi rezervaciju u definirano stanje (NF-09). Provjera: uspješno, odbijeno i prekinuto plaćanje testirati PayPal testnim (sandbox) računima i testnim karticama. |
| C-03 | Ograničenje | Sva komunikacija između klijenta i poslužitelja odvija se preko HTTPS-a. | Odobreni zadatak, sigurnost |  Sustav se postavlja na platformu koja automatski osigurava HTTPS. Adrese za preusmjeravanje (redirect URI) OAuth-a i servisa za plaćanje moraju biti HTTPS adrese. Provjera pregledom mrežnog prometa.|
| C-04 | Ograničenje | Sustav je web aplikacija koja se koristi u pregledniku na računalima i mobilnim uređajima (NF-01, NF-04)| Odobreni zadatak | Potreban je responzivan raspored sučelja. Zaposlenik prodajnog mjesta QR kod skenira kamerom mobitela izravno u pregledniku, pomoću gotove JavaScript biblioteke za čitanje QR kodova. Preglednik dopušta pristup kameri samo preko HTTPS-a i uz dozvolu korisnika. Ako kamera nije dostupna ili skeniranje ne uspije, zaposlenik kod upisuje ručno. Provjera: aplikaciju otvoriti na mobitelu i računalu te na mobitelu skenirati i ručno upisati kod za preuzimanje. |
| C-05 | Ograničenje | Ponuda se može uređivati ili brisati samo ako za nju ne postoje rezervacije. | Odluka tima | Postojanje rezervacija provjerava se na poslužitelju u istoj transakciji kao izmjena. U sučelju se akcije za takve ponude onemogućuju. |
| C-06 | Ograničenje | Uz online plaćanje podržano je plaćanje gotovinom pri preuzimanju. | Odluka tima | Sustav mora razlikovati plaćene i neplaćene rezervacije. Kod za preuzimanje izdaje se i za neplaćene rezervacije, a prodajno mjesto pri verifikaciji koda potvrđuje i naplatu. |
| AS-01 | Pretpostavka | Kartično plaćanje i povrat novca mogu se obaviti putem istog vanjskog servisa, uključujući povrat. | Pretpostavaka o vanjskim servisima | Provjeriti u sandbox okruženju plaćanje karticom bez PayPal računa i povrat za otkazanu rezervaciju. |
| AS-02 | Pretpostavka | Kupci dopuštaju pregledniku pristup lokaciji radi izračuna udaljenosti. | Pretpostavaka o okolini | Testirati scenarij odbijanja dozvole. Tada ponuditi ručni unos lokacije ili prikaz ponuda bez udaljenosti. |
| AS-03 | Pretpostavka | Zaposlenici prodajnih mjesta koriste račun predstavnika prodajnog mjesta i nisu zaseban akter u sustavu. | Pretpostavaka o okolini | Potvrditi s asistenticom. Ako su potrebni zasebni računi zaposlenika, dodati podulogu s ovlašću samo za verifikaciju koda. |
| AS-04 | Pretpostavka | Administrator vjerodostojnost humanitarne organizacije provjerava izvan sustava (npr. uvidom u Registar udruga). Sustav se ne povezuje s javnim registrima. | Procjena tima | Potvrditi s asistenticom. U profil organizacije uključiti podatke potrebne za ručnu provjeru (npr. OIB). |
|  |  |  |  |  |

---

## 2.5 Značajne promjene zahtjeva

**Cilj:** Sačuvati razlog promjena koje mijenjaju dogovoreni opseg, prioritet ili kriterije prihvaćanja, uz istodobno održavanje prethodnih zahtjeva ažurnima.

| Datum | ID zahtjeva | Promjena i razlog | Dogovoreno s / dokaz |
| --- | --- | --- | --- |
|  |  |  |  |

<details>
<summary><strong>Primjer značajne promjene</strong></summary>

**Primjer scenarija — odluka tijekom pregleda CrisisMapa:**

| Datum | ID zahtjeva | Promjena i razlog | Dogovoreno s / dokaz |
| --- | --- | --- | --- |
| [Datum pregleda] | F-004, NF-01 | Dodati ručni odabir lokacije kada korisnik odbije geolokaciju preglednika; bez toga građanin ne može podnijeti prijavu kriznog događaja s lokacijom. | [Poveznica na stvarnu bilješku pregleda ili zadatak tima] |

**Razlog promjene:** Zapis navodi zahvaćene stavke i objašnjava razlog iz perspektive korisnika, a zatim upućuje na dokaz odluke. Nakon stvarne odluke ažurirajte trenutačne zahtjeve; stilska izmjena teksta ne zahtijeva zapis u dnevniku promjena. Trenutačni red zadataka držite u alatu za praćenje zadataka.

</details>

---

## 2.6 Sljedivost zahtjeva

**Cilj:** Osigurati dvosmjernu sljedivost — povezati zahtjeve s opisanim ponašanjem, dijelovima sustava koji ih ostvaruju te ispitivanjem ili drugom provjerom.

| ID zahtjeva | Obrazac uporabe / modul | Arhitekturna realizacija | Ispitivanje ili provjera |
| --- | --- | --- | --- |
| F-001 | [UC-001] | [Komponenta, modul ili drugo mjesto realizacije] | [ST-01, CT-01 ili druga provjera] |
|  |  |  |  |

<details>
<summary><strong>Sljedivost zahtjeva do obrazaca uporabe i ispitivanja</strong></summary>

**Primjer — poveznice zahtjeva CrisisMapa:**

| ID zahtjeva | Obrazac uporabe / ponašanje | Arhitekturna realizacija | Ispitivanje / provjera |
| --- | --- | --- | --- |
| F-004 | UC-002 Podnošenje prijave kriznog događaja | Web-klijent; API za prijave; usluga za prijave | ST-01 Valjano podnošenje prijave; ST-02 Nedostaje obavezna lokacija; ST-03 Dopuštenje geolokacije odbijeno |
| F-005 | Odabir primatelja obavijesti | Proces za obavijesti | CT-06 Primatelji obavijesti; potpuna dostava obavijesti nije pokrivena ovom provjerom |

**Pokrivenost i dokazi:** CT-06 provjerava odabir primatelja za F-005, ali sam po sebi ne dokazuje da je obavijest stvarno dostavljena; ako projekt zahtijeva potpunu dostavu od početka do kraja, za taj dio treba postojati zasebna provjera. Svaki redak pokazuje gdje je zahtjev opisan kao ponašanje, gdje je ostvaren u sustavu i gdje se nalazi dokaz njegove provjere. Zahtjev ne mora imati zaseban obrazac uporabe ako ne opisuje korisničku interakciju; tada navedite relevantno ponašanje, arhitekturni element ili drugi prikladan način realizacije. U svojem projektu koristite stvarne nazive komponenata, modula i provjera iz dokumentacije i implementacije. Nedostajuću poveznicu ostavite praznom ili označite kao *na čekanju* uz kratak razlog. Stvarni rezultati ispitivanja bilježe se uz ispitne slučajeve; ova tablica pokazuje gdje ih pronaći.

</details>

---

## 2.7 Pregled zahtjeva

**Cilj:** Zabilježiti važne povratne informacije o zahtjevima i odluke koje iz njih proizlaze na dvije formalne kontrolne točke projekta, u 7. i 14. tjednu.

| Kontrolna točka | Povratna informacija ili nalaz | Odluka o zahtjevu ili opsegu |
| --- | --- | --- |
| Iteracija 1 | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |
| Demo 2 | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |
| Završni pregled | [Povratna informacija] | [Odluka; vidi odjeljak 2.5] |

<details>
<summary><strong>Odluke o zahtjevima nakon pregleda</strong></summary>

**Primjer scenarija — pregled zahtjeva CrisisMapa:**

| Kontrolna točka | Povratna informacija ili nalaz | Odluka o zahtjevu ili opsegu |
| --- | --- | --- |
| Iteracija 1 | Demonstracija pokazuje da odbijanje pristupa lokaciji preglednika onemogućuje podnošenje prijave. | Dogovoriti ručni odabir lokacije; ažurirati F-004 i primjer NF-01 te razlog zabilježiti u odjeljku 2.5. |

**Odluka povezana s povratnom informacijom:** Zapis povezuje opaženo ponašanje s konkretnom promjenom zahtjeva i izbjegava zapisnik cijelog sastanka. Zapišite samo stvarne povratne informacije i odluke za kontrolne točke. Konzultacije u nekom drugom tjednu ne stvaraju dodatnu obaveznu predaju.

</details>

---

>**Završna provjera:** Zahtjevi su aktualni, provjerljivi i povezani. Svaki važan zahtjev moguće je pratiti od njegova opisa i ponašanja preko mjesta realizacije u sustavu do odgovarajućeg ispitivanja ili druge provjere. Poveznice koriste stvarne identifikatore i nazive iz projektne dokumentacije.
