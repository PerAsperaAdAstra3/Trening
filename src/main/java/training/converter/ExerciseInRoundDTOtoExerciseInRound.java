package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseInRoundDTO;
import training.model.ExerciseInRound;

@Component
public class ExerciseInRoundDTOtoExerciseInRound implements Converter<ExerciseInRoundDTO, ExerciseInRound> {
	
	@Override
	public ExerciseInRound convert(ExerciseInRoundDTO source) {
	
		ExerciseInRound exerciseInRound = new ExerciseInRound();
		exerciseInRound.setDifficulty(source.getDifficulty());
		exerciseInRound.setExerciseName(source.getExerciseInRoundExerciseName());
		exerciseInRound.setExerciseId(source.getexerciseInRoundExerciseId());
		exerciseInRound.setNumberOfRepetitions(source.getNumberOfRepetitions());
		exerciseInRound.setNote(source.getNote());
		return exerciseInRound;
	}
	
}
