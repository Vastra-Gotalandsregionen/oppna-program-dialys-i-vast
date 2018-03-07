package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.vgregion.dialys.i.vast.jpa.requisitions.Mottagning;

import java.util.List;

/**
 * @author Claes Lundahl
 */
public interface MottagningRepository extends JpaRepository<Mottagning, Integer> {

/*    @Query("select u from User u " +
            "left join fetch u.ansvariga " +
            "left join fetch u.usersRoles ur " +
            "left join fetch ur.role r " +
            "order by u.userName")
    List<User> findAllByOrderByUserName();

    @Query("select u from User u " +
            "left join fetch u.ansvariga " +
            "left join fetch u.usersRoles ur " +
            "left join fetch ur.role r " +
            "where u.userName = :userName")
    User findOne(@Param("userName") String userName);
    */

    @Override
    @Query("select m from Mottagning m order by m.namn")
    List<Mottagning> findAll();

}