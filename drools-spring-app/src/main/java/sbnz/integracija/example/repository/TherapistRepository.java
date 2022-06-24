package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Therapist;
@Repository
public interface TherapistRepository extends JpaRepository<Therapist, Long> {
}
