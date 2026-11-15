# 1. Opseg projekta

Cilj projekta je izraditi aplikaciju koja smanjuje bacanje hrane kojoj se bliži rok trajanja. Prehrambene ustanove se prijavljuju u aplikaciju i objavljuju proizvode s kratkim rokom trajanja po sniženoj cijeni. Postoje samostalni proizvodi i paketi iznenađenja čiji sadržaj nije poznat unaprijed. Pojedine proizvode ustanove mogu namijeniti isključivo donaciji humanitarnim organizacijama.

Kupci se prijavljuju kao privatne osobe i vide trenutno dostupne ponude. Mogu ih rezervirati i platiti te preuzeti do zadanog roka. Nakon plaćanja dobivaju potvrdu i kod koji prodavač potvrđuje pri preuzimanju. Humanitarne organizacije imaju zasebnu ulogu: pregledavaju ponude namijenjene donacijama i od ustanova traže određenu količinu. Administrator upravlja sustavom i odobrava profile humanitarnih organizacija.

Rasprodani ili istekli proizvodi automatski se uklanjaju iz aplikacije. Izvan opsega projekta su dostava i obrada plaćanja (prepušteno vanjskim servisima).

## 1.1 Problem i cilj projekta

Prehrambeni objekti bacaju još ispravnu hranu kojoj se bliži rok trajanja, a kupci i humanitarne organizacije nemaju jednostavan način da za nju saznaju na vrijeme. Zbog toga hrana propada, a objekti gube prihod.
Cilj projekta je izraditi web aplikaciju koja povezuje prehrambene objekte, kupce i humanitarne organizacije kako bi se hrana kojoj se bliži rok trajanja iskoristila umjesto bacanja. Aplikacija omogućuje trgovinama da objave proizvode i pakete iznenađenja po sniženoj cijeni ili ih namijene donaciji, kupcima da ih pronađu, rezerviraju, plate i preuzmu, a humanitarnim organizacijama da zatraže donirane proizvode.

## 1.2 Ciljani korisnici i dionici

**Cilj:** Utvrditi osobe, uloge i organizacije čije potrebe i interesi određuju projektni zadatak.

| Skupina korisnika ili dionika                              | Potreba ili interes relevantan za ovaj projekt                                                                                                                                |
| ---------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Kupci (privatne osobe)                                     | Pronaći i rezervirati hranu po sniženoj cijeni, platiti putem aplikacije i preuzeti je do zadanog roka.                                                                       |
| Prehrambene ustanove                                       | Prodati ili donirati hranu kojoj se bliži rok trajanja umjesto da je bace te jednostavno objavljivati i uređivati ponude.                                                     |
| Zaposlenici ustanova                                       | Brzo potvrditi preuzimanje rezervacije unosom ili skeniranjem koda.                                                                                                           |
| Humanitarne organizacije                                   | Vidjeti ponude namijenjene donacijama i zatražiti potrebnu količinu hrane.                                                                                                    |
| Administrator                                              | Odobravati profile humanitarnih organizacija te upravljati korisnicima i sadržajem sustava.                                                                                   |
| Ocjenjivači projekta                                       | Dobiti dokumentiran projekt koji ispunjava odobreni zadatak.                                                                                                                  |
| Regulatorno tijelo / Agencija za zaštitu podataka (Dionik) | Zahtijeva obradu i zaštitu osobnih podataka korisnika u skladu s GDPR-om. Ovaj dionik ne koristi aplikaciju, ali definira ograničenja sustava. |

## 1.3 Granice projekta

**Uključeno:**
Korisnička strana:
Izrada korisničkog računa i prijava putem vanjskog pružatelja autentifikacije (Google, OAuth 2.0), bez pohrane lozinki u sustavu (NF-03), te moguća promjena osobnih podataka korisniku. Kupac može pregledavati sve aktivne ponude prodajnih mjesta i humanitarne akcije. Kupac može filtrirati ponude prema kategoriji, udaljenosti, cijeni i vremenu preuzimanja (F-027). Kupac može rezervirati i platiti aktivnu ponudu. Kupac može otkazati rezervaciju i dobiti povrat novca najkasnije jedan sat prije isteka roka preuzimanja (F-010).

Prodavačka strana:
Prodavač/trgovina može napraviti javni račun, te mora predati barem ključne podatke poput naziva tvrtke, OIB, logo, lokaciju. Prodavač može napraviti novu ponudu upisujući opis artikala, dodavanjem slike artikala i određivanjem roka za preuzimanje sadržaja. Prodavač može urediti ili obrisati ponudu koja nije rezervirana (F-024). Prodavač može ponudu namijeniti isključivo donaciji humanitarnim organizacijama (F-012), a humanitarna organizacija takve ponude pregledava i šalje zahtjev za preuzimanje određene količine (F-013).

Ostalo:
Potrebno je realizirati relacijsku bazu podataka, ugraditi i osposobiti vanjske sustave. Potrebno je napraviti aplikaciju koja se može prilagoditi uređaju tj. skalirati s obzirom na ekran (mobitel / stolno računalo). 

**Izvan opsega ili odgođeno:**
U opsegu projekta nije omogućavanje plaćanja gotovinom, već će svaka transakcija od korisnika prema prodavaču biti isključivo uz pomoć kartičnog plaćanja ili usluge PayPal. Također nije uključeno naknadno održavanje i daljnji razvoj aplikacije zbog prirode projekta.

**Vanjske ovisnosti:**
Prema vanjskim ovisnostima, realizacija projekta ovisi o sustavima: OAuth 2.0 pružatelj Google (identifikacija korisnika), PayPal (plaćanje rezervacija i članarina karticom ili PayPal računom), Google Maps (prikaz lokacije i karta) i SendGrid (slanje e-pošte korisnicima).

## 1.4 Postojeći pristupi i opravdanost projekta


Građanin koji želi kupiti višak hrane po sniženoj cijeni može se o dostupnosti i uvjetima preuzimanja raspitivati izravno u pojedinim prodajnim mjestima, dok humanitarna organizacija može zasebno kontaktirati ta mjesta radi dogovora o donaciji. Takav pristup omogućuje izravan dogovor, ali može zahtijevati više pojedinačnih kontakata kako bi se pronašla odgovarajuća hrana i uskladilo preuzimanje. SpasiZalogaj objedinjuje pregled dostupnih ponuda, rezervacije i zahtjeve za donacije na jednom mjestu, čime olakšava povezivanje prodajnih mjesta s kupcima i humanitarnim organizacijama te organizaciju preuzimanja viška hrane.
