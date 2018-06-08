package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.SearchLog;

/**
 * @author Patrik Björk
 */
public interface SearchLogRepository extends JpaRepository<SearchLog, Integer> {

}
