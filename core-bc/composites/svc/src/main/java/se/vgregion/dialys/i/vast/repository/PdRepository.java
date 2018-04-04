package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.vgregion.dialys.i.vast.jpa.requisitions.Ansvarig;
import se.vgregion.dialys.i.vast.jpa.requisitions.Pd;

import java.util.List;

/**
 * @author Claes Lundahl
 */
public interface PdRepository extends JpaRepository<Pd, Integer> {



}