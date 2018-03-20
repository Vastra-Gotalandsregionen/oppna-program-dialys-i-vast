package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.BestPDRad;
import se.vgregion.dialys.i.vast.vymodel.BestArtikelRad;

import java.util.List;

public interface BestRadRepository extends JpaRepository<BestPDRad, Integer> {

    /*@Query("select brad from BestPDRad brad " +
            "join fetch brad.pdArtikel part " +
            "join fetch part.artikel art " +
            "where brad.bestID = :bestId ")
    List<BestPDRad> getRows(@Param("bestId") Integer bestId);*/

    /*@Query("select new se.vgregion.dialys.i.vast.vymodel.BestArtikelRad(brad, art)" +
            *//*"brad.antal, " +
            "art.artNr, " +
            "art.storlek, " +
            "art.namn" +
            ") " +*//*
            "from BestPDRad brad " +
            "join fetch brad.pdArtikel part " +
            "join fetch part.artikel art " +
            "where brad.bestID = :bestId ")
    List<BestArtikelRad> getBestArtikelRads(@Param("bestId") Integer bestId);*/


    @Query("select brad " +
            "from BestPDRad brad " +
            "join fetch brad.pdArtikel part " +
            "join fetch part.artikel art " +
            "where brad.bestID = :bestId ")
    List<BestPDRad> findBestPDRad(@Param("bestId") Integer bestId);



    // int antal, int art_nr, String mangd, String artikel
    /*@Query("select new BestArtikelRad(bar.antal, bar.id,bar.id,bar.id )" +
            "from BestPDRad bar where bar.id = bestId "
            )
    List<BestArtikelRad> bestArtikelrRader(Integer bestId);*/
}
