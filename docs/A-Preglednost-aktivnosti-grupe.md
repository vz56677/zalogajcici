[11:23, 19/11/2026] Viki <3: # A. Pregled aktivnosti grupe

*Cilj:* Prikazati kako je tim organizirao projekt, raspodijelio rad, reagirao na važne probleme i pridonio rezultatu. Zapisi trebaju opisivati ishode, a ne samo navoditi da je sastanak održan ili da se o nekoj temi raspravljalo.

## A.1 Evidencija sastanaka

*Cilj:* Voditi kratak zapis projektnih sastanaka i tjedne koordinacije: tko je sudjelovao, što je odlučeno i koja je radnja uslijedila. Raspravu koja nije dovela do odluke zabilježite samo ako je važno pitanje ostalo otvoreno.

*Kratice članova tima:* GD = Gabrijel Dumbović; FK = Klara Ferišak; KL = Karlo List; LP = Lucija Perica; DR = Domagoj Radman; ES = Ena Špoler; VZ = Viktor Žigić.

| Datum i sudionici | Glavna tema | Donesena odluka ili zadatak  | Poveznica na zadatak ili  rezultat, ako postoji |
| --- | --- | --- | --- |
|8.10. GD, FK, KL, LP, DR, ES, VZ|Definiranje uloge voditelja i upoznavanje tima|Voditelj tima je LP, dogovoren idući sastanak||
|15.10. GD, FK, KL,LP,ES,VZ |Završno definiranje funkcijskih i nefunkcijskih zahtjeva, završavanje opsega projekta i podjela timskih uloga za daljnji rad  | Dodani funkcijski zahtjevi, propisani issues za idući sastanak | [Analiza zahtjeva](./2-Analiza-zahtjeva.md) ||
|22.10. GD, FK, KL, LP, DR, ES, VZ| Podijela posla za izradu dokumentacije 1. i 2. |Dogovoren je i raspodijeljen posao za izradu dokumentaciju| [Izrađena dokumentacija 1](./1-Projekt.md) [Izrađena dokumentacija 2](./2-Analiza-zahtjeva.md) ||
|29.10. GD, FK, KL, LP, DR, ES, VZ| Podijela posla za izradu dokumentacije 3. i 4. |Dogovoren je i raspodijeljen posao za izradu dokumentaciju| *[Izrađena dokumentacija 3](./3-Obrasci-uporabe.md) [Izrađena dokumentacija 2](./4-Arhitektura-i-oblikovanje.md) ||
|5.11. GD, FK, KL, LP, DR, ES, VZ| Definiranje arhitekture sustava i početak izrade aplikacije |Dogovorena je arhitektura sustava i raspodjeljen posao za izradu frontenda, backenda i baze podataka| Arhitektura sustava ||

Za svaki relevantan sastanak ili zapis tjedne koordinacije upišite jedan sažet redak. Dodijeljeni zadatak treba imati odgovornu osobu; za neriješeno pitanje treba navesti tko će ga dalje obraditi. Sam naslov sastanka ne pokazuje što se promijenilo. U ovu evidenciju nemojte kopirati cijeli popis issuea ni prijepis razgovora.

## A.2 Plan rada

**Trenutačni plan:** Rad je organiziran po projektnim tjednima, a plan ažurira voditeljica tima Lucija Perica (LP).

| Razdoblje/Tjedan | Glavni planirani rad | Odgovorni članovi | Poveznica na zadatke ili rezultat |
| --- | --- | --- | --- |
| Projektni tjedan 1 | Postavljanje početne strukture projekta i GitHub repozitorija te priprema predložaka dokumentacije. Definiranje osnovne organizacije rada i upoznavanje članova tima s projektom. | Svi članovi | Početni commitovi i README. |
| Projektni tjedan 2 | Definiranje cilja, opsega i problema koji projekt rješava te prepoznavanje dionika i korisnika sustava. Izrada početnih funkcijskih i nefunkcijskih zahtjeva. | LP, FK, GD, ES | Poglavlje 2 – Analiza zahtjeva. |
| Projektni tjedan 3 | Dovršavanje analize zahtjeva, razrada funkcionalnosti i provjera usklađenosti zahtjeva s ciljevima projekta. Priprema dokumentacije za izradu obrazaca uporabe i arhitekture. | LP, ES, FK, KL, GD | Poglavlja 2.1 – 2.7. |
| Projektni tjedan 4 | Izrada obrazaca uporabe i pripadajućih UML dijagrama te definiranje ključnih arhitekturnih odluka. Paralelno se pripremaju početna struktura frontenda i backenda. | VZ, GD, DR, LP, ES, KL | Poglavlja 3 i 4.1. |
| Projektni tjedan 5 | Oblikovanje modela podataka, razreda i SQL testnih podataka te razvoj osnovnih funkcionalnosti aplikacije. Započinje implementacija prikaza i objave ponuda, rezervacija i prijave korisnika. | KL, ES, FK, DR, GD | Poglavlje 4.3; issuei #57, #59, #60 i #64. |
| Projektni tjedan 6 | Povezivanje frontenda i backenda, prelazak frontenda na React te ispravljanje problema u prikazu i objavi ponuda. Razvijaju se funkcionalnosti rezervacija, otkazivanja rezervacija i podrške humanitarnim organizacijama. | Svi članovi | Issuei #71, #80, #84, #86, #89 i #93. |

Tijekom razvoja prioriteti su prilagođavani napretku projekta i problemima otkrivenima tijekom implementacije. U završnoj fazi naglasak je na povezivanju svih dijelova sustava, provjeri funkcionalnosti i usklađivanju dokumentacije s implementacijom.


## A.3 Aktivnosti i doprinosi članova


