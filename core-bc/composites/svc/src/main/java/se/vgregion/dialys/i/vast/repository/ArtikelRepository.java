package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.Artikel;
import se.vgregion.dialys.i.vast.jpa.requisitions.PDArtikel;

import java.util.List;

public interface ArtikelRepository extends JpaRepository<Artikel, Integer> {

    @Query("select pdart from PDArtikel pdart where :pdid = pdart.pdid and pdart.artikel.aktiv = true")
    List<PDArtikel> getPdArtikelsForRekvis(@Param("pdid") Integer pdid);

}
