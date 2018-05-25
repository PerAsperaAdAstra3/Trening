package trening.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import trening.model.Trening;

@Repository
public interface TreningRepository extends JpaRepository<Trening, Long>{

}
