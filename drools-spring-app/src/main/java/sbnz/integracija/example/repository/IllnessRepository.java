package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Illness;

import java.util.List;

@Repository
public interface IllnessRepository extends JpaRepository<Illness, Long> {

    @Query("select d from Illness d left join fetch d.illnessSymptoms")
    List<Illness> findAllWithSymptomList();
}
