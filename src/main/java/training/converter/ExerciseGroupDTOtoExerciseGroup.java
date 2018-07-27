package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseGroupDTO;
import training.model.ExerciseGroup;

@Component
public class ExerciseGroupDTOtoExerciseGroup implements Converter<ExerciseGroupDTO, ExerciseGroup> {

	@Override
	public ExerciseGroup convert(ExerciseGroupDTO source) {
		if (source == null) {
			return null;
		}

		ExerciseGroup exerciseGroup = new ExerciseGroup();
		exerciseGroup.setId(source.getId());
		exerciseGroup.setName(source.getName());
		return exerciseGroup;
	}

}
