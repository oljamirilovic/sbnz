package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Diagnosis;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    @Query("select d from Diagnosis d left join fetch d.illness i where d.patient.username =?1")
    List<Diagnosis> findByUsername(String username);
}
