package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

}
