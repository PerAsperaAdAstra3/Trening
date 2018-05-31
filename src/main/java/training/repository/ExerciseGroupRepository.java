package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import training.model.ExerciseGroup;

public interface ExerciseGroupRepository extends JpaRepository<ExerciseGroup ,Long> {

}
