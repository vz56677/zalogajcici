package com.zalogajcici.backend.donacija;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ZahtjevDonacijeRepository extends JpaRepository<ZahtjevDonacije, Long> {
    List<ZahtjevDonacije> findByOrganizacija_IdOrderByPoslanUDesc(Long organizacijaId);
}