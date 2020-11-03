package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Operator;
import training.repository.OperatorRepository;
import training.service.OperatorService;

@Service
@Transactional
public class JpaOperatorService implements OperatorService {

	@Autowired
	OperatorRepository operatorRepository;
	
	@Override
	public Operator findOne(Long id) {
		return operatorRepository.getOne(id);
	}

	@Override
	public List<Operator> findAll() {
		return operatorRepository.findAll();
	}

	@Override
	public List<Operator> findByUsername(String username) {
		return operatorRepository.findByUserName(username);
	}
	
	@Override
	public List<Operator> findByEmail(String email) {
		return operatorRepository.findByEmail(email);
	}
	
	@Override
	public Operator save(Operator operator) {
		return operatorRepository.save(operator);
	}

	@Override
	public List<Operator> save(List<Operator> operators) {
		return operatorRepository.saveAll(operators);
	}

	@Override
	public Operator delete(Long id) {
		Operator operator = operatorRepository.findById(id).get();
		if(id == null) {
			throw new IllegalStateException("Round does not exist");
		}
		operatorRepository.delete(operator);
		return operator;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids)
			this.delete(id);
	}

	@Override
	public Operator edit(Long id, Operator operator) {
		Operator oldOperator = operatorRepository.findById(id).get();
		oldOperator.setUserName(operator.getUserName());
		if(operator.getPassword() != null && !operator.getPassword().equals("")) {
			oldOperator.setPassword(operator.getPassword());
		}
		oldOperator.setAuthorities(operator.getAuthorities());
		oldOperator.setPersonalName(operator.getPersonalName());
		oldOperator.setFamilyName(operator.getFamilyName());
		oldOperator.setEmail(operator.getEmail());
		operatorRepository.save(oldOperator);
		
		return oldOperator;
	}

	@Override
	public Operator findOneByUserName(String username) {
		return operatorRepository.findOneByUserName(username);
	}

	@Override
	public Operator findOneByEmail(String email) {
		return operatorRepository.findOneByEmail(email);
	}

}
