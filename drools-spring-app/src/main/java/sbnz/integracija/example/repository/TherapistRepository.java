package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Patient;
import sbnz.integracija.example.model.Therapist;

import java.util.Optional;

@Repository
public interface TherapistRepository extends JpaRepository<Therapist, Long> {

    @Query("select user from Therapist user where user.username =?1")
    Optional<Therapist> findByUsername(String username);

}
