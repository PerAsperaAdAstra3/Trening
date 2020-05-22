package training.service;

import java.util.List;

import training.model.Operator;

public interface OperatorService {

	Operator findOne(Long id);
	
	List<Operator> findAll();
	
	List<Operator> findByUsername(String username);
	
	Operator findOneByUserName(String username);
	
	Operator findOneByEmail(String username);
	
	List<Operator> findByEmail(String email);
	
	Operator save(Operator operator);
	
	List<Operator> save(List<Operator> operators);
	
	Operator delete(Long id);
	
	void delete(List<Long> ids);
	
	Operator edit(Long id, Operator operator);
}
