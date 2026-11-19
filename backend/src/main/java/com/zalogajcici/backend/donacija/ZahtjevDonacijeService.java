
package com.zalogajcici.backend.donacija;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.korisnik.Korisnik;
import com.zalogajcici.backend.korisnik.UlogaKorisnika;
import com.zalogajcici.backend.ponuda.Ponuda;
import com.zalogajcici.backend.ponuda.NamjenaPonude;
import com.zalogajcici.backend.korisnik.KorisnikRepository;
import com.zalogajcici.backend.ponuda.PonudaRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ZahtjevDonacijeService {

    private final ZahtjevDonacijeRepository zahtjevi;
    private final PonudaRepository ponude;
    private final KorisnikRepository korisnici;
    private final Clock clock;

    public ZahtjevDonacijeService(ZahtjevDonacijeRepository zahtjevi, PonudaRepository ponude, KorisnikRepository korisnici, Clock clock) {
        this.zahtjevi = zahtjevi;
        this.ponude = ponude;
        this.korisnici = korisnici;
        this.clock = clock;
    }


    @Transactional
    public ZahtjevDonacijeOdgovor posalji(Long organizacijaId, PosaljiZahtjevDonacijeZahtjev unos) {

        Korisnik organizacija = korisnici.findById(organizacijaId)
            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "KORISNIK_NE_POSTOJI", "Korisnik ne postoji."));

        if (organizacija.getUloga() != UlogaKorisnika.ORGANIZACIJA || !organizacija.isAktivan()) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "NEDOZVOLJEN_PRISTUP", "Nemate ovlasti za slanje zahtjeva.");
        }

        if (unos == null || unos.ponudaId() == null || unos.kolicina() == null || unos.kolicina().signum() <= 0 || unos.kolicina().scale() > 2) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "NEISPRAVAN_ZAHTJEV", "Unesite ispravnu ponudu i pozitivnu količinu s najviše dvije decimale.");
        }

        OffsetDateTime sada = OffsetDateTime.now(clock);

        Ponuda ponuda = ponude.findAktivnaById(sada, unos.ponudaId())
            .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "PONUDA_NE_POSTOJI", "Ponuda ne postoji ili je istekla."));

        if (ponuda.getNamjena() != NamjenaPonude.DONACIJA) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "NIJE_DONACIJA", "Ponuda nije namijenjena donaciji.");
        }

        if (unos.kolicina().compareTo(ponuda.getRaspolozivo()) > 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "NEDOVOLJNA_KOLICINA", "Tražena količina veća je od raspoložive.");
        }

        ZahtjevDonacije zahtjev = new ZahtjevDonacije(organizacija, ponuda, unos.kolicina(), sada);
        return ZahtjevDonacijeOdgovor.iz(zahtjevi.save(zahtjev));
    }

    @Transactional(readOnly = true)
    public List<ZahtjevDonacijeOdgovor> moji(Long organizacijaId) {
    return zahtjevi.findByOrganizacija_IdOrderByPoslanUDesc(organizacijaId).stream().map(ZahtjevDonacijeOdgovor::iz).toList();
    }
}
