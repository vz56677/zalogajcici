# 4. Arhitektura i oblikovanje

**Cilj:** Definirati arhitekturni stil, dekompoziciju programa na podsustave i module, organizaciju podataka te dinamičko ponašanje tijekom izvođenja. Arhitektura predstavlja temelj za implementaciju i ažurira se pri svakoj važnijoj promjeni projektnih odluka.

| Arhitekturni pogled | Ključno inženjersko pitanje | Primijenjeni model ili dijagram |
| :--- | :--- | :--- |
| **Strategija rješenja** | Koji stil i temeljne odluke određuju program? | Tablica odluka (odjeljak 4.1) |
| **Struktura podsustava** | Koji su glavni dijelovi i kako komuniciraju? | Dijagram komponenata |
| **Model podataka** | Kako su strukturirani trajni podaci programa? | Relacijski ER dijagram |
| **Struktura koda** | Kako su dodijeljene odgovornosti i ključne klase? | Reprezentativni dijagram razreda |
| **Dinamičko ponašanje** | Kako moduli surađuju pri obradi ključnih transakcija? | sekvencijski dijagram |
| **Stanja sustava** | Kako ključni domenski objekt mijenja svoja stanja? | Dijagram stanja |
| **Fizički razmještaj** | Na kojim se izvršnim čvorovima program izvodi? | Dijagram razmještaja (poglavlje 6) |

---

Za svaki UML dijagram čuvajte čitljivu sliku i PlantUML izvor, kako je opisano na stranici [Home](./Home.md). U dovršenoj stranici koristite vlastite projektne odluke i modele.

## 4.1 Ključne arhitekturne odluke

**Cilj:** Objasniti nekoliko arhitekturnih odluka koje najviše utječu na rješenje i navesti njihove projektno specifične razloge.

| Odluka | Razmatrana alternativa | Projektno specifično obrazloženje i kompromis |
| --- | --- | --- |
| Klijent-poslužitelj arhitektura (Spring Boot REST API + React/Native klijent) | Više zasebnih mikrousluga ili klasični server-side rendering (MVC) | Za opseg aplikacije, monolitna Spring Boot struktura je lakše održiva i brža za razvoj; kompromis je što se aplikacija skalira u komadu, te zato padom jednog servisa pada cijela aplikacija. |
| Relacijaska baza podataka (PostgreSQL) | Dokumentno-orijentirana baza podataka (MongoDB) | Podaci koje je potrebno spremiti u bazu su skloni relacijskom modelu baze podataka. Relacijska baza podataka osigurava dosljednost. |
| Vanjska autentifikacija (OAuth) | Vlastiti sustav upravljanja računima i kozinkama | Korištenje OAuth provjere skraćuje vrijeme prijave i povećava sigurnost, dok je kompromis ovisnost o vanjskom servisu. |


## 4.2 Gradivni blokovi i UML dijagram komponenata

**Cilj:** Prikazati logičku podjelu programa na komponente, njihova sučelja i vanjske integracije. Dijagram komponenata prikazuje logičku organizaciju, dok dijagram razmještaja prikazuje fizičko izvođenje. Ovaj prikaz ažurirajte kako sustav poprima konačan oblik.

**Dijagram komponenata:** [Ugradite čitljiv UML dijagram komponenata.]  
**PlantUML izvor:** [Poveznica na odgovarajuću `.puml` datoteku.]

| Komponenta | Odgovornost | Razlog za ovu granicu |
| --- | --- | --- |
|  |  |  |

Započnite crtanjem granice oko predloženog sustava. Unutar nje prepoznajte dijelove s različitim odgovornostima; planiranu bazu podataka aplikacije smjestite unutar granice, a usluge trećih strana izvan nje. Važne veze označite podatkom ili uslugom koja njima prolazi. Logička komponenta može biti modul unutar jedne poslužiteljske aplikacije: zaseban pravokutnik ne znači zasebno postavljenu uslugu. Tijekom implementacije ažurirajte dijagram kada se promijene odgovornosti ili veze. U završnoj inačici čitatelj treba moći povezati njegove dijelove s kodom. Dijagram razmještaja odgovara na pitanje gdje se ti dijelovi stvarno izvode.

