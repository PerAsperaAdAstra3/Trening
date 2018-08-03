package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseDTO;
import training.model.Exercise;
import training.model.ExerciseGroup;
import training.service.ExerciseGroupService;

@Component
public class ExerciseDTOtoExercise implements Converter<ExerciseDTO,Exercise> {
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Override
	public Exercise convert(ExerciseDTO source) {
		
		if(source == null) {
			return null;
		}
		Long exerciseGroupFindOneParam = 0l;
		Exercise exercise = new Exercise();
		exercise.setId(source.getId());
		exercise.setName(source.getName());
		exercise.setDescription(source.getDescription());
		/*if(source.getExerciseGroupId() != null) {
			exerciseGroupFindOneParam = source.getExerciseGroupId();
		}
		exercise.setExerciseGroup(exerciseGroupService.findOne(exerciseGroupFindOneParam));
			*/	
		return exercise;
	}
	
}
