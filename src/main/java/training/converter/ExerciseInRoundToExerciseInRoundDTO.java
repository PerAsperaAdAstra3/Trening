package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseInRoundDTO;
import training.model.ExerciseInRound;

@Component
public class ExerciseInRoundToExerciseInRoundDTO implements Converter<ExerciseInRound, ExerciseInRoundDTO> {

	@Override
	public ExerciseInRoundDTO convert(ExerciseInRound source) {
		ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
		exerciseInRoundDTO.setNumberOfRepetitions(source.getNumberOfRepetitions());
		exerciseInRoundDTO.setDifficulty(source.getDifficulty());
		exerciseInRoundDTO.addExecName(source.getExercises().get(0).getName());
		exerciseInRoundDTO.addExecName(source.getExercises().get(1).getName());
		return exerciseInRoundDTO;
	}

	public List<ExerciseInRoundDTO> convert(List<ExerciseInRound> sources) {
		List<ExerciseInRoundDTO> exerciseInRoundDTO = new ArrayList<ExerciseInRoundDTO>();
		for(ExerciseInRound exerciseInRound : sources){
			exerciseInRoundDTO.add(convert(exerciseInRound));
		}
		return exerciseInRoundDTO;
	}
}
