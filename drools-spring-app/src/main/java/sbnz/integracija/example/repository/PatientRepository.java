package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select m from Patient m where (( lower(m.username) like lower(concat('%', :searchTerm,'%'))) or " +
            "( lower(m.firstName) like lower(concat('%', :searchTerm,'%'))) or " +
            "( lower(m.lastName) like lower(concat('%', :searchTerm,'%'))))")
    List<Patient> findAllBySearchTerm(String searchTerm);

    @Query("select user from Patient user where user.username =?1")
    Optional<Patient> findByUsername(String username);
}
