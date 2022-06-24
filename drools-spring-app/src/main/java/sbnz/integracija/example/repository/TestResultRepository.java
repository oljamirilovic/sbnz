package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
