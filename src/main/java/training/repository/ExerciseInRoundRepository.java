package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.ExerciseInRound;
import training.model.Training;

@Repository
public interface ExerciseInRoundRepository extends JpaRepository<ExerciseInRound,Long> {
	
	@Query(
	  value = "select * from exercise_in_round er join round r on r.id = er.round_exercise_in_round join training tr on tr.id = r.training_round where er.exerciseid = :exerciseId and tr.training_list = :clientId ORDER BY tr.date DESC LIMIT 1;", 
	  nativeQuery = true)
	ExerciseInRound previousExerciseOfSameTypeForClient(@Param("clientId") String clientId, @Param("exerciseId") String exerciseId);
	
	@Query(
			  value = "select * from exercise_in_round where round_exercise_in_round in (select id from round where training_round in :topTrainingsIds)",
			  nativeQuery = true)
	List<ExerciseInRound> getAllExercisesInRound(@Param("topTrainingsIds")List<Long> topTrainingsIds);
	
	List<ExerciseInRound> findByRoundTrainingRoundIn(List<Training> trainings);
}