<details>
<summary><strong>Granica sustava, odgovornosti i vanjske ovisnosti</strong></summary>

**Primjer — CrisisMap:** Predložena arhitektura podržava podnošenje prijava kriznih događaja, prikaz na karti i obavijesti o potvrđenim prijavama.

![Dijagram komponenata sustava CrisisMap](./assets/diagrams/4-1-crisismap-system-CMP.svg)

**Slika 4.1. Dijagram komponenata sustava CrisisMap**

**PlantUML izvor:** [izvor](./puml/4-1-crisismap-system-CMP.puml)

| Dio | Odgovornost | Razlog za ovu granicu |
| --- | --- | --- |
| Web-klijent | Prikazuje prijave i prikuplja podatke za novu prijavu. | Korisnička interakcija odvojena je od pravila prijave i trajne pohrane. |
| API za prijave | Prihvaća zahtjeve za rad s prijavama i vraća odgovore. | Odgovornosti izložene putem HTTP-a odvojene su od obrade prijava. |
| Usluga za prijave | Primjenjuje pravila prijave i pristupa pohranjenim prijavama. | Pravila provjere valjanosti i pohrane prijava imaju jasno mjesto odgovornosti. |
| Proces za obavijesti | Određuje korisnike koji trebaju primiti obavijest i traži od vanjskog pružatelja da je pošalje. | Obrada obavijesti odvojena je od odgovaranja na korisnikov zahtjev za prijavu. |

**Analiza primjera:** Vanjska granica odvaja predloženi sustav od pružatelja kartografskih usluga i pružatelja obavijesti. Unutarnja granica grupira API za prijave, uslugu za prijave i proces za obavijesti unutar jedne poslužiteljske aplikacije; njihovi zasebni simboli prikazuju predložene odgovornosti, a ne neovisno postavljene usluge. PostgreSQL pohranjuje podatke aplikacije, dok pružatelj kartografskih usluga osigurava kartografske pločice. Strelice sažimaju ovisnosti, a ne redoslijed transakcije; taj redoslijed prikazuje sekvencijski dijagram. Izostavite proces za obavijesti i Firebase Cloud Messaging ako obavijesti nisu dio odobrenog opsega. Pružatelja prijave u sustav uključite samo ako to zahtijeva odobreni zadatak. Kako implementacija napreduje, ispravite svaku predloženu granicu koja se pokaže netočnom; završni dijagram mora odgovarati onome što je tim izgradio. Jedan koherentan prikaz komponenata korisniji je od nekoliko gotovo jednakih inačica s proturječnim vezama.


## 4.3 Model podataka i oblikovanje razreda

**Cilj:** Objasniti važne informacije koje aplikacija pohranjuje i jedno reprezentativno područje oblikovanja na razini razreda. Model podataka i UML dijagram razreda odgovaraju na različita pitanja; jedno ne zamjenjuje drugo.

### 4.3.1 ER dijagram i model podataka

**Cilj:** Prikazati važne pohranjene entitete, njihove identifikatore i veze te dovoljno atributa za razumijevanje podataka aplikacije. ER dijagram odvojen je od reprezentativnog UML dijagrama razreda u nastavku.

**ER dijagram:** [Ugradite čitljiv dijagram projekta.]  
**Izvor dijagrama:** [Poveznica na njegov izvor.]  
**Implementacija:** [Navedite gdje je odgovarajući model podataka definiran u projektu.]

<details>
<summary><strong>Jedna prijava poplave i povezani podaci</strong></summary>

**Primjer — CrisisMap:** Ana putem svojeg korisničkog računa podnosi prijavu poplave `R-104`. Odabire lokaciju na karti i označava razinu ozbiljnosti kao visoku. Građanin može podnijeti više prijava; svaka prijava pripada jednoj kategoriji kriznog događaja, primjerice *Poplava*. Aplikacija pohranjuje prijavu i njezine koordinate. Kartografske pločice dolaze od vanjskog pružatelja i nisu entiteti u modelu podataka aplikacije.

![ER dijagram podataka sustava CrisisMap](./assets/diagrams/4-2-crisismap-data-ERD.svg)

**Slika 4.2. ER dijagram podataka sustava CrisisMap**

