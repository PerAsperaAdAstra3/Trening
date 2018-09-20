package training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Exercise;
import training.model.ExerciseInRound;
import training.model.Round;
import training.model.Training;
import training.repository.TrainingRepository;
import training.service.ExerciseService;
import training.service.TrainingService;

@Service
@Transactional
public class JpaTrainingService implements TrainingService {

	@Autowired
	private TrainingRepository trainingRepository;

	@Autowired
	private ExerciseService exerciseService;

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
		newTraining.setClient(training.getClient());
		trainingRepository.save(newTraining);
		return newTraining;
	}

	@Override
	public Map<Long, Integer> exercisesLastTraining(Training training) {
		
		List<Training> lastTrainings = trainingRepository.findTop10ByClientIdAndIdLessThanOrderByIdDesc(training.getClient().getId(), training.getId());

		Map<Long, Integer> mapExerciseIndexes = new HashMap<Long, Integer>();
		Map<Long, Integer> currentGroupIndexes = new HashMap<Long, Integer>();
		List<Long> allIdsInExerciseTable = new ArrayList<>();
		
		for(Exercise exerciseTemp : exerciseService.findAll()) {
			allIdsInExerciseTable.add(exerciseTemp.getId());
		}
		
		for(Training currentTraining : lastTrainings) {

			List<Long> groupsInTraining = new ArrayList<Long>();

			for(Round round : currentTraining.getRounds())
				for(ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
					if(allIdsInExerciseTable.contains(exerciseInRound.getExerciseId())) {
					Long exerciseGroupId = exerciseService.findOne(exerciseInRound.getExerciseId()).getExerciseGroup().getId();

					Integer index;
					if (groupsInTraining.contains(exerciseGroupId)){
						index = currentGroupIndexes.get(exerciseGroupId);
					}
					else{
						groupsInTraining.add(exerciseGroupId);
						if (!currentGroupIndexes.containsKey(exerciseGroupId)){
							index = 1;
						}
						else{
							index = currentGroupIndexes.get(exerciseGroupId) + 1;
						}
						currentGroupIndexes.put(exerciseGroupId, index);
					}
					if (!mapExerciseIndexes.containsKey(exerciseInRound.getExerciseId()))
						mapExerciseIndexes.put(exerciseInRound.getExerciseId(), index.intValue());
					}
				}
		}

		return mapExerciseIndexes;
	}
}
