package training.repository;

import java.sql.ResultSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.Exercise;
import training.model.ExerciseInRound;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

	List<Exercise> findByNameIgnoreCaseContainingAndDescriptionIgnoreCaseContaining(String name, String description);
	Exercise findByName(String name);
/*	
	@Query(
			  value = "select exercise_group from exercise as e inner join (select * from exercise_in_round where round_exercise_in_round in (select id from round where training_round in :topTrainingsIds)) as T on e.id = T.exerciseid;",
			  nativeQuery = true)
	List<Long> getAllExerciseGroupsFortraining(@Param("topTrainingsIds")List<Long> topTrainingsIds);
	
	@Query(
			  value = "select * from exercise_in_round where round_exercise_in_round in (select id from round where training_round in :topTrainingsIds)",
			  nativeQuery = true)
	List<Long> getAllExercisesInRound(@Param("topTrainingsIds")List<Long> topTrainingsIds);
	*/
	
	@Query(
			  value = "select * from exercise as e inner join (select * from exercise_in_round where round_exercise_in_round in (select id from round where training_round in :topTrainingsIds)) as T on e.id = T.exerciseid;",
			  nativeQuery = true)
	List<Exercise> getAllExerciseGroupsFortraining(@Param("topTrainingsIds")List<Long> topTrainingsIds);
	
	@Query(
			  value = "select T.exec_in_round_id, e.exercise_group from exercise as e inner join (select * from exercise_in_round where round_exercise_in_round in (select id from round where training_round in :topTrainingsIds)) as T on e.id = T.exerciseid;",
			  nativeQuery = true)
	List<Long> getAllExerciseGroupsFortrainingMULTIPLE(@Param("topTrainingsIds")List<Long> topTrainingsIds);
//	ResultSet getAllExerciseGroupsFortraining(@Param("topTrainingsIds")List<Long> topTrainingsIds);
}
