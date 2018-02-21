package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.vgregion.dialys.i.vast.jpa.requisitions.Role;
import se.vgregion.dialys.i.vast.jpa.requisitions.User;

import java.util.List;

/**
 * @author Claes Lundahl
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}