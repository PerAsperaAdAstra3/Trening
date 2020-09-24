package training.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{
	List<Training> findTop10ByClientIdAndIdLessThanOrderByIdDesc(Long clientId, Long id);	
	List<Training> findAllByClientIdOrderByIdDesc(Long clientId);
	
	@Query(
			value ="select * from training where training_list = :clientId and date < :endDate and date > :startDate and status = \"DONE\" ",
			nativeQuery = true)
	List<Training> getForClientInInterval(@Param("clientId")Long clientId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
	
	@Query(
			value ="select * from training where training_executor_id = :executorId and date < :endDate and date > :startDate",
			nativeQuery = true)
	List<Training> getForTrainerInInterval(@Param("executorId")Long executorId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

	@Query(
			value ="select * from training where id = :id",
			nativeQuery = true)
	Training getOneTrainingById(@Param("id")Long id);
}
