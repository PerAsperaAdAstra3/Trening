package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.Client;
import training.model.ClientPackage;
import training.model.ExerciseInRound;

@Repository
public interface ClientPackageRepository extends JpaRepository<ClientPackage, Long> {
	/*@Query(
			value = "select * from client_package where client = :client;", 
			nativeQuery = true)
	List<ClientPackage> allPackagesOfClient(@Param("client") Integer client);*/
	
	List<ClientPackage> findByClientId(Long id);
}

/*
@Query(
	  value = "select * from exercise_in_round er join round r on r.id = er.round_exercise_in_round join training tr on tr.id = r.training_round where er.exerciseid = :exerciseId and tr.training_list = :clientId ORDER BY tr.date DESC LIMIT 1;", 
	  nativeQuery = true)
	ExerciseInRound previousExerciseOfSameTypeForClient(@Param("clientId") String clientId, @Param("exerciseId") String exerciseId);
 */