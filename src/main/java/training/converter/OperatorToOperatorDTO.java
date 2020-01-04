package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.OperatorDTO;
import training.model.Operator;

@Component
public class OperatorToOperatorDTO implements Converter<Operator, OperatorDTO> {
	@Override
	public OperatorDTO convert(Operator source){

		if(source==null){
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();
		OperatorDTO operatorDTO = modelMapper.map(source, OperatorDTO.class);
		return operatorDTO;
	}
	
	public List<OperatorDTO> convert(List<Operator> source){
		List<OperatorDTO> operatorDTOList = new ArrayList<OperatorDTO>();
		for(Operator operator : source) {
			operatorDTOList.add(convert(operator));
		}
		return operatorDTOList;
	}
}
