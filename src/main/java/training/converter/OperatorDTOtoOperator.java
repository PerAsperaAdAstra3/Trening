package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.OperatorDTO;
import training.model.Operator;
import training.service.OperatorService;

@Component
public class OperatorDTOtoOperator implements Converter<OperatorDTO, Operator> {
	
	@Autowired
	OperatorService operatorService;
	
	@Override
	public Operator convert(OperatorDTO source) {
		
		if(source == null) {
			return null;
		}
		Operator operator = new Operator();
		operator.setId(source.getId());
		operator.setUserName(source.getUserName());
		operator.setPassword(source.getPassword());
		operator.setAuthorities(source.getAuthorities());
		
		return operator;
	}

}
