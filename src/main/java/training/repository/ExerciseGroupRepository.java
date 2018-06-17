package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.ExerciseGroup;

@Repository
public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup ,Long> {

}
