package se.vgregion.dialys.i.vast.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import se.vgregion.dialys.i.vast.jpa.requisitions.Patient;

import java.util.List;

/**
 * @author Patrik Björk
 */
public interface PatientRepository extends JpaRepository<Patient, String> {

    //List<Patient> search(String constraints, Pageable pageable);

}