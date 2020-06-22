package training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Client;
import training.model.Exercise;
import training.model.ExerciseInRound;
import training.model.Round;
import training.model.Training;
import training.repository.ExerciseInRoundRepository;
import training.repository.ExerciseRepository;
import training.repository.TrainingRepository;
import training.service.ExerciseService;
import training.service.TrainingService;

@Service
@Transactional
public class JpaTrainingService implements TrainingService {

	@Autowired
	private TrainingRepository trainingRepository;

	@Autowired
	private ExerciseInRoundRepository exerciseInRoundRepository;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private ExerciseRepository exerciseRepository;

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
/*
	@Override
	public Map<Long, Integer> exercisesLastTraining(Training training) {
		
		List<Training> lastTrainings = trainingRepository.findTop10ByClientIdAndIdLessThanOrderByIdDesc(training.getClient().getId(), training.getId());

		Map<Long, Integer> mapExerciseIndexes = new HashMap<Long, Integer>();
		Map<Long, Integer> currentGroupIndexes = new HashMap<Long, Integer>();
		List<Long> allIdsInExerciseTable = new ArrayList<>();
		
		for(Exercise exerciseTemp : exerciseService.findAll()) {
			allIdsInExerciseTable.add(exerciseTemp.getId());
		}
		
		for (Training currentTraining : lastTrainings) {

			List<Long> groupsInTraining = new ArrayList<Long>();

			for (Round round : currentTraining.getRounds())
				for (ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
					Integer index;
					if (allIdsInExerciseTable.contains(exerciseInRound.getExerciseId())) {
						Long exerciseGroupId = exerciseService.findOne(exerciseInRound.getExerciseId())
								.getExerciseGroup().getId();

						if (groupsInTraining.contains(exerciseGroupId)) {
							index = currentGroupIndexes.get(exerciseGroupId);
						} else {
							groupsInTraining.add(exerciseGroupId);
							if (!currentGroupIndexes.containsKey(exerciseGroupId)) {
								index = 1;
							} else {
								index = currentGroupIndexes.get(exerciseGroupId) + 1;
							}
							currentGroupIndexes.put(exerciseGroupId, index);
						}
						if (!mapExerciseIndexes.containsKey(exerciseInRound.getExerciseId()))
							mapExerciseIndexes.put(exerciseInRound.getExerciseId(), index.intValue());
					} else { // Doing the check based on Exercise NAME instead of ID
						exerciseInRound.getExerciseName();
						
						Exercise exerciseOfInterest = null;

						
						exerciseOfInterest = exerciseRepository.findByName(exerciseInRound.getExerciseName());

						
						if(exerciseOfInterest != null)
						if (allIdsInExerciseTable.contains(exerciseOfInterest.getId())) {

							Long exerciseGroupId = exerciseService.findOne(exerciseOfInterest.getId())
									.getExerciseGroup().getId();

							if (groupsInTraining.contains(exerciseGroupId)) {
								index = currentGroupIndexes.get(exerciseGroupId);
							} else {
								groupsInTraining.add(exerciseGroupId);
								if (!currentGroupIndexes.containsKey(exerciseGroupId)) {
									index = 1;
								} else {
									index = currentGroupIndexes.get(exerciseGroupId) + 1;
								}
								currentGroupIndexes.put(exerciseGroupId, index);
							}
							if (!mapExerciseIndexes.containsKey(exerciseOfInterest.getId()))
								mapExerciseIndexes.put(exerciseOfInterest.getId(), index.intValue());
						}
					}
				}
		}

		return mapExerciseIndexes;
	}*/
	
