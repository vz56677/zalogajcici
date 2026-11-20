# A. Pregled aktivnosti grupe

Prikazuje kako je tim organizirao projekt, raspodijelio rad, reagirao na važne probleme i pridonio rezultatu.

## A.1 Evidencija sastanaka

*Kratice članova tima:* GD = Gabrijel Dumbović; FK = Klara Ferišak; KL = Karlo List; LP = Lucija Perica; DR = Domagoj Radman; ES = Ena Špoler; VZ = Viktor Žigić.

| Datum i sudionici | Glavna tema | Donesena odluka ili zadatak  | Poveznica na zadatak ili  rezultat, ako postoji |
| --- | --- | --- | --- |
|8.10. GD, FK, KL, LP, DR, ES, VZ|Definiranje uloge voditelja i upoznavanje tima|Voditelj tima je LP, dogovoren idući sastanak||
|15.10. GD, FK, KL,LP,ES,VZ |Završno definiranje funkcijskih i nefunkcijskih zahtjeva, raspodjela posla za izradu dokumentacije  | Dodani funkcijski zahtjevi, propisani issues za idući sastanak | [Analiza zahtjeva](./2-Analiza-zahtjeva.md) Issues: #1 - #9||
|22.10. GD, FK, KL, LP, DR, ES, VZ| Pregled i plan za dovršavanje dokumentacije te podjela projektnih uloga za implementaciju | Dogovoreno što još treba napraviti za dokumentaciju. Raspodijeljene uloge za implementaciju prve inačice (baza, frontend, backend, deploy)| [Izrađena dokumentacija 1](./1-Projekt.md) [Izrađena dokumentacija 2](./2-Analiza-zahtjeva.md) ||
|29.10. GD, FK, KL, LP, DR, ES, VZ| Pregled dovršene dokumentacije, odluka o bazi i početak implementacije | Pregled dokumentacije. Do sastanka su napravljene dvije verzije baze podataka - tim se odlučuje za drugu verziju. Definiranje arhitekture sustava i početak izrade aplikacije | [Izrađena dokumentacija 3](./3-Obrasci-uporabe.md), [Izrađena dokumentacija 4](./4-Arhitektura-i-oblikovanje.md), [baza podataka (naknadno kao flyway migracija)](../backend/src/main/resources/db/migration/V1__init.sql) Issues #18 - #43||
|5.11. GD, FK, KL, LP, DR, ES, VZ| Plan nastavka izrade aplikacije | Pregled napravljenog: dizajn sučelja te prve verzije frontenda i backenda. Dogovorena izrada funkcionalnosti koje nedostaju (prijava), popravak i nadopuna postojećih stavki, povezivanje komponenti sustava te postavljanje aplikacije na Render i baze na Neon. | [javno dostupna aplikacija](https://spasizalogaj.onrender.com/)||
|19.11. GD, FK, KL, LP, DR, ES, VZ| Popravak dokumentacije na temelju povratnih informacije asistentice | Odlučeno ponovno napraviti dijagrame obrasca uporabe i razreda te popraviti ostale manje greške | [UC dijagrami](./puml/), [dijagrami razreda](./4-Arhitektura-i-oblikovanje.md)||



## A.2 Plan rada

**Trenutačni plan:** Rad je organiziran po projektnim tjednima, a plan ažurira voditeljica tima Lucija Perica (LP).

| Razdoblje/Tjedan | Glavni planirani rad | Odgovorni članovi | Poveznica na zadatke ili rezultat |
| --- | --- | --- | --- |
| Projektni tjedan 1 | Postavljanje početne strukture projekta i GitHub repozitorija te priprema predložaka dokumentacije. Definiranje osnovne organizacije rada i upoznavanje članova tima s projektom. | Svi članovi | Početni commitovi i README. |
| Projektni tjedan 2 | Definiranje cilja, opsega i problema koji projekt rješava te prepoznavanje dionika i korisnika sustava. Definiranje funkcijskih i nefunkcijskih zahtjeva. Početak izrade baze podataka. | LP, FK, GD, ES | Poglavlje 2 – Analiza zahtjeva. |
| Projektni tjedan 3 | Dovršavanje analize zahtjeva, razrada funkcionalnosti i provjera usklađenosti zahtjeva s ciljevima projekta. Priprema dokumentacije za izradu obrazaca uporabe i arhitekture. | LP, ES, FK, KL, GD | Poglavlja 2.1 – 2.7. |
| Projektni tjedan 4 | Izrada obrazaca uporabe i pripadajućih UML dijagrama te definiranje ključnih arhitekturnih odluka. Paralelno se pripremaju početna struktura frontenda i backenda. | VZ, GD, DR, LP, ES, KL | Poglavlja 3 i 4.1. |
| Projektni tjedan 5 | Oblikovanje modela podataka, razreda i SQL testnih podataka te razvoj osnovnih funkcionalnosti aplikacije. Započinje implementacija prikaza i objave ponuda, rezervacija i prijave korisnika. | KL, ES, FK, DR, GD | Poglavlje 4.3; issuei #57, #59, #60 i #64. |
| Projektni tjedan 6 | Povezivanje frontenda i backenda, prelazak frontenda na React te ispravljanje problema u prikazu i objavi ponuda. Razvijaju se funkcionalnosti rezervacija, otkazivanja rezervacija i podrške humanitarnim organizacijama. | Svi članovi | Issuei #71, #80, #84, #86, #89 i #93. |

Tijekom razvoja prioriteti su prilagođavani napretku projekta i problemima otkrivenima tijekom implementacije. U završnoj fazi naglasak je na povezivanju svih dijelova sustava, provjeri funkcionalnosti i usklađivanju dokumentacije s implementacijom.


## A.3 Aktivnosti i doprinosi članova


| Aktivnost ili rezultat | Član | Prijavljeni sati | Reprezentativni rezultat ili poveznica |
| --- | --- | --- | --- |
| Izrada i uređivanje analize zahtjeva, definiranje dionika i funkcionalnosti te priprema prijedloga frontend dizajna. Sudjelovanje u povezivanju frontenda i backenda, ispravljanju dokumentacije i završnoj provjeri zahtjeva. | LP | 27 | frontend dizajn sučelja u Figmi, [rad na frontendu](../frontend/frontend/src/pages/), povezivanje frontenda i backenda, [prva verzija baze](../db/database.sql) dijelovi dokumentacije i organizacija tima |
| Izrada specifikacija obrazaca uporabe, definiranje glavnih i alternativnih tijekova te izrada UML dijagrama. Izrada početnog izgleda frontenda i sudjelovanje u njegovu prelasku na React. | VZ | 25 | [Poglavlje 3 – Obrasci uporabe](./3-Obrasci-uporabe.md); [rad na frontendu](../frontend/frontend/src/) prve verzije UML dijagrama; issue #71. |
| Izrada dokumentacije o pristupu i opravdanosti projekta te oblikovanje modela podataka i dijagrama razreda. Priprema SQL testnih podataka i definiranje ograničenja vezanih uz ponude. | KL | 28 | [Poglavlje 4.3 – Model podataka i oblikovanje razreda](./4-Arhitektura-i-oblikovanje.md); [SQL testni podaci](../db/seed.sql). |
| Implementacija postupka rezervacije, provjere dostupnosti proizvoda i ažuriranja zaliha. Razvoj funkcionalnosti otkazivanja rezervacija i povezivanje s backendom. | FK | 25 | [rezervacija ponuda](../backend/src/main/java/com/zalogajcici/backend/rezervacija), relevantni testovi, [analiza zahtjeva](./2-Analiza-zahtjeva.md), deploy (aplikacija na Renderu i baza na Neonu)|
| Razvoj funkcionalnosti za objavu i pregled ponuda, povezivanje API poziva s korisničkim sučeljem te obrada pogrešaka. | ES | 29 | [ponude](../backend/src/main/java/com/zalogajcici/backend/ponuda/), [prodajno mjesto](../backend/src/main/java/com/zalogajcici/backend/prodajnomjesto/), [upravljanje zalihama](../backend/src/main/java/com/zalogajcici/backend/zaliha/), relevantni testovi, [zajedničke greške i sat](../backend/src/main/java/com/zalogajcici/backend/common), [UC dijagram sustava](./puml/3-1-system-UC-V2.puml) |
| Izrada backend strukture i implementacija Google OAuth2 prijave te upravljanja korisničkim ulogama. Razvoj registracije i autorizacije humanitarnih organizacija te povezivanje njihova frontenda i backenda. | DR | 25 | Issue #64, #86 i #93. [korisnik i prijava](../backend/src/main/java/com/zalogajcici/backend/korisnik), [UC dijagrami komponenti](./puml)|
| Postavljanje početne strukture backend projekta i sudjelovanje u izradi arhitekturne dokumentacije. Rješavanje pogrešaka i dorada funkcionalnosti tijekom razvoja aplikacije. | GD | 20 | Poglavlje 4.1 – Ključne arhitekturne odluke; issue #45 i #88. |

## A.4 Pregled promjena u repozitoriju

**Prikaz aktivnosti repozitorija:** [GitHub Contributors – Spasi Zalogaj](https://github.com/vz56677/zalogajcici/graphs/contributors)

**Razdoblje prikaza:** Od početka projekta do 19. 11. 2026.

Tijekom razvoja projekta promjene u repozitoriju obuhvaćale su izradu projektne dokumentacije, razvoj frontenda i backenda, oblikovanje baze podataka te implementaciju glavnih funkcionalnosti aplikacije. U kasnijim fazama rada naglasak je bio na povezivanju frontenda i backenda, autentifikaciji korisnika, rezervacijama, objavi ponuda i funkcionalnostima za humanitarne organizacije.

GitHubov graf prikazuje doprinose članova prema njihovim commitovima kroz vrijeme. Povijest repozitorija uključuje i promjene napravljene kroz zasebne grane i spojene pull requestove, što omogućuje praćenje razvoja projekta i suradnje članova tima.

Broj commitova nije izravna mjera ukupnog doprinosa pojedinog člana jer ne obuhvaća sav rad na planiranju, sastancima, testiranju, pregledu koda i zajedničkoj izradi dokumentacije. Zbog toga se prikaz aktivnosti repozitorija promatra zajedno s evidencijom doprinosa iz poglavlja A.3.f.

## A.5 Ključni inženjerski izazovi i rješenja

**Cilj:** Dokumentirati stvarne tehničke i organizacijske prepreke s kojima se tim suočio, poduzete korektivne inženjerske korake te konačni ishod. Odaberite konkretne izazove umjesto općenite pohvale timskog rada.


Privremeno prazno, potrebno savjetovanje sa timom, koliko velik problem mora biti da bi ga zapisali.


| Izazov | Poduzeta radnja | Ishod ili preostalo ograničenje |
| --- | --- | --- |
| Napravljena baza podataka nadmašuje opseg projekta. Drugi članovi tima teško razumiju bazu. | Na temelju prve verzije baze, napravljena je nova jednostavnija baza. | Sustav se izgradio na temelju nove verzije baze. |
| Korištenje Reacta za frontend | Frontend je naknadno prebačen na React biblioteku umjesto običnog JavaScripta | Novi frontend koristi React |
| Ispravljeni zahtjevi | Funkcijski i nefunkcijski zahtjevi su s asistenticom promjenjeni, pa su dokumentacije ažurirane na reference za nove zahtjeve | zahtjevi su ažurirani |
| GitHub pretplata je potrebna za praćenje aktivnosti | Repozitorij će umjesto toga na kraju projekta biti javno objavljen | Aktivnost će se vidjeti na kraju projekta |
| Besplatna verzija Rendera je spora pri prvom otvaranju | Pokušat ćemo imati stranicu otvorenu na jednom računalu prije bitnog testiranja | Ostalim članovima tima se stranica prije učitava |
|  |  |  |


---

