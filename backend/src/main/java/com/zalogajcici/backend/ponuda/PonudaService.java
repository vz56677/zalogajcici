package com.zalogajcici.backend.ponuda;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjesto;
import com.zalogajcici.backend.prodajnomjesto.ProdajnoMjestoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;

@Service
public class PonudaService {
    private final PonudaRepository ponude;
    private final ProdajnoMjestoRepository mjesta;
    private final KorisnikRepository korisnici;
    private final Clock clock;

    public PonudaService(PonudaRepository ponude, ProdajnoMjestoRepository mjesta, KorisnikRepository korisnici, Clock clock) {
        this.ponude = ponude;
        this.mjesta = mjesta;
        this.korisnici = korisnici;
        this.clock = clock;
    }


    @Transactional
    public PonudaOdgovor objavi(Long korisnikId, ObjaviPonuduZahtjev z) {
        Korisnik k = korisnici.findById(korisnikId).orElseThrow(() ->
                new BusinessException(HttpStatus.UNAUTHORIZED, "KORISNIK_NE_POSTOJI", "Korisnik ne postoji."));
        if (k.getUloga() != UlogaKorisnika.PRODAVAC) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "NIJE_PRODAVAC", "Samo prodajna mjesta mogu objavljivati ponude.");
        }
        if (z.prodajnoMjesto() == null) throw neispravno("PRODAJNO_MJESTO_OBAVEZNO", "Prodajno mjesto je obavezno.");
        ProdajnoMjesto mjesto = mjesta.findById(z.prodajnoMjesto().getId()).orElseThrow(() ->
                new BusinessException(HttpStatus.NOT_FOUND, "PRODAJNO_MJESTO_NE_POSTOJI", "Prodajno mjesto ne postoji."));
        if (!mjesto.getVlasnik().getId().equals(korisnikId)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "NIJE_VLASNIK", "Ponudu možete objaviti samo za svoje prodajno mjesto.");
        }
        OffsetDateTime sada = OffsetDateTime.now(clock);

        // TODO F-025: provjeriti aktivnu članarinu kad postoji entitet Clanarina (UC-007).
        // Ovdje baciti 403 CLANARINA_NEAKTIVNA.

        provjeri(z, sada);
        Ponuda p = new Ponuda(z.prodajnoMjesto(), z.vrsta(), z.namjena(), z.naziv(), z.kategorija(), z.opis(), z.fotografijaUrl(), z.redovnaCijena(), z.prodajnaCijena(), z.raspolozivo(), z.jedinicaMjere(), z.preuzimanjeOd(), z.preuzimanjeDo(), z.rokUpotrebe());
        return PonudaOdgovor.iz(ponude.save(p));
    }


    // ---------- zadane vrijednosti ----------

    private static NamjenaPonude namjena(ObjaviPonuduZahtjev z) {
        return z.namjena() != null ? z.namjena() : NamjenaPonude.PRODAJA;
    }

    private static String jedinica(ObjaviPonuduZahtjev z) {
        return (z.jedinicaMjere() == null || z.jedinicaMjere().isBlank()) ? "kom" : z.jedinicaMjere().trim();
    }

    // Za donaciju je prodajna cijena uvijek 0 (CHECK u bazi), bez obzira na to što je poslano
    private static BigDecimal prodajnaCijena(ObjaviPonuduZahtjev z) {
        return namjena(z) == NamjenaPonude.DONACIJA ? BigDecimal.ZERO : z.prodajnaCijena();
    }

    private static boolean prazno(String s) {
        return s == null || s.isBlank();
    }

    private static BusinessException neispravno(String kod, String poruka) {
        return new BusinessException(HttpStatus.BAD_REQUEST, kod, poruka);
    }

