package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.Link;

import java.util.List;

/**
 * @author Patrik Björk
 */
public interface LinkRepository extends JpaRepository<Link, Integer> {

    List<Link> findAllByOrderById();

}