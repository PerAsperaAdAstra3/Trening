package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.ExerciseInRound;
import training.repository.ExerciseInRoundRepository;
import training.service.ExerciseInRoundService;

@Service
@Transactional
public class JpaExerciseInRoundService implements ExerciseInRoundService {

	@Autowired
	private ExerciseInRoundRepository exerciseInRoundRepository;

	@Override
	public ExerciseInRound findOne(Long id) {
		return exerciseInRoundRepository.findOne(id);
	}

	@Override
	public List<ExerciseInRound> findAll() {
		return exerciseInRoundRepository.findAll();
	}

	@Override
	public ExerciseInRound save(ExerciseInRound exerciseInRound) {
		return exerciseInRoundRepository.save(exerciseInRound);
	}

	@Override
	public List<ExerciseInRound> save(List<ExerciseInRound> exercisesInRound) {
		return exerciseInRoundRepository.save(exercisesInRound);
	}

	@Override
	public ExerciseInRound delete(Long id) {
		ExerciseInRound exerciseInRound = exerciseInRoundRepository.findOne(id);
		if (exerciseInRound == null) {
			throw new IllegalStateException("Exercise in round does not exist");
		}
		exerciseInRoundRepository.delete(exerciseInRound);
		return exerciseInRound;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids)
			this.delete(id);
	}
	
	public ExerciseInRound edit(Long id, ExerciseInRound exerciseInRound) {
		ExerciseInRound newExerciseInRound = exerciseInRoundRepository.getOne(id);
		newExerciseInRound.setDifficulty(exerciseInRound.getDifficulty());
		newExerciseInRound.setNumberOfRepetitions(exerciseInRound.getNumberOfRepetitions());
		newExerciseInRound.setExerciseId(exerciseInRound.getExerciseId());
		return newExerciseInRound;
	}

}
