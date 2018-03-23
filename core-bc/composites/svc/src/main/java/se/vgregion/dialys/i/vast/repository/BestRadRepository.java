package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;
import se.vgregion.dialys.i.vast.vymodel.BestArtikelRad;

import java.util.List;

public interface BestRadRepository extends JpaRepository<BestPDRad, Integer> {


    @Query("select new se.vgregion.dialys.i.vast.vymodel.BestArtikelRad(brad.antal, art.artNr, art.storlek, art.namn)" +
            "from BestPDRad brad " +
                    "join brad.pdArtikel part " +
                    "join part.artikel art " +
                    "where brad.bestID = :bestId ")
    List<BestArtikelRad> getBestArtikelRads(@Param("bestId") Integer bestId);
}


