package com.zalogajcici.backend.ponuda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface PonudaRepository extends JpaRepository<Ponuda, Long> {

    @Query("""
        select p from Ponuda p
        where p.aktivna = true
                and p.preuzimanjeDo > :sada
                and p.raspolozivo > 0
        """)
    List<Ponuda> findAktivne(@Param("sada") OffsetDateTime sada);
}
