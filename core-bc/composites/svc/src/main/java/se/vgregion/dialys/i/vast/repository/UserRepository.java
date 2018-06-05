package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Claes Lundahl
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u order by u.userName")
    List<User> findAllByOrderByUserName();

    @Query("select u from User u where u.userName = :userName")
    User findOne(@Param("userName") String userName);

    @Query("select u from User u where u.userName like :userNameFilter order by u.userName")
    List<User> findAllByUserName(@Param("userNameFilter") String userNameFilter);

}