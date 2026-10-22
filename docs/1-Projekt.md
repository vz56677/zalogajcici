# 1. Opseg projekta

Cilj projekta je izraditi aplikaciju koja smanjuje bacanje hrane kojoj se bliži rok trajanja. Prehrambene ustanove se prijavljuju u aplikaciju i objavljuju proizvode s kratkim rokom trajanja po sniženoj cijeni. Postoje samostalni proizvodi i paketi iznenađenja čiji sadržaj nije poznat unaprijed. Pojedine proizvode ustanove mogu namijeniti isključivo donaciji humanitarnim organizacijama.

Kupci se prijavljuju kao privatne osobe i vide trenutno dostupne ponude. Mogu ih rezervirati i platiti te preuzeti do zadanog roka. Nakon plaćanja dobivaju potvrdu i kod koji prodavač potvrđuje pri preuzimanju. Humanitarne organizacije imaju zasebnu ulogu: pregledavaju ponude namijenjene donacijama i od ustanova traže određenu količinu. Administrator upravlja sustavom i odobrava profile humanitarnih organizacija i ustanova.

Rasprodani ili istekli proizvodi automatski se uklanjaju iz aplikacije. Izvan opsega projekta su dostava i obrada plaćanja (prepušteno vanjskim servisima).


## 1.1 Problem i cilj projekta
Prehrambeni objekti bacaju još ispravnu hranu kojoj se bliži rok trajanja, a kupci i humanitarne organizacije nemaju jednostavan način da za nju saznaju na vrijeme. Zbog toga hrana propada, a objekti gube prihod.
Cilj projekta je izraditi web aplikaciju koja povezuje prehrambene objekte, kupce i humanitarne organizacije kako bi se hrana kojoj se bliži rok trajanja iskoristila umjesto bacanja. Aplikacija omogućuje trgovinama da objave proizvode i pakete iznenađenja po sniženoj cijeni ili ih namijene donaciji, kupcima da ih pronađu, rezerviraju, plate i preuzmu, a humanitarnim organizacijama da zatraže donirane proizvode.


## 1.2 Ciljani korisnici i dionici

**Cilj:** Utvrditi osobe, uloge i organizacije čije potrebe i interesi određuju projektni zadatak.

| Skupina korisnika ili dionika | Potreba ili interes relevantan za ovaj projekt |
| --- | --- |
|Kupci (privatne osobe) | Pronaći i rezervirati hranu po sniženoj cijeni, platiti putem aplikacije i preuzeti je do zadanog roka.|
| Prehrambene ustanove| Prodati ili donirati hranu kojoj se bliži rok trajanja umjesto da je bace te jednostavno objavljivati i uređivati ponude.|
|Zaposlenici ustanova|Brzo potvrditi preuzimanje rezervacije unosom ili skeniranjem koda.|
|Humanitarne organizacije|	Vidjeti ponude namijenjene donacijama i zatražiti potrebnu količinu hrane.|
|Administrator	|Odobravati profile humanitarnih organizacija i ustanova te upravljati korisnicima i sadržajem sustava.|
|Ocjenjivači projekta|	Dobiti dokumentiran projekt koji ispunjava odobreni zadatak.|
| Regulatorno tijelo / Agencija za zaštitu podataka (Dionik) | Zahtijeva osiguravanje anonimnosti prijavitelja i zaštitu osobnih podataka (npr. usklađenost s GDPR-om). Ovaj dionik ne koristi aplikaciju, ali definira ograničenja sustava. |

## 1.3 Granice projekta

**Cilj:** Jasno definirati granice sustava — što je obuhvaćeno odobrenim zadatkom, što je svjesno izostavljeno ili odgođeno te o kojim vanjskim uslugama sustav izravno ovisi.

**Uključeno:** [Glavne mogućnosti.]  
**Izvan opsega ili odgođeno:** [Navedite funkcionalnosti koje su namjerno izostavljene ili prebačene u buduće verzije, uz kratko inženjersko ili vremensko obrazloženje.] 
**Vanjske ovisnosti:** [Navedite nužne vanjske usluge, API-je, baze podataka ili izvore informacija bez kojih sustav ne može funkcionirati (npr. Google Maps, platni pristupnici, vanjski pružatelj identiteta (OAuth)).]

