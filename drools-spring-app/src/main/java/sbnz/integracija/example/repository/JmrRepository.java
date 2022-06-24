package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.JointMotionRange;

import java.util.List;

@Repository
public interface JmrRepository extends JpaRepository<JointMotionRange, Long> {

    @Query("select d from JointMotionRange d where d.diagnosis.id =?1")
    List<JointMotionRange> findAllByDiagnosisId(long id);
}