// ---------- validacija ----------------------------

    private void provjeri(ObjaviPonuduZahtjev z, OffsetDateTime sada) {
        // 1. Obavezna polja
        if (z.vrsta() == null)          throw neispravno("OBAVEZNO_POLJE", "Vrsta ponude je obavezna.");
        if (prazno(z.naziv()))          throw neispravno("OBAVEZNO_POLJE", "Naziv je obavezan.");
        if (prazno(z.kategorija()))     throw neispravno("OBAVEZNO_POLJE", "Kategorija je obavezna.");
        if (z.redovnaCijena() == null)  throw neispravno("OBAVEZNO_POLJE", "Redovna cijena je obavezna.");
        if (z.raspolozivo() == null)    throw neispravno("OBAVEZNO_POLJE", "Količina je obavezna.");
        if (z.preuzimanjeOd() == null)  throw neispravno("OBAVEZNO_POLJE", "Početak preuzimanja je obavezan.");
        if (z.preuzimanjeDo() == null)  throw neispravno("OBAVEZNO_POLJE", "Kraj preuzimanja je obavezan.");

        // 2. Količina
        if (z.raspolozivo().compareTo(BigDecimal.ZERO) <= 0) {
            throw neispravno("NEISPRAVNA_KOLICINA", "Količina mora biti veća od 0.");
        }

        // 3. Redovna cijena
        if (z.redovnaCijena().compareTo(BigDecimal.ZERO) < 0) {
            throw neispravno("NEISPRAVNA_CIJENA", "Redovna cijena ne smije biti negativna.");
        }

        // 4. Donacija samo za proizvod (F-012)
        NamjenaPonude namjena = namjena(z);
        if (namjena == NamjenaPonude.DONACIJA && z.vrsta() != VrstaPonude.PROIZVOD) {
            throw neispravno("DONACIJA_SAMO_PROIZVOD", "Za donaciju se može označiti samo proizvod, ne paket.");
        }

        // 5. Snižena cijena kod prodaje: postoji, nije negativna i manja je od redovne
        if (namjena == NamjenaPonude.PRODAJA) {
            BigDecimal snizena = z.prodajnaCijena();
            if (snizena == null
                    || snizena.compareTo(BigDecimal.ZERO) < 0
                    || snizena.compareTo(z.redovnaCijena()) >= 0) {
                throw neispravno("NEISPRAVNA_SNIZENA_CIJENA", "Snižena cijena mora biti manja od redovne.");
            }
        }

        // 6. Vrijeme preuzimanja
        if (!z.preuzimanjeOd().isBefore(z.preuzimanjeDo())) {
            throw neispravno("NEISPRAVNO_VRIJEME_PREUZIMANJA", "Početak preuzimanja mora biti prije kraja preuzimanja.");
        }
        if (!z.preuzimanjeDo().isAfter(sada)) {
            throw neispravno("PREUZIMANJE_U_PROSLOSTI", "Kraj preuzimanja mora biti u budućnosti.");
        }

        // 7. Rok upotrebe: obavezan za proizvod, ne smije isteći prije kraja preuzimanja (CHECK u bazi)
        if (z.vrsta() == VrstaPonude.PROIZVOD && z.rokUpotrebe() == null) {
            throw neispravno("ROK_UPOTREBE_OBAVEZAN", "Rok upotrebe je obavezan za proizvod.");
        }
        if (z.rokUpotrebe() != null && z.rokUpotrebe().isBefore(z.preuzimanjeDo())) {
            throw neispravno("ROK_UPOTREBE_PRERAN", "Rok upotrebe ne smije isteći prije kraja preuzimanja.");
        }

        // 8. Paket: samo cijeli komadi (CHECK u bazi)
        if (z.vrsta() == VrstaPonude.PAKET) {
            boolean cijeliBroj = z.raspolozivo().stripTrailingZeros().scale() <= 0;
            if (!"kom".equals(jedinica(z)) || !cijeliBroj) {
                throw neispravno("PAKET_CIJELI_KOMADI", "Paketi se objavljuju u cijelim komadima.");
            }
        }
    }
}
