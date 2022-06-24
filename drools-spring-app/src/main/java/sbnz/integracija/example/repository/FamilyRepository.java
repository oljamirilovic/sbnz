package sbnz.integracija.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sbnz.integracija.example.model.Family;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query("select d from Family d where d.child =?1")
    List<Family> findAllByChildUsername(String child);

    @Query("select d from Family d where d.parent =?1")
    List<Family> findAllByParentUsername(String parent);
}
