package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseDTO;
import training.model.Exercise;

@Component
public class ExerciseDTOtoExercise implements Converter<ExerciseDTO,Exercise> {
	
	@Override
	public Exercise convert(ExerciseDTO source) {
		
		if(source == null) {
			return null;
		}
		
		Exercise exercise = new Exercise();
		
		exercise.setName(source.getName());
		exercise.setDescription(source.getDescription());
				
		return exercise;
	}
	
}