**PlantUML izvor:** [izvor](./puml/4-2-crisismap-data-ERD.puml)

**Analiza primjera:** `R-104` ima jednog autora i jednu kategoriju; Ana i kategorija *Poplava* mogu biti povezane s više prijava. Dijagram prikazuje veze bez prepisivanja svih stupaca baze podataka ili pisanja drugog tabličnog opisa za svaki entitet. Ako aplikacija prihvaća i **anonimne** prijave, tu mogućnost prikažite u vezi s entitetom `Citizen`. Ako su kategorije fiksne vrijednosti, a ne pohranjeni zapisi, nemojte izmišljati tablicu kategorija. Završni ER dijagram usporedite s modelom trajne pohrane i objasnite samo razlike važne za razumijevanje projekta.

</details>

### 4.3.2 Reprezentativni UML dijagram razreda

**Cilj:** Prije implementacije reprezentativnog dijela sustava modelirati i prikazati razrede ili sučelja potrebna za dodjelu odgovornosti, operacija i veza. Oblikovanje ažurirajte kada se te odluke promijene.

**Dijagram razreda:** [Ugradite čitljiv UML dijagram razreda za reprezentativni dio oblikovanja.]  
**PlantUML izvor:** [Poveznica na odgovarajuću `.puml` datoteku.]  
**Referenca na kod pri završnom pregledu:** [Poveznica na odgovarajuću implementaciju.]

<details>
<summary><strong>Oblikovanje odgovornosti prijave i dopuštenih promjena</strong></summary>

**Primjer — CrisisMap:** Prijava sadrži pravila za promjenu vlastitog statusa. Razred tijeka rada koordinira podnošenje i pregled, dok sučelje repozitorija opisuje potrebne operacije trajne pohrane. Dijagram predlaže te odgovornosti prije implementacije.

![Dijagram razreda za obradu prijava kriznih događaja](./assets/diagrams/4-3-crisismap-domain-CD.svg)

**Slika 4.3. Dijagram razreda za obradu prijava kriznih događaja**

**PlantUML izvor:** [izvor](./puml/4-3-crisismap-domain-CD.puml)

**Analiza primjera:** `DisasterReport` sadrži dopuštena pravila promjene stanja pa tijek rada ne može prešutno odobriti već odbijenu prijavu. `ReportWorkflow` koordinira operaciju bez preuzimanja pravila prijelaza stanja same prijave; `ReportRepository` definira potrebne operacije pohrane. Kompozicija `Location` i odabrani tipovi prikazuju veze korisne za oblikovanje. Dijagram namjerno izostavlja stupce baze podataka i nepovezane razrede: ER model objašnjava pohranjene podatke, dok ovaj dijagram dodjeljuje programske odgovornosti i operacije. Ti su razredi odluka oblikovanja, a ne obavezni slojevi za svaki tim. Završnu implementaciju usporedite s početnim oblikovanjem i ažurirajte dijagram tamo gdje su se odluke promijenile; generiranje dijagrama iz koda tek na kraju ne može zamijeniti ovo početno modeliranje.

</details>

## 4.4 Ponašanje tijekom izvođenja

**Cilj:** Objasniti jednu važnu interakciju među dijelovima koji se izvode i životni ciklus jednog smislenog objekta. Odaberite primjere koji otkrivaju odluke ili neuspješne putove umjesto izrade dijagrama za svaku CRUD radnju.

### 4.4.1 Reprezentativni UML sekvencijski dijagram

**Cilj:** Prikazati redoslijed poruka za jedan reprezentativni scenarij i odgovore na važnu alternativu.

**Scenarij:** [Naziv interakcije; dodajte UC ID ako postoji.]  
**sekvencijski dijagram:** [Ugradite čitljiv UML sekvencijski dijagram.]  
**PlantUML izvor:** [Poveznica na odgovarajuću `.puml` datoteku.]

<details>
<summary><strong>Odabir lokacije, provjera valjanosti i podnošenje prijave</strong></summary>

**Primjer — CrisisMap:** Građanin podnosi prijavu kriznog događaja nakon što ručno odabere lokaciju ili zatraži položaj uređaja. Interakcija prikazuje i što se događa kada je pristup lokaciji odbijen, podaci prijave nisu valjani ili pohrana ne uspije.

