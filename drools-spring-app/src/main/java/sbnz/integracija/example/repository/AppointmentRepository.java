package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Appointment;
import sbnz.integracija.example.model.Patient;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select d from Appointment d left join fetch d.diagnosis i where d.therapist.username =?1")
    List<Appointment> findByTherapistUsername(String username);

    @Query("select d from Appointment d left join fetch d.diagnosis p where d.therapist.username =?1 and" +
            "(( lower(p.patient.username) like lower(concat('%', ?2,'%'))) or " +
            "( lower(p.patient.firstName) like lower(concat('%', ?2,'%'))) or " +
            "( lower(p.patient.lastName) like lower(concat('%', ?2,'%'))))")
    List<Appointment> findByTherapistUsernameAndPatient(String username, String searchTerm);

    @Query("select d from Appointment d left join fetch d.diagnosis i where d.id =?1")
    Appointment findById(long id);
}
