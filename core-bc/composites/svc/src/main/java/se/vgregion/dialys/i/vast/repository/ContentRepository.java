package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.Content;

/**
 * @author Patrik Björk
 */
public interface ContentRepository extends JpaRepository<Content, String> {

}