![Sekvencijski dijagram podnošenja prijave kriznog događaja](./assets/diagrams/4-4-crisismap-report-submission-SD.svg)

**Slika 4.4. Sekvencijski dijagram podnošenja prijave kriznog događaja**

**PlantUML izvor:** [izvor](./puml/4-4-crisismap-report-submission-SD.puml)

**Analiza primjera:** Dva načina odabira lokacije te alternative provjere i pohrane imaju različite posljedice za korisnika; usporedite ih s odgovarajućim obrascem uporabe i njegovim alternativnim tijekovima. API preglednika za lokaciju mogućnost je preglednika; web-klijent, API, usluga i pohrana odgovaraju prikazu komponenata. Odaberite grane koje pomažu objasniti scenarij vašeg projekta i završni dijagram uskladite s implementiranom interakcijom.

</details>

### 4.4.2 Reprezentativni UML dijagram stanja

**Cilj:** Prikazati važna stanja jednog domenskog objekta ili eksplicitno upravljanog načina rada sustava te događaje ili uvjete koji ih mijenjaju.

**Vlasnik stanja i razlog odabira:** [Navedite objekt ili način rada sustava, kako se njegovo stanje prepoznaje i zašto su prijelazi važni.]  
**Dijagram stanja:** [Ugradite čitljiv UML dijagram stanja.]  
**PlantUML izvor:** [Poveznica na odgovarajuću `.puml` datoteku.]

<details>
<summary><strong>Složeno stanje sustava: koordinacija događaja i dostupnost usluge</strong></summary>

**Primjer — CrisisMap:** Ovaj primjer ne prikazuje životni ciklus jednog domenskog objekta, nego dva neovisna aspekta stanja cijelog sustava CrisisMap. Jedna regija opisuje način koordinacije kriznog događaja, a druga opaženu dostupnost usluge. Budući da se ta dva aspekta mogu mijenjati neovisno jedan o drugome, prikazani su kao ortogonalne regije istog složenog stanja sustava.

![Dijagram stanja prijave kriznog događaja](./assets/diagrams/4-5-crisismap-system-SM.svg)

**Slika 4.5. Dijagram stanja sustava s ortogonalnim regijama**

**PlantUML izvor:** [izvor](./puml/4-5-crisismap-system-SM.puml)

**Analiza primjera:** Vlasnik stanja u ovom je primjeru sustav CrisisMap, a ne pojedinačna prijava kriznog događaja. Dvije ortogonalne regije opisuju stanja koja vrijede istodobno: način koordinacije kriznog događaja i dostupnost usluge. Promjena stanja u jednoj regiji ne mora promijeniti stanje u drugoj; primjerice, aktivni krizni događaj može postojati dok je usluga potpuno ili djelomično dostupna.

Prijelazi imaju imenovane događaje ili uvjete, a grane nakon oporavka poslužiteljskog sustava uzimaju u obzir da pružatelj kartografskih usluga može i dalje biti nedostupan. Time primjer pokazuje situaciju u kojoj ortogonalne regije imaju stvarnu semantičku svrhu, a nisu samo način vizualnog grupiranja elemenata.

Ovo je napredniji primjer dijagrama stanja. Koristite takav oblik samo ako sustav stvarno održava dva ili više međusobno neovisnih aspekata stanja koji se mogu mijenjati istodobno. Za projekt u kojem je važan životni ciklus jednog objekta, primjerice prijave, rezervacije ili narudžbe, jednostavniji dijagram stanja tog objekta obično je prikladniji. Složenost dijagrama nije kriterij vrednovanja.

Pri završnom pregledu svaki prikazani prijelaz i uvjet usporedite s ponašanjem koje implementirana aplikacija stvarno podržava.

</details>

## 4.5 Zapisi arhitekturnih odluka

**Cilj:** Sačuvati razloge i obrazložiti odabrane važne arhitekturne odluke kada tablica u odjelku 4.1 nije dovoljna za očuvanje konteksta, razmatranih alternativa i prihvaćenih kompromisa.

### ADR-01: Centralizirana provjera korisničkih ovlasti

