package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.requisitions.Grupp;

/*import se.vgregion.dialys.i.vast.jpa.requisitions.Ansvarig;*/

/**
 * @author Claes Lundahl
 */
public interface GruppRepository extends JpaRepository<Grupp, Integer> {


}