| Aktivnost ili rezultat | Član | Prijavljeni sati | Reprezentativni rezultat ili poveznica |
| --- | --- | --- | --- |
| Izrada i uređivanje analize zahtjeva, definiranje dionika i funkcionalnosti te priprema prijedloga frontend dizajna. Sudjelovanje u povezivanju frontenda i backenda, ispravljanju dokumentacije i završnoj provjeri zahtjeva. | LP | 15 | Poglavlje 2 – Analiza zahtjeva; frontend dizajn; povezivanje frontenda i backenda. |
| Izrada specifikacija obrazaca uporabe, definiranje glavnih i alternativnih tijekova te izrada UML dijagrama. Izrada početnog izgleda frontenda i sudjelovanje u njegovu prelasku na React. | VZ | 15 | Poglavlje 3 – Obrasci uporabe; UML dijagrami; issue #71. |
| Izrada dokumentacije o pristupu i opravdanosti projekta te oblikovanje modela podataka i dijagrama razreda. Priprema SQL testnih podataka i definiranje ograničenja vezanih uz ponude. | KL | 12 | Poglavlje 4.3 – Model podataka i oblikovanje razreda; SQL testni podaci. |
| Implementacija postupka rezervacije, provjere dostupnosti proizvoda i ažuriranja zaliha. Razvoj funkcionalnosti otkazivanja rezervacija i povezivanje s backendom. | KF | 17 | Issue #57, #84 i #89. |
| Razvoj funkcionalnosti za objavu i pregled ponuda, povezivanje API poziva s korisničkim sučeljem te obrada pogrešaka. Priprema `.env.example` datoteke s uputama za konfiguraciju aplikacije. | ES | 17 | Issue #59, #60 i #80; `.env.example`. |
| Izrada backend strukture i implementacija Google OAuth2 prijave te upravljanja korisničkim ulogama. Razvoj registracije i autorizacije humanitarnih organizacija te povezivanje njihova frontenda i backenda. | DR | 12 | Issue #64, #86 i #93. |
| Postavljanje početne strukture backend projekta i sudjelovanje u izradi arhitekturne dokumentacije. Rješavanje pogrešaka i dorada funkcionalnosti tijekom razvoja aplikacije. | GD | 16 | Poglavlje 4.1 – Ključne arhitekturne odluke; issue #45 i #88. |

Sati predstavljaju samoprijavljenu procjenu svakog člana i potrebno ih je dopuniti prema stvarno uloženom vremenu. Git povijest ne prikazuje sav rad na sastancima, planiranju, testiranju i zajedničkoj izradi dokumentacije.

## A.4 Pregled promjena u repozitoriju
## A.4 Pregled promjena u repozitoriju


**Prikaz aktivnosti repozitorija:** [GitHub Contributors – Spasi Zalogaj](https://github.com/vz56677/zalogajcici/graphs/contributors)

**Razdoblje prikaza:** Od početka projekta do 19. 11. 2026.

Tijekom razvoja projekta promjene u repozitoriju obuhvaćale su izradu projektne dokumentacije, razvoj frontenda i backenda, oblikovanje baze podataka te implementaciju glavnih funkcionalnosti aplikacije. U kasnijim fazama rada naglasak je bio na povezivanju frontenda i backenda, autentifikaciji korisnika, rezervacijama, objavi ponuda i funkcionalnostima za humanitarne organizacije.

GitHubov graf prikazuje doprinose članova prema njihovim commitovima kroz vrijeme. Povijest repozitorija uključuje i promjene napravljene kroz zasebne grane i spojene pull requestove, što omogućuje praćenje razvoja projekta i suradnje članova tima.

Broj commitova nije izravna mjera ukupnog doprinosa pojedinog člana jer ne obuhvaća sav rad na planiranju, sastancima, testiranju, pregledu koda i zajedničkoj izradi dokumentacije. Zbog toga se prikaz aktivnosti repozitorija promatra zajedno s evidencijom doprinosa iz poglavlja A.3.f.

## A.5 Ključni inženjerski izazovi i rješenja

**Cilj:** Dokumentirati stvarne tehničke i organizacijske prepreke s kojima se tim suočio, poduzete korektivne inženjerske korake te konačni ishod. Odaberite konkretne izazove umjesto općenite pohvale timskog rada.

| Izazov | Poduzeta radnja | Ishod ili preostalo ograničenje |
| --- | --- | --- |
|  |  |  |

<details>
<summary><strong>Rješavanje nesklada formata lokacije</strong></summary>

**Primjer — CrisisMap:**

| Izazov | Poduzeta radnja | Ishod ili preostalo ograničenje |
| --- | --- | --- |
| Odabrani položaj na karti prikazivao se u klijentu, ali podnesena prijava nije zadržavala koordinate. | AK i EP usporedile su zahtjev preglednika s poljima lokacije koje očekuje API; AK je uskladila format zahtjeva, a LM je dodao provjeru valjanosti lokacije. | Nova prijava u ovom se scenariju može dohvatiti sa spremljenim položajem; tim povezuje odgovarajuću promjenu integracije i provjeru. |

**Problem, odgovor i ishod:** Svaki se dio može provjeriti u projektu. Rečenica poput „imali smo poteškoće s integracijom i riješili ih timskim radom” ne bi čitatelju rekla što nije radilo ni kako je rješenje promijenjeno.

</details>

---

>**Završna provjera:** Sastanci navode smislene ishode i odgovorne osobe; plan odražava namjeravani rad; tablica uloženog rada i generirani prikaz repozitorija odnose na ovaj vidljivi projekt; konkretni izazovi povezani su s radnjama i njihovim rezultatima. 
