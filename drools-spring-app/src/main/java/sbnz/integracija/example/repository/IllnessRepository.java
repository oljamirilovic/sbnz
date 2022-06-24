package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Illness;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, Long> {
}
