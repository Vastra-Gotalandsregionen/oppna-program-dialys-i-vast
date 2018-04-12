package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestInfo;
import se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel;

public interface PDArtikelRepository extends JpaRepository<PDArtikel, Integer> {

}


