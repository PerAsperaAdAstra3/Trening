package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import training.model.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
	@Query(
			value ="select * from round where id in :ids",
			nativeQuery = true)
	List<Round> getAllRoundsThatAreInList(@Param("ids")List<Long> ids);
}
