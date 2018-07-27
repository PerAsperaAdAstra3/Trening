package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseDTO;
import training.model.Exercise;

@Component
public class ExerciseToExerciseDTO implements Converter<Exercise, ExerciseDTO> {

	@Override
	public ExerciseDTO convert(Exercise source){

		if(source == null){
			return null;
		}

		ExerciseDTO exerciseDTO = new ExerciseDTO();
		exerciseDTO.setId(source.getId());
		exerciseDTO.setName(source.getName());
		exerciseDTO.setDescription(source.getDescription());
		exerciseDTO.setExerciseGroup(source.getExerciseGroup().getName());
		exerciseDTO.setExerciseGroupId(source.getExerciseGroup().getId());
		return exerciseDTO;
	}

	public List<ExerciseDTO> convert(List<Exercise> source){

		List<ExerciseDTO> exerciseDTO = new ArrayList<ExerciseDTO>();
		for(Exercise exercise : source){
			exerciseDTO.add(convert(exercise));
		}
		return exerciseDTO;
	}
}
