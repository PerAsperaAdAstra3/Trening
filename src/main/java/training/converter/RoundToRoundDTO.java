package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.RoundDTO;
import training.model.Round;

@Component
public class RoundToRoundDTO implements Converter<Round,RoundDTO> {

	@Override
	public RoundDTO convert(Round source){

		if(source==null){
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();
		RoundDTO roundDTO = modelMapper.map(source, RoundDTO.class);
		return roundDTO;
	}
	
	public List<RoundDTO> convert(List<Round> source){
		List<RoundDTO> roundDTOList = new ArrayList<RoundDTO>();
		for(Round round : source) {
			roundDTOList.add(convert(round));
		}
		return roundDTOList;
	}
}
