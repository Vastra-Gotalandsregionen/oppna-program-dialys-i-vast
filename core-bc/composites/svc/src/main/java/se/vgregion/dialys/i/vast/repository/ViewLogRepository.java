package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.SearchLog;
import se.vgregion.dialys.i.vast.jpa.ViewLog;

/**
 * @author Claes Lundahl
 */
public interface ViewLogRepository extends JpaRepository<ViewLog, Integer> {

}
