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

    @Query("select user from Patient user left join fetch user.medicalHistory m where user.id =?1")
    Patient getPatientWithMedicalHistoryById(long id);

    @Query("select user from Patient user where user.id =?1")
    Optional<Patient> findById(long username);

    /*@Query("select user from Patient user left join fetch user.medicalHistory m")
    List<Patient> getAllPatientsWithMedicalHistory();*/
}