| Polje | Zapis odluke |
| --- | --- |
| Kontekst i cilj | Sustav SpasiZalogaj ima različite korisničke uloge (kupac, prodajno mjesto, humanitarna organizacija i administrator), pri čemu svaka uloga ima različite ovlasti. Pristup pojedinim funkcionalnostima dodatno ovisi o uvjetima poput aktivnog članstva prodajnog mjesta ili odobrenja profila humanitarne organizacije. Cilj je osigurati dosljednu i sigurnu provjeru korisničkih ovlasti u svim dijelovima sustava te spriječiti neovlašten pristup funkcionalnostima i podacima. |
| Kriteriji odluke | Zaštita osobnih podataka i administrativnih radnji; dosljedna provedba pravila na svim API zahtjevima; jednostavno održavanje i proširivanje korisničkih ovlasti. |
| Razmatrane alternative | Provjera uloga samo u korisničkom sučelju: jednostavna, ali nesigurna jer se API može pozivati izravno. Zasebne provjere u svakoj funkcionalnosti: provode se na poslužitelju, ali povećavaju mogućnost dupliciranja i nedosljednosti. Centralizirana autorizacija: zajednički mehanizam za provjeru korisničkih ovlasti na poslužiteljskoj strani, uz dodatnu provjeru posebnih uvjeta za izvršavanje pojedinih radnji. |
| Odluka i obrazloženje | Predlaže se centralizirana autorizacija. Za autentifikaciju korisnika koristi se vanjski servis, dok aplikacija provjerava korisničke ovlasti na temelju uloge i dodatnih uvjeta pristupa. Time se smanjuje opasnost od različitih pravila pristupa u pojedinim funkcionalnostima. Kompromis su dodatni razvoj zajedničkog mehanizma i potreba za jasno definiranim pravilima ovlasti. |
| Status | Predloženo. |


### ADR-02: Osiguravanje konzistentnosti podataka prilikom rezervacija

| Polje | Zapis odluke |
| --- | --- |
| Kontekst i cilj | Sustav SpasiZalogaj omogućuje korisnicima rezerviranje proizvoda i paketa hrane. Budući da više korisnika može istovremeno pokušati rezervirati posljednje raspoložive proizvode, potrebno je spriječiti rezerviranje količine veće od dostupne. Cilj je očuvati točnost podataka o zalihama i osigurati pouzdanost postupka rezerviranja. |
| Kriteriji odluke | Očuvanje konzistentnosti podataka, sprječavanje dvostrukih rezervacija i pouzdanost postupka rezerviranja. |
| Razmatrane alternative | Odvojena provjera i ažuriranje količine: jednostavnija implementacija, ali postoji mogućnost da više korisnika istovremeno rezervira istu količinu. Transakcijska obrada rezervacija: provjera dostupnosti, umanjenje količine i stvaranje rezervacije provode se kao jedna cjelina, uz odgovarajuću zaštitu od istovremenih izmjena. |
| Odluka i obrazloženje | Predlaže se transakcijska obrada rezervacija na poslužiteljskoj strani. Rezervacija se uspješno izvršava samo ako je tražena količina dostupna i promjena zalihe uspješno provedena. Ako neki korak ne uspije, cijela se operacija poništava. Time se sprječavaju dvostruke rezervacije, uz dodatnu složenost upravljanja transakcijama i vraćanja zaliha prilikom otkazivanja. |
| Status | Predloženo. |

### ADR-03: Odvojeno modeliranje rezervacija i uplata

