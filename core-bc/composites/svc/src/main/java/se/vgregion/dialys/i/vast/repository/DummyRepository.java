package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.Dummy;

/**
 * @author Patrik Björk
 */
public interface DummyRepository extends JpaRepository<Dummy, Integer> {

}
