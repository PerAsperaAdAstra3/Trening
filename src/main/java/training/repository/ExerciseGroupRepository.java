package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.ExerciseGroup;

@Repository
public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup ,Long> {
	List<ExerciseGroup> findByNameIgnoreCaseContaining(String name);
	
	@Query(
			value ="select * from exercise_group;",
			nativeQuery = true)
	List<ExerciseGroup> getExerciseGroupTest();

	@Query( 
			value ="select * from exercise_group as eg where eg.id in :exerciseGroupIdsToGet order by eg.id asc;",
			nativeQuery = true)
	List<ExerciseGroup> getExerciseGroupsInIdList(@Param("exerciseGroupIdsToGet")List<Long> exerciseGroupIdsToGet);
}