	@Override
	public Map<Long, Integer> exercisesLastTraining(Training training) {
		long start = System.currentTimeMillis();
		System.out.println(start);
		/* do your algorithm iteration */
		
		List<Training> lastTrainings = trainingRepository.findTop10ByClientIdAndIdLessThanOrderByIdDesc(training.getClient().getId(), training.getId());
		List<Long> lastTrainingsId = new ArrayList<Long>();
		for(Training trainingx : lastTrainings) {
			lastTrainingsId.add(trainingx.getId());
		}
		
		long elapsed = System.currentTimeMillis();
		System.out.println("Posle vadjenja top 10 treninga : "+elapsed);
		List<Exercise> exercisesInTraining =  exerciseRepository.getAllExerciseGroupsFortraining(lastTrainingsId);
		elapsed = System.currentTimeMillis();
		System.out.println("Sve vezbe iz treninga : "+elapsed);
	//	elapsed = System.currentTimeMillis() - elapsed;
		Map<Long, Integer> mapExerciseIndexes = new HashMap<Long, Integer>();
		Map<Long, Integer> currentGroupIndexes = new HashMap<Long, Integer>();
		List<Long> allIdsInExerciseTable = new ArrayList<>();
		
	//	List<ExerciseInRound> allExercises =  exerciseInRoundRepository.findByRoundTrainingRoundIn(lastTrainings);
		List<Exercise> allExercisesList = exerciseService.findAll();
		for(Exercise exerciseTemp : allExercisesList) {
			allIdsInExerciseTable.add(exerciseTemp.getId());
		}
		elapsed = System.currentTimeMillis();
		System.out.println("Posle vadjenja svih vezbi : "+elapsed);
		for (Training currentTraining : lastTrainings) {

			List<Long> groupsInTraining = new ArrayList<Long>();

			for (Round round : currentTraining.getRounds())
				for (ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
					Integer index = -1;
				
					if (allIdsInExerciseTable.contains(exerciseInRound.getExerciseId())) {
						Long exerciseGroupId = -1l;
						for(Exercise exerciseTemp : exercisesInTraining) {
							if(exerciseTemp.getId() == exerciseInRound.getExerciseId()) {
								exerciseGroupId = exerciseTemp.getExerciseGroup().getId();
							}
						}
						
						if (groupsInTraining.contains(exerciseGroupId)) {
							index = currentGroupIndexes.get(exerciseGroupId);
						} else {
							groupsInTraining.add(exerciseGroupId);
							if (!currentGroupIndexes.containsKey(exerciseGroupId)) {
								index = 1;
							} else {
								index = currentGroupIndexes.get(exerciseGroupId) + 1;
							}
							currentGroupIndexes.put(exerciseGroupId, index);
						}
						if (!mapExerciseIndexes.containsKey(exerciseInRound.getExerciseId()))
							mapExerciseIndexes.put(exerciseInRound.getExerciseId(), index.intValue());
					} else { // Doing the check based on Exercise NAME instead of ID
						exerciseInRound.getExerciseName();
						
						Exercise exerciseOfInterest = null;
						for(Exercise exerciseTemp : exercisesInTraining) {
							if(exerciseTemp.getName().equals(exerciseInRound.getExerciseName())) {
								exerciseOfInterest = exerciseTemp;
							}
						}

						
						if(exerciseOfInterest != null)
						if (allIdsInExerciseTable.contains(exerciseOfInterest.getId())) {

							Long exerciseGroupId = -1l;

							for(Exercise exerciseTemp : exercisesInTraining) {
								if(exerciseTemp.getId() == exerciseInRound.getExerciseId()) {
									exerciseGroupId = exerciseTemp.getExerciseGroup().getId();
								}
							}

							if (groupsInTraining.contains(exerciseGroupId)) {
								index = currentGroupIndexes.get(exerciseGroupId);
							} else {
								groupsInTraining.add(exerciseGroupId);
								if (!currentGroupIndexes.containsKey(exerciseGroupId)) {
									index = 1;
								} else {
									index = currentGroupIndexes.get(exerciseGroupId) + 1;
								}
								currentGroupIndexes.put(exerciseGroupId, index);
							}
							if (!mapExerciseIndexes.containsKey(exerciseOfInterest.getId()))
								mapExerciseIndexes.put(exerciseOfInterest.getId(), index.intValue());
						}
					}
				}
			elapsed = System.currentTimeMillis();
			System.out.println("Prolaz kroz treninge : "+elapsed);
		}

		return mapExerciseIndexes;
	}
}
