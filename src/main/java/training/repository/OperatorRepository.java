package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

}
