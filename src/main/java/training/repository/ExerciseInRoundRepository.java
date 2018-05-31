package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import training.model.ExerciseInRound;

public interface ExerciseInRoundRepository extends JpaRepository<ExerciseInRound,Long> {

}
