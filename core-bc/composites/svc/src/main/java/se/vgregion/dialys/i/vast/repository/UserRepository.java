package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.vgregion.dialys.i.vast.jpa.User;

import java.util.List;

/**
 * @author Patrik Björk
 */
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByOrderById();

}