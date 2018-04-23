package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/*import se.vgregion.dialys.i.vast.jpa.requisitions.Ansvarig;*/
import se.vgregion.dialys.i.vast.jpa.requisitions.Flik;

import java.util.List;

/**
 * @author Claes Lundahl
 */
public interface FlikRepository extends JpaRepository<Flik, Integer> {

    @Override
    @Query("select distinct f from Flik f " +
            "left join fetch f.grupps gs " +
            "left join fetch gs.artikels " +
            "order by f.ordning")
    List<Flik> findAll();

    @Query("select distinct f from Flik f " +
            "join fetch f.grupps gs " +
            "join fetch gs.artikels " +
            "where f.typ = :typ and f.aktiv = :aktiv " +
            "order by f.ordning")
    List<Flik> findFliksByTypAndAktiv(@Param("typ") String typ, @Param("aktiv") Boolean aktiv);

}