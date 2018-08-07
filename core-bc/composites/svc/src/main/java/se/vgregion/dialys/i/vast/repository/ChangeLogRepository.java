package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.ChangeLog;
import se.vgregion.dialys.i.vast.jpa.SearchLog;

/**
 * @author clalu4
 */
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Integer> {

}
