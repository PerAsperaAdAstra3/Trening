package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Client;
import training.model.ExerciseGroup;

@Repository
public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup ,Long> {
	List<ExerciseGroup> findByNameIgnoreCaseContaining(String name);
}