<details>
<summary><strong>Primjer početne granice projekta</strong></summary>

**Primjer — CrisisMap:**  
**Uključeno:** Podnošenje prijave kriznog događaja s lokacijom i pregled prijava na karti.  
**Isključeno:** Automatsko angažiranje hitnih službi; prikaz prijave ne odobrava niti pokreće službenu intervenciju.  
**Vanjska ovisnost:** Pružatelj kartografskih usluga omogućuje prikaz karte; aplikacija ostaje odgovorna za vlastite podatke o prijavama.

Ovo je konkretna granica jer razdvaja prikaz informacija od službenog odgovora hitnih službi i određuje koji sustav osigurava kartu. Izuzeće i ovisnost dio su **nastavnog scenarija**, a ne tvrdnja o tome što objavljena aplikacija stvarno implementira; te činjenice određuju odobreni opseg i stvarna aplikacija tima.

**Drugi primjer korisne granice:** U prvoj implementaciji usredotočite se na koordinaciju odgovora na poplavu. Navedite vrste događaja ili korisnika koje pokriva odobreni projekt umjesto obećavanja svih mogućih kriznih procesa. Predviđanje pomoću umjetne inteligencije ili obuku u virtualnoj stvarnosti navedite kao budući rad samo ako predstavljaju smislen sljedeći korak, a ne kao pretpostavljene funkcionalnosti prve inačice.

Tvrdnja poput „modularna arhitektura omogućuje brzo postavljanje u novim regijama” zahtijeva stvarno projektno uporište. Ovdje nemojte obećavati prilagodljivost samo zato što zvuči kao poželjna korist projekta.

Ovaj pregled zadržite na razini opsega: navedite glavnu mogućnost i njezinu granicu bez popisivanja svakog zahtjeva ili projektne pojedinosti. Ono što je stvarno dovršeno i ono što je ostalo nedovršeno navedite u završnim rezultatima, a ne u ovom početnom opisu.

</details>

## 1.4 Postojeći pristupi i opravdanost projekta

**Cilj:** Objasniti kako korisnici trenutačno rješavaju problem i koja preostala potreba opravdava odobreni projekt.

[U dvije do četiri rečenice opišite jedan postojeći postupak ili relevantno rješenje, što ono već omogućuje i koja konkretna potreba motivira ovaj projekt. Činjenične tvrdnje o vanjskim proizvodima ili praksama potkrijepite izvorom.]

<details>
<summary><strong>Postojeći pristupi i opravdanost projekta</strong></summary>

**Primjer — CrisisMap:** „Nakon teškog vremenskog događaja građanin će možda morati provjeriti izvor meteoroloških upozorenja i zaseban izvor lokalnih prijava. Predloženi prikaz na karti u CrisisMapu objedinjuje prijave kriznih događaja relevantne za odabranu lokaciju. Aplikacija ne zamjenjuje službena upozorenja ni hitne službe.” Time se objašnjava potreba i namjeravana granica projekta bez tvrdnje da su svi postojeći kanali neučinkoviti. Prva je rečenica **primjer korisničkog scenarija**, a ne izmjeren nalaz o stvarnim korisnicima. Ako tim navodi konkretnu službu upozoravanja ili konkurentski proizvod, treba navesti izvor. Pregled konkurencije i snimke zaslona nisu obavezni.

Ako je postojeće rješenje stvarno utjecalo na projekt, usporedite samo relevantnu funkcionalnost, primjerice jasnoću karte ili potrebnu integraciju. Izbjegavajte unaprijed zadani broj proizvoda ili tablicu kriterija koji nisu povezani s odobrenim zadatkom.

</details>

---

>**Završna provjera:** Opis problema, cilj projekta, korisnici i dionici, granice opsega te postojeći pristupi međusobno su usklađeni. Navedene koristi ne predstavljaju neprovjerene tvrdnje, a uključene i izostavljene funkcionalnosti odgovaraju odobrenom projektnom zadatku.