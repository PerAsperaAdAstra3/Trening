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
	public Operator save(Operator operator) {
		return operatorRepository.save(operator);
	}

	@Override
	public List<Operator> save(List<Operator> operators) {
		return operatorRepository.save(operators);
	}

	@Override
	public Operator delete(Long id) {
		Operator operator = operatorRepository.findOne(id) ;
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
		Operator oldOperator = operatorRepository.findOne(id);
		oldOperator.setUserName(operator.getUserName());
		oldOperator.setPassword(operator.getPassword());
		oldOperator.setAuthorities(operator.getAuthorities());
	/*	for(String action : operator.getActionList()) {
			oldOperator.setActionList(action);
		}*/
		operatorRepository.save(oldOperator);
		
		return oldOperator;
	}

}
