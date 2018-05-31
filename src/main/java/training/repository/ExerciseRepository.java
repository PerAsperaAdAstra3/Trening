package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import training.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

}
