package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseGroupDTO;
import training.model.Exercise;
import training.model.ExerciseGroup;

@Component
public class ExerciseGroupToExerciseGroupDTO implements Converter<ExerciseGroup, ExerciseGroupDTO> {

	@Override
	public ExerciseGroupDTO convert(ExerciseGroup source) {
		ExerciseGroupDTO exerciseGroupDTO = new ExerciseGroupDTO();
		exerciseGroupDTO.setName(source.getName());
		for(Exercise exercise : source.getExerciseList()) {
			exerciseGroupDTO.addExerciseNameList(exercise.getName());
		}
		return exerciseGroupDTO;
	}

	public List<ExerciseGroupDTO> convert(List<ExerciseGroup> source){
		List<ExerciseGroupDTO> exerciseListGroupDTO = new ArrayList<ExerciseGroupDTO>();
		for(ExerciseGroup exerciseGroup : source) {
			exerciseListGroupDTO.add(convert(exerciseGroup));
		}
		return exerciseListGroupDTO;
	}
}
