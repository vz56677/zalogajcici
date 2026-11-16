package com.zalogajcici.backend.prodajnomjesto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdajnoMjestoRepository extends JpaRepository<ProdajnoMjesto, Long> {

    Optional<ProdajnoMjesto> findFirstByVlasnikId(Long vlasnikId);
}
