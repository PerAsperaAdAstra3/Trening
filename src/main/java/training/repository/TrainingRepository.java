package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Client;
import training.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{
	List<Training> findTop4ByClientId(Long clientId);
}
