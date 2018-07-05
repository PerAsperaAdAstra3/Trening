package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseInRoundDTO;
import training.model.Exercise;
import training.model.ExerciseInRound;
import training.model.Round;

@Component
public class ExerciseInRoundToExerciseInRoundDTO implements Converter<ExerciseInRound, ExerciseInRoundDTO> {

	@Override
	public ExerciseInRoundDTO convert(ExerciseInRound source) {
		ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
		exerciseInRoundDTO.setNumberOfRepetitions(source.getNumberOfRepetitions());
		exerciseInRoundDTO.setDifficulty(source.getDifficulty());
		for(Exercise exercise : source.getExercises()) {
			exerciseInRoundDTO.addExecName(exercise.getName());
		}
		for(Round round : source.getRounds()) {
			exerciseInRoundDTO.addRoundName(String.valueOf(round.getRoundSequenceNumber()));
		}
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
