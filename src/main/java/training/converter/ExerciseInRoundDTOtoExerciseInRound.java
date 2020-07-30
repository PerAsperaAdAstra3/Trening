package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseInRoundDTO;
import training.model.ExerciseInRound;
import training.repository.ExerciseInRoundRepository;
import training.repository.ExerciseRepository;
import training.service.RoundService;

@Component
public class ExerciseInRoundDTOtoExerciseInRound implements Converter<ExerciseInRoundDTO, ExerciseInRound> {
	
	@Autowired
	private RoundService roundService;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Override
	public ExerciseInRound convert(ExerciseInRoundDTO source) {
	
		ExerciseInRound exerciseInRound = new ExerciseInRound();
		exerciseInRound.setDifficulty(source.getDifficulty());
	//	exerciseInRound.setExerciseName(source.getExercise().getName()); //getExerciseInRoundExerciseName());
		exerciseInRound.setExerciseId(source.getExerciseInRoundExerciseId());
		if(source.getExercise() == null ) {
			exerciseInRound.setExercise(exerciseRepository.getOne(source.getExerciseInRoundExerciseId()));
		} else {
			exerciseInRound.setExercise(source.getExercise());
		}

		exerciseInRound.setNumberOfRepetitions(source.getNumberOfRepetitions());
		exerciseInRound.setNote(source.getNote());
		exerciseInRound.setRound(roundService.findOne(source.getRoundId()));
		return exerciseInRound;
	}
	
}
