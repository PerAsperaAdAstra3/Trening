package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseInRoundDTO;
import training.model.ExerciseInRound;
import training.service.RoundService;

@Component
public class ExerciseInRoundDTOtoExerciseInRound implements Converter<ExerciseInRoundDTO, ExerciseInRound> {

	@Autowired
	private RoundService roundService;
	
	@Override
	public ExerciseInRound convert(ExerciseInRoundDTO source) {
	
		ExerciseInRound exerciseInRound = new ExerciseInRound();
		exerciseInRound.setDifficulty(source.getDifficulty());
		exerciseInRound.setExerciseName(source.getExerciseInRoundExerciseName());
		exerciseInRound.setExerciseId(source.getexerciseInRoundExerciseId());
		exerciseInRound.setNumberOfRepetitions(source.getNumberOfRepetitions());
	//	exerciseInRound.setRound(roundService.findOne(source.getRoundId()));
		return exerciseInRound;
	}
	
}
