package com.zalogajcici.backend.ponuda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

    //join fetch rjesava problem: (lazy + open-in-view=false)
    @Query("""
        select p from Ponuda p join fetch p.prodajnoMjesto
        where p.aktivna = true
            and p.preuzimanjeDo > :sada
            and p.raspolozivo > 0
            and p.namjena = :namjena
        order by p.preuzimanjeDo
        """)
    List<Ponuda> findAktivne(@Param("sada") OffsetDateTime sada,
                             @Param("namjena") NamjenaPonude namjena);


    //sigurno kod istodobnih rezervacija: provjera i oduzimanje događaju se u istoj SQL naredbi
    @Modifying(flushAutomatically = true) //mijenjaju se podaci
    @Query("""
        update Ponuda p
        set p.raspolozivo = p.raspolozivo - :kolicina
        where p.id = :id
            and p.raspolozivo >= :kolicina
            and p.aktivna = true
            and p.preuzimanjeDo > :sada
        """)
    int smanjiRaspolozivo(@Param("id") Long id,
                          @Param("kolicina") BigDecimal kolicina,
                          @Param("sada") OffsetDateTime sada);


    //otkazana rezervacija
    @Modifying(flushAutomatically = true) //mijenjaju se podaci
    @Query("""
        update Ponuda p
        set p.raspolozivo = p.raspolozivo + :kolicina
        where p.id = :id
        """)
    int vratiRaspolozivo(@Param("id") Long id,
                         @Param("kolicina") BigDecimal kolicina);


    //za prikaz kupcu
    @Query("""
        select p.raspolozivo
        from Ponuda p
        where p.id = :id
            and p.aktivna = true
            and p.preuzimanjeDo > :sada
        """)
    Optional<BigDecimal> dostupnaKolicina(@Param("id") Long id,
                                          @Param("sada") OffsetDateTime sada);
}
