package training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Client;
import training.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {
	List<Operator> findByUserName(String userName);

	Operator findOneByUserName(String userName);
	Operator findOneByEmail(String email);
	
	List<Operator> findByEmail(String email);
	List<Operator> findByAuthoritiesNot(String authorities);
}
