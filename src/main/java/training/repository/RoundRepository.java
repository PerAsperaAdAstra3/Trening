package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import training.model.Round;

public interface RoundRepository extends JpaRepository<Round, Long> {

}
