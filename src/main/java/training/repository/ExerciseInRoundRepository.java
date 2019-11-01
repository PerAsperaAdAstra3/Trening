package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.ExerciseInRound;

@Repository
public interface ExerciseInRoundRepository extends JpaRepository<ExerciseInRound,Long> {
	
	@Query(
	  value = "select * from exercise_in_round er join round r on r.id = er.round_exercise_in_round join training tr on tr.id = r.training_round where er.exerciseid = :exerciseId and tr.training_list = :clientId ORDER BY tr.date DESC LIMIT 1;", 
	  nativeQuery = true)
	ExerciseInRound previousExerciseOfSameTypeForClient(@Param("clientId") String clientId, @Param("exerciseId") String exerciseId);
	
}
