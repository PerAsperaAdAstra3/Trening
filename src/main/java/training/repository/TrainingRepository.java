package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import training.model.ExerciseInRound;
import training.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{
	List<Training> findTop10ByClientIdAndIdLessThanOrderByIdDesc(Long clientId, Long id);
	
	List<Training> findAllByClientIdOrderByIdDesc(Long clientId);
}
