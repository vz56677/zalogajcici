package com.zalogajcici.backend.zaliha;

import com.zalogajcici.backend.common.BusinessException;
import com.zalogajcici.backend.ponuda.PonudaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.OffsetDateTime;

@Service
@Transactional(propagation = Propagation.MANDATORY) //metoda se poziva samo unutar postojece
                                                    // transakcije kreiranja rezervacije
public class ZalihaServiceImpl implements ZalihaService {

    private final PonudaRepository ponude;
    private final Clock clock;

    public ZalihaServiceImpl(PonudaRepository ponude, Clock clock) {
        this.ponude = ponude;
        this.clock = clock;
    }

    private void provjeriKolicinu(BigDecimal kolicina) {
        if (kolicina == null || kolicina.compareTo(BigDecimal.ZERO) <= 0){
            throw new BusinessException(HttpStatus.BAD_REQUEST, "NEISPRAVNA_KOLICINA", "Kolicina mora biti veca od 0.");
        }
    }

    @Override
    public void rezerviraj(Long ponudaId, BigDecimal kolicina){
        provjeriKolicinu(kolicina);
        OffsetDateTime sada = OffsetDateTime.now(clock);

        if (ponude.smanjiRaspolozivo(ponudaId, kolicina, sada) == 1){
            return; //uspjelo
        }
        //nije uspjelo -> ili ponuda ne postoji ili nema na zalihama
        if (!ponude.existsById(ponudaId)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "PONUDA_NE_POSTOJI", "Ponuda ne postoji.");
        }
        //nedovoljno na zalihama
        BigDecimal dostupno = ponude.dostupnaKolicina(ponudaId, sada).orElse(BigDecimal.ZERO);
        throw new NedovoljnaZalihaException(dostupno);
    }


    @Override
    public void vrati(Long ponudaId, BigDecimal kolicina) {
        provjeriKolicinu(kolicina);

        if (ponude.vratiRaspolozivo(ponudaId, kolicina) == 1){
            return; //uspjelo
        }
        //nije uspjelo jer ponuda ne postoji
        throw new BusinessException(HttpStatus.NOT_FOUND, "PONUDA_NE_POSTOJI", "Ponuda ne postoji.");

    }


}
