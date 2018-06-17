package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

}
