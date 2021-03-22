package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

	List<Exercise> findByNameIgnoreCaseContainingAndDescriptionIgnoreCaseContaining(String name, String description);
	Exercise findByName(String name);

	@Query(
			value ="select e.exercise_group, R.training_round, e.id from exercise as e inner join exercise_in_round as eir on e.id = eir.exerciseid inner join round as R on eir.round_exercise_in_round = R.id where training_round in :topTrainingsIds order by R.training_round desc;",
			nativeQuery = true)
	List<Object[]> getAllExerciseGroupsFortrainingMULTIPLE(@Param("topTrainingsIds")List<Long> topTrainingsIds);
	
	@Query(
			value ="select * from exercise as ex where ex.id in :exerciseIdsToGet order by ex.id asc;",
			nativeQuery = true)
	List<Exercise> getAllExerciseInIds(@Param("exerciseIdsToGet")List<Long> exerciseIdsToGet); 
	
	List<Exercise> findByIdIn(List<Long> id);
	
	@Query(
			value ="select * from exercise;",
			nativeQuery = true)
	List<Exercise> getAllExercises();
	
}
