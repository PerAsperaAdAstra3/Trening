package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

	List<Exercise> findByNameIgnoreCaseContainingAndDescriptionIgnoreCaseContaining(String name, String description);
	Exercise findByName(String name);
}
