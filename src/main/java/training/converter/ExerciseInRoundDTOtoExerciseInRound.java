package training.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseDTO;
import training.dto.ExerciseInRoundDTO;
import training.model.Exercise;
import training.model.ExerciseInRound;
import training.model.Round;
import training.repository.ExerciseInRoundRepository;
import training.repository.ExerciseRepository;
import training.repository.RoundRepository;
import training.service.RoundService;

@Component
public class ExerciseInRoundDTOtoExerciseInRound implements Converter<ExerciseInRoundDTO, ExerciseInRound> {
	
	@Autowired
	private RoundService roundService;
	
	@Autowired
	private RoundRepository roundRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private ExerciseDTOtoExercise exerciseDTOtoExercise;
	
	@Override
	public ExerciseInRound convert(ExerciseInRoundDTO source) {
	
		ExerciseInRound exerciseInRound = new ExerciseInRound();
		exerciseInRound.setDifficulty(source.getDifficulty());
		exerciseInRound.setExerciseId(source.getExerciseInRoundExerciseId());
		if(source.getExercise() == null ) {
			exerciseInRound.setExercise(exerciseRepository.findById(source.getExerciseInRoundExerciseId()).get());
		} else {
			exerciseInRound.setExercise(exerciseDTOtoExercise.convert(source.getExercise()));
		}

		exerciseInRound.setNumberOfRepetitions(source.getNumberOfRepetitions());
		exerciseInRound.setNote(source.getNote());
		exerciseInRound.setRound(roundRepository.findById(source.getRoundId()).get());
		return exerciseInRound;
	}
	
	public ExerciseInRound convertForList(ExerciseInRoundDTO source, List<Exercise> listExerciseToGet, List<Long> roundsToGetIds) {
		
		ExerciseInRound exerciseInRound = new ExerciseInRound();
		exerciseInRound.setDifficulty(source.getDifficulty());
		exerciseInRound.setExerciseId(source.getExerciseInRoundExerciseId());
		if(source.getExercise() == null ) {
			exerciseInRound.setExercise(exerciseRepository.findById(source.getExerciseInRoundExerciseId()).get());
		} else {
			exerciseInRound.setExercise(exerciseDTOtoExercise.convert(source.getExercise()));
		}

		exerciseInRound.setNumberOfRepetitions(source.getNumberOfRepetitions());
		exerciseInRound.setNote(source.getNote());
		exerciseInRound.setRound(roundRepository.findById(source.getRoundId()).get());
		return exerciseInRound;
	}
	
	public List<ExerciseInRound> convert(List<ExerciseInRoundDTO> source) {
		List<ExerciseInRound> exerciseInRoundList = new ArrayList<ExerciseInRound>();
		
		List<Exercise> listExerciseToGet = new ArrayList<Exercise>();
		List<Long> exerciseIdsToGet = new ArrayList<Long>();
		List<Long> roundsToGetIds = new ArrayList<Long>();
		Map<Long,Round> roundMap = new HashMap<Long,Round>();
		List<Round> roundsToGet = new ArrayList<Round>();
		
		for(ExerciseInRoundDTO execInRoundDTO : source) {
			if(execInRoundDTO.getExercise() == null) {
				exerciseIdsToGet.add(execInRoundDTO.getExercise().getId());
			}
			roundsToGetIds.add(execInRoundDTO.getRoundId());
		}

		if(exerciseIdsToGet.size() > 0) {
			listExerciseToGet = exerciseRepository.getAllExerciseInIds(exerciseIdsToGet);
		}
		roundsToGet = roundRepository.getAllRoundsThatAreInList(roundsToGetIds);

		List<ExerciseDTO> exerciseDTOList = new ArrayList<ExerciseDTO>();
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		for(ExerciseInRoundDTO exerciseInRoundDTOiter : source) {
			exerciseDTOList.add(exerciseInRoundDTOiter.getExercise());
		}
		
		exerciseList = exerciseDTOtoExercise.convert(exerciseDTOList);
		
		for(int y =0; y < source.size(); y++) {
			ExerciseInRound exerciseInRound = new ExerciseInRound();
			exerciseInRound.setDifficulty(source.get(y).getDifficulty());
			exerciseInRound.setExerciseName(source.get(y).getExercise().getName());
			exerciseInRound.setExerciseId(source.get(y).getExerciseInRoundExerciseId());
			if(source.get(y).getExercise() == null ) {
				for(Exercise exerciseIter : listExerciseToGet) {
					if(exerciseIter.getId() == source.get(y).getExerciseInRoundExerciseId()) {
						exerciseInRound.setExercise(exerciseIter);
					}
				}
			} else {
				for(Exercise exercise : exerciseList) {
					if(exercise.getId() == source.get(y).getExercise().getId()) {
						exerciseInRound.setExercise(exerciseDTOtoExercise.convert(source.get(y).getExercise()));
					}
				}
			}

			exerciseInRound.setNumberOfRepetitions(source.get(y).getNumberOfRepetitions());
			exerciseInRound.setNote(source.get(y).getNote());
			for(Round round : roundsToGet) {
				if(round.getId() == source.get(y).getRoundId()) {
					exerciseInRound.setRound(round);
				}
			}
			exerciseInRoundList.add(exerciseInRound);
		}
		return exerciseInRoundList;
	}
	
}
