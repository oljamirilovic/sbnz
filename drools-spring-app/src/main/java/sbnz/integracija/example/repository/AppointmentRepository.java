package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Appointment;
import sbnz.integracija.example.model.Diagnosis;
import sbnz.integracija.example.model.Symptom;

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

    @Query("select d from Appointment d left join fetch d.diagnosis i where i.patient.id =?1 and d.resolved = true")
    List<Appointment> findByPatientIdAndResolved(long id);

    @Query("select d.diagnosis from Appointment d left join fetch d.diagnosis.jointMotionRangeList i where d.id =?1")
    Diagnosis findDiagnosisByAppointmentIdWithJmrs(long id);

    @Query("select d.diagnosis from Appointment d left join fetch d.diagnosis.illness i where d.diagnosis.patient.id =?1 and d.resolved = true")
    List<Diagnosis> findDiagnosisByPatientIdAndResolved(long id);

    @Query("select d from Appointment d left join fetch d.diagnosis i where i.id =?1")
    Appointment findByDiagnosisId(long id);

    @Query("select d from Appointment d left join fetch d.appointmentSymptoms")
    List<Appointment> findAllWithSymptoms();

    @Query("select d from Appointment d left join fetch d.appointmentSymptoms i where d.id =?1")
    Appointment findByIdWithSymptoms(long id);
}
