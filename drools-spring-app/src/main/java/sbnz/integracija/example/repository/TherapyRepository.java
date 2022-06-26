package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Therapy;

import java.util.List;

@Repository
public interface TherapyRepository extends JpaRepository<Therapy, Long> {

    @Query("select d from Therapy d where d.diagnosis.id =?1")
    List<Therapy> findAllByDiagnosisId(long id);

}