| Polje | Zapis odluke |
| --- | --- |
| Kontekst i cilj | Sustav SpasiZalogaj omogućuje rezerviranje proizvoda i paketa hrane te podržava različite načine plaćanja, uključujući elektroničko plaćanje i plaćanje gotovinom prilikom preuzimanja. Budući da rezervacija ne mora biti odmah plaćena, potrebno je omogućiti zasebno evidentiranje rezervacija i pripadajućih uplata. Cilj je osigurati pregledno i pouzdano upravljanje rezervacijama neovisno o odabranom načinu plaćanja. |
| Kriteriji odluke | Preglednost podatkovnog modela, podrška različitim načinima plaćanja te jednostavnost održavanja i upravljanja podacima o rezervacijama i uplatama. |
| Razmatrane alternative | Jedinstveni model: podaci o rezervaciji i plaćanju pohranjuju se zajedno, što pojednostavljuje strukturu, ali otežava neovisno upravljanje uplatama. Odvojeni model: rezervacije i uplate pohranjuju se zasebno te se međusobno povezuju prema potrebi. |
| Odluka i obrazloženje | Predlaže se odvojeno modeliranje rezervacija i uplata. Takav pristup omogućuje podršku različitim načinima plaćanja te jasnije razdvaja podatke o rezerviranim proizvodima od podataka o izvršenim uplatama. Time se olakšava daljnje proširivanje funkcionalnosti plaćanja, uz prihvaćanje složenijeg podatkovnog modela i potrebe za održavanjem konzistentnosti povezanih podataka. |
| Status | Predloženo. |


## 4.6 Zajednička pravila oblikovanja (engl. Cross-cutting concerns)

**Cilj:** Prepoznati i opisati globalna pravila i mehanizme oblikovanja koji se primjenjujuju u više dijelova aplikacije, navesti gdje se planira primijeniti i ažurirati objašnjenje kako implementacija poprima konačan oblik. Uključite samo pravila koja su važna za ovaj projekt.

| Aspekt | Mehanizam | Pravilo | Mjesto primjene |
| --- | --- | --- | --- |
| Autentifikacija | Vanjski servis za autentifikaciju (OAuth 2.0) | Korisnička prijava i registracija oslanjaju se na vanjski servis; aplikacija ne pohranjuje korisničke lozinke. | Prijava i registracija korisnika. |
| Autorizacija | Zajednička provjera prava pristupa | Korisničke ovlasti provjeravaju se na poslužiteljskoj strani prije izvršavanja zaštićenih radnji. Pristup pojedinim funkcionalnostima ovisi o korisničkoj ulozi i dodatnim uvjetima poput aktivnog članstva ili odobrenja profila. | Poslužiteljski API za upravljanje ponudama, rezervacijama, donacijama i administracijom. |
| Validacija podataka | Provjera ulaza i poslovnih pravila | Pri objavi i rezervaciji provjeravaju se obvezni podaci, ispravnost količina i cijena te rokovi trajanja i preuzimanja. Nevaljani podaci ne smiju se pohraniti. | Poslužiteljska obrada objave ponuda i rezervacija. |
| Konzistentnost | Transakcije za rezervacije | Provjera zalihe, umanjenje raspoložive količine i izrada rezervacije odvijaju se u transakciji. Neuspjeh bilo kojeg koraka ne smije ostaviti djelomično izvršenu rezervaciju. | Poslužiteljska logika za rezerviranje proizvoda i paketa hrane. |
| Životni ciklus rezervacije | Provjera statusa i jednokratnog koda | Otkazivanje je dopušteno samo unutar zadanog roka; kod za preuzimanje veže se uz odgovarajuću rezervaciju i ne može se ponovno iskoristiti nakon potvrde preuzimanja. | Obrada rezervacija, otkazivanja i potvrđivanja preuzimanja. |
| Integracije i obrada pogrešaka | Provjera elektroničkog plaćanja | Kod elektroničkog plaćanja uspješnost transakcije provjerava se putem vanjskog servisa za plaćanje. U slučaju neuspješnog plaćanja ili nedostupnosti servisa korisniku se prikazuje odgovarajuća poruka o pogrešci. | Poslužiteljska obrada elektroničkih plaćanja i korisničko sučelje. |
| Prijenos i sučelje | HTTPS i poruke na hrvatskom | Klijent i poslužitelj komuniciraju preko HTTPS-a; korisničke poruke, uključujući pogreške, prikazuju se na hrvatskom jeziku. | Komunikacija između klijenta i poslužitelja te korisničko sučelje. |

## 4.7 Arhitekturni rizici i tehnički dug

**Cilj:** Navesti stvarne tehničke ili domenske rizike i svjesno prihvaćene kompromise oblikovanja, njihove posljedice i način na koji ih tim obrađuje.

**Tehnički rizici i dug**

