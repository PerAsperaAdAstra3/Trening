package training.converter;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseDTO;
import training.model.Exercise;

@Component
public class ExerciseToExerciseDTO implements Converter<Exercise,ExerciseDTO> {
	
	@Override
	public ExerciseDTO convert(Exercise source) {

		if(source == null) {
			return null;
		}
		
		ModelMapper modelMapper = new ModelMapper();
		ExerciseDTO exerciseDTO = modelMapper.map(source, ExerciseDTO.class); 		
		return exerciseDTO;
	}

}
