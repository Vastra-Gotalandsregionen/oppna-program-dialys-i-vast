package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel;

import java.util.List;

public interface ArtikelRepository extends JpaRepository<Artikel, Integer> {


    /*@Query("select art from Artikel art"+
    " join art.pdArtikels pda" + " where :pdid = pda.pdid" + " and art.namn NOT like '%UTGÅTT%'")
    List<Artikel> getArtikelsForRekvisition(@Param("pdid") Integer pdid);*/

    @Query("select pdart from PDArtikel pdart where :pdid = pdart.pdid and pdart.artikel.aktiv = true")
    List<PDArtikel> getPdArtikelsForRekvis(@Param("pdid") Integer pdid);
}