| Rizik ili kompromis | Posljedica | Odgovor na rizik |
| --- | --- | --- |
| Istovremene rezervacije ograničenih količina hrane | Ako više kupaca istovremeno rezervira posljednje dostupne proizvode, sustav može prihvatiti više rezervacija nego što ima raspoložive hrane. | Predviđa se transakcijska obrada rezervacija i provjera raspoloživih količina prije potvrde. |
| Ponovno korištenje ili podudaranje kodova za preuzimanje | Ako kod nije jedinstven ili se može iskoristiti više puta, može doći do pogrešnog ili neovlaštenog potvrđivanja preuzimanja. | Osigurati jedinstvene kodove, provjeravati njihovu valjanost i onemogućiti ponovno korištenje nakon preuzimanja. |
| Neusklađenost statusa rezervacije i plaćanja | Rezervacija može biti pogrešno označena kao plaćena ili neplaćena, što može uzrokovati probleme pri preuzimanju proizvoda ili povratu novca. | Predviđa se provjera potvrde plaćanja putem vanjskog servisa i usklađivanje statusa rezervacije s evidentiranim uplatama. |
| Pogreške pri otkazivanju i povratu novca | Otkazivanje može ostaviti nepotrebno zauzetu količinu hrane ili dovesti do pogreške pri povratu već uplaćenog novca. | Uskladiti otkazivanje rezervacije, vraćanje raspoložive količine i povrat novca kada je primjenjivo te testirati neuspješne operacije. |
| Ovisnost o vanjskom servisu za autentifikaciju | Ako servis nije dostupan, korisnici se možda neće moći prijaviti ili registrirati. | Predvidjeti obradu nedostupnosti servisa i jasnu obavijest korisniku, bez označavanja neuspjele prijave kao uspješne. |
| Nedostupnost vanjskog servisa za plaćanje | Korisnik ne može dovršiti elektroničko plaćanje, a uplata članarine može biti odgođena. | Prikazati odgovarajuću poruku o pogrešci i omogućiti siguran ponovni pokušaj. Za rezervacije hrane moguće je koristiti i plaćanje gotovinom pri preuzimanju. |
| Nedostupnost kartografske usluge | Korisnici možda neće moći pregledavati lokacije prodajnih mjesta na karti i njihove udaljenosti. | Prikazati poruku o pogrešci i omogućiti pregled osnovnih podataka i adresa prodajnih mjesta bez karte. |
| Usporavanje pretraživanja s rastom broja ponuda | Veći broj dostupnih ponuda može usporiti pretraživanje i filtriranje hrane. | Optimizirati dohvat podataka i učestale filtre te prema potrebi uvesti straničenje rezultata. |

**Poslovni ili domenski rizici, ako su značajni**

| Rizik | Posljedica | Odgovor na rizik |
| --- | --- | --- |
| Istek roka ponude ili preuzimanja | Korisnik može pokušati rezervirati hranu koja više nije dostupna ili čije je vrijeme preuzimanja isteklo. | Skrivati neaktivne i rasprodane ponude te ponovno provjeriti njihovu dostupnost prilikom rezervacije. |
| Nepreuzimanje rezerviranih proizvoda | Prodajno mjesto može ostati s pripremljenom hranom koju kupac nije preuzeo, čime se povećava mogućnost bacanja hrane i gubitka prihoda. | Predvidjeti jasno istaknuto vrijeme preuzimanja, potvrdu rezervacije i slanje podsjetnika kada je izvedivo; pratiti status nepreuzetih rezervacija. |
| Neovlaštena objava ponuda ili pristup donacijama | Prodajno mjesto bez aktivnog članstva ili neodobrena humanitarna organizacija mogla bi pokušati koristiti zaštićene funkcionalnosti. | Provjeravati korisničku ulogu, aktivnost članarine i odobrenje profila pri izvršavanju odgovarajućih radnji. |


---


>**Završna provjera:** Usporedite dijagrame komponenata, podataka, razreda, sekvencijski dijagram i dijagram stanja s implementiranim kodom, ponašanjem aplikacije i stvarnim razmještajem. Ažurirajte zastarjele opise oblikovanja i objasnite svaku važnu razliku koja ostaje; poveznice na odgovarajući kod ili ispitivanja korisne su kada odgovaraju na konkretno pitanje.
