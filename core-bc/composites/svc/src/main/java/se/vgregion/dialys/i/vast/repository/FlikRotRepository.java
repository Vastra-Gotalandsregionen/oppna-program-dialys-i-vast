package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.vgregion.dialys.i.vast.jpa.requisitions.FlikRot;

/*import se.vgregion.dialys.i.vast.jpa.requisitions.Ansvarig;*/

/**
 * @author Claes Lundahl
 */
public interface FlikRotRepository extends JpaRepository<FlikRot, Integer> {

    /*@Override
    @Query("select distinct f from Flik f " +
            "left join fetch f.grupps gs " +
            "left join fetch gs.artikels " +
            "order by f.ordning")
    List<Flik> findAll();

    @Query("select distinct f from Flik f " +
            "left join fetch f.grupps gs " +
            "left join fetch gs.artikels " +
            "where f.typ = :typ and f.aktiv = :aktiv " +
            "order by f.ordning")
    List<Flik> findFliksByTypAndAktiv(@Param("typ") String typ, @Param("aktiv") Boolean aktiv);*/

    @Query("select distinct fr from FlikRot fr " +
            "left join fetch fr.fliks f " +
            "left join fetch f.grupps gs " +
            "left join fetch gs.artikels " +
            "where fr.id = 0 " +
            "order by f.ordning")
    FlikRot fetchDefault();

}