package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;

import java.util.List;

/**
 * @author Claes Lundahl
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u " +
            "left join fetch u.ansvariga " +
            //"left join fetch u.usersRoles ur " +
            // "left join fetch ur.role r " +
            "order by u.userName")
    List<User> findAllByOrderByUserName();

    @Query("select u from User u " +
            "left join fetch u.ansvariga " +
            //"left join fetch u.usersRoles ur " +
            // "left join fetch ur.role r " +
            "where u.userName = :userName")
    User findOne(@Param("userName") String userName);

}