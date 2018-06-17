package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ExerciseInRound;

@Repository
public interface ExerciseInRoundRepository extends JpaRepository<ExerciseInRound,Long> {

}
