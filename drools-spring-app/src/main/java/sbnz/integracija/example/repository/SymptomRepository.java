package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Appointment;
import sbnz.integracija.example.model.Symptom;

import java.util.List;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    @Query("select d from Symptom d where" +
            "( lower(d.name) like lower(concat('%', ?1,'%')))")
    List<Symptom> findAllByName(String searchTerm);
}
