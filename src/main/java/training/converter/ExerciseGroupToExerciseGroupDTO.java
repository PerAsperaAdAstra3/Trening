package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseGroupDTO;
import training.model.ExerciseGroup;

@Component
public class ExerciseGroupToExerciseGroupDTO implements Converter<ExerciseGroup, ExerciseGroupDTO> {

	@Override
	public ExerciseGroupDTO convert(ExerciseGroup source) {
		
		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		ExerciseGroupDTO exerciseGroupDTO = modelMapper.map(source, ExerciseGroupDTO.class);
		return exerciseGroupDTO;
	}

	public List<ExerciseGroupDTO> convert(List<ExerciseGroup> source){
		List<ExerciseGroupDTO> exerciseGroupDTOList = new ArrayList<ExerciseGroupDTO>();
		for(ExerciseGroup exerciseGroup : source) {
			exerciseGroupDTOList.add(convert(exerciseGroup));
		}
		return exerciseGroupDTOList;
	}
}
