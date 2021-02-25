package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseInRoundDTO;
import training.model.ExerciseInRound;

@Component
public class ExerciseInRoundToExerciseInRoundDTO implements Converter<ExerciseInRound, ExerciseInRoundDTO> {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Override
	public ExerciseInRoundDTO convert(ExerciseInRound source) {
		ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
		if(source.getNumberOfRepetitions() != null) {
			exerciseInRoundDTO.setNumberOfRepetitions(source.getNumberOfRepetitions());
		} else {
			exerciseInRoundDTO.setNumberOfRepetitions("");
		}
		
		if(source.getDifficulty() != null) {
			exerciseInRoundDTO.setDifficulty(source.getDifficulty());
		} else {
			exerciseInRoundDTO.setDifficulty("");
		}
		
		if(source.getNote() != null) {
			exerciseInRoundDTO.setNote(source.getNote());
		} else {
			exerciseInRoundDTO.setNote("");
		}
		
		if(source.getExerciseName() != null) {
			exerciseInRoundDTO.setExerciseInRoundExerciseName(source.getExerciseName());
		} else {
			exerciseInRoundDTO.setExerciseInRoundExerciseName("");
		}
		

		exerciseInRoundDTO.setId(source.getExecInRound_Id());
	//	exerciseInRoundDTO.setDifficulty(source.getDifficulty());
	//	exerciseInRoundDTO.setExerciseInRoundExerciseName(source.getExerciseName());
		if(source.getExerciseId() != null) {
			exerciseInRoundDTO.setExerciseInRoundExerciseId(source.getExerciseId());
		} else {
			exerciseInRoundDTO.setExerciseInRoundExerciseId(-1l);
		}
		if(source.getExercise() != null) {
			exerciseInRoundDTO.setExercise(exerciseToExerciseDTO.convert(source.getExercise()));
		} 
		
		if(source.getRound().getId() != null) {
			exerciseInRoundDTO.setRoundId(source.getRound().getId());
		} else {
			exerciseInRoundDTO.setRoundId(-1l);
		}
	//	if(source.getExerciseName() != null) {
			exerciseInRoundDTO.addRoundName(String.valueOf(source.getRound().getRoundSequenceNumber()));
	//	} else {
	//		exerciseInRoundDTO.setExerciseInRoundExerciseName("");
	//	}
	//	exerciseInRoundDTO.setNote(source.getNote());
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
