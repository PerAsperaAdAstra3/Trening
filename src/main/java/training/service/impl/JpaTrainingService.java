package training.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.ExerciseInRound;
import training.model.Round;
import training.model.Training;
import training.repository.TrainingRepository;
import training.service.TrainingService;

@Service
@Transactional
public class JpaTrainingService implements TrainingService {

	@Autowired
	private TrainingRepository trainingRepository;
	
	@Override
	public Training findOne(Long id) {
		return trainingRepository.findOne(id);
	}

	@Override
	public List<Training> findAll() {
		return trainingRepository.findAll();
	}

	@Override
	public Training save(Training training) {
		return trainingRepository.save(training);
	}

	@Override
	public List<Training> save(List<Training> trainings) {
		return trainingRepository.save(trainings);
	}

	@Override
	public Training delete(Long id) {
		Training training = trainingRepository.findOne(id);
		if(training == null){
			throw new IllegalArgumentException("Training does not exist");
		}
		trainingRepository.delete(training);
		return training;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids) {
			this.delete(id);
		}
	}

	@Override
	public Training edit(Long id, Training training) {
		Training newTraining = trainingRepository.findOne(id);
		newTraining.setDate(training.getDate());
		newTraining.setNumberOfTrainings(training.getNumberOfTrainings());
		return newTraining;
	}

	@Override
	public Map<Long, Integer> exercisesLastTraining(Long clientId) {
		List<Training> lastTrainings = trainingRepository.findTop4ByClientIdOrderByIdDesc(clientId);
		Map<Long,Integer> mapExercise = new HashMap<>();
		int i =1;
		for(Training training : lastTrainings) {
			for(Round round : training.getRounds())
				for(ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
					if(!mapExercise.containsKey(exerciseInRound.getExerciseId()))
						mapExercise.put(exerciseInRound.getExerciseId(), i);
				}
			i++;
		}
		return mapExercise;
	}
}
