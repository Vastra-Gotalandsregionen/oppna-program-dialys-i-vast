package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.Patient;

import java.util.List;

/**
 * @author Patrik Björk
 */
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Override
    @Query("select p from Patient p " +
            "left join fetch p.ansvarig a " +
            "left join fetch p.pds ps " +
            "left join fetch ps.bestInfos bi " +
            "left join fetch bi.bestPDRads brad " +
            "left join fetch brad.pdArtikel part " +
            "left join fetch part.artikel art " +
            "where :id = p.id ")
    Patient findOne(@Param("id") Integer id);

}