package training.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.dto.TrainingDTO;
import training.enumerations.ClientPackageStateEnum;
import training.enumerations.TrainingStatusEnum;
import training.model.Client;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
import training.model.Exercise;
import training.model.Training;
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
		changeClientPackageElementState(training, newTraining);
		newTraining.setDate(training.getDate());
		newTraining.setNumberOfTrainings(training.getNumberOfTrainings());
		newTraining.setClient(training.getClient());
		newTraining.setStatus(training.getStatus());
		trainingRepository.save(newTraining);
		return newTraining;
	}
	
	private void changeClientPackageElementState(Training training, Training oldTraining) {
		Client client = training.getClient();
		TrainingStatusEnum tse = training.getStatus();
		
		System.out.println("Uslo smo u changeClientPackageElementState");
		List<ClientPackage> clientPackages = client.getClientPackages();
		System.out.println("New status : " + training.getStatus());
		System.out.println("Old status : " + oldTraining.getStatus());
		if(training.getStatus() != oldTraining.getStatus()) {
			System.out.println("Status je razlicit!! Usli smo u IF");
			for(ClientPackage clientPackage : clientPackages) {
				ClientPackageStateEnum clientPackageStateEnum = clientPackage.getClientPackageActive();
				if(clientPackageStateEnum == clientPackageStateEnum.ACTIVE) {
					System.out.println("Klijentov paket aktivan!");
					List<ClientPackageElement> clientPackageElements = clientPackage.getClientPackageElements();
					for(ClientPackageElement clientPackageElement : clientPackageElements) {
						System.out.println("Is protected - clientPackageElement : "+clientPackageElement.isIsProtected());
						System.out.println("Status - clientPackageElement : "+clientPackageElement.isClientPackageElementStatus());
						if(clientPackageElement.isIsProtected() && clientPackageElement.isClientPackageElementStatus()) {
							System.out.println("Trebalo bi da umanji!");
							Date todaysDate = new Date();
							System.out.println("Todays date : " + todaysDate);
							Date oldDateVar = new Date(1990,1,1);
							System.out.println("OldDate : " + oldDateVar);
							if(clientPackageElement.getDateOfChanged() != null) {
								oldDateVar = clientPackageElement.getDateOfChanged();
							}

							if(training.getStatus() == TrainingStatusEnum.READY) {

								if(clientPackageElement.getActiveLeft() < clientPackageElement.getCounter()) {
									clientPackageElement.setActiveLeft(clientPackageElement.getActiveLeft() + 1);
									System.out.println("Active left - povecan za jedan");
									clientPackageElement.setDateOfChanged(todaysDate);
								}
							
							} else {
								if(clientPackageElement.getActiveLeft() > 0) {
									clientPackageElement.setActiveLeft(clientPackageElement.getActiveLeft() - 1);
									System.out.println("Active left - umanjen za jedan");
									clientPackageElement.setDateOfChanged(todaysDate);
								}
								
								if(clientPackageElement.getActiveLeft() < 1) {
									clientPackageElement.setClientPackageElementStatus(false);
									clientPackageElement.setDateOfChanged(todaysDate);
								}
							} 
						}
					}
				}
			}
		}
	}

	@Override
	public Map<Long, Integer> exercisesLastTraining(Training training, List<Exercise> allExercisesList) {
		List<Training> lastTrainings = trainingRepository.findTop10ByClientIdAndIdLessThanOrderByIdDesc(training.getClient().getId(), training.getId());
		List<Long> lastTrainingsId = new ArrayList<Long>();
		for(Training trainingx : lastTrainings) {
			lastTrainingsId.add(trainingx.getId());
		}

		Map<Long, Integer> mapExerciseIndexes = new HashMap<Long, Integer>();
		Map<Long, Integer> currentGroupIndexes = new HashMap<Long, Integer>();
		List<Long> allIdsInExerciseTable = new ArrayList<>();
		if(!lastTrainingsId.isEmpty()) {
			List<Object[]> rs = exerciseRepository.getAllExerciseGroupsFortrainingMULTIPLE(lastTrainingsId);

		//List<Exercise> allExercisesList = exerciseService.findAll();
		for(Exercise exerciseTemp : allExercisesList) {
			allIdsInExerciseTable.add(exerciseTemp.getId());
		}

		Long trainingIdPrevious = -1l;
		List<Long> groupsInTraining = new ArrayList<Long>();
		for(int iter = 0; iter < rs.size(); iter++) {
			Long exercise_group = Long.parseLong(rs.get(iter)[0] + "");
			Long training_round = Long.parseLong(rs.get(iter)[1]+"");
			Long exerciseId = Long.parseLong(rs.get(iter)[2]+"");
			if(iter!=0) {
				trainingIdPrevious = Long.parseLong(rs.get(iter-1)[1]+"");
			} else {
				trainingIdPrevious = Long.parseLong(rs.get(iter)[1]+"");
			}
			
			if(iter!=0 && trainingIdPrevious.longValue() != training_round.longValue()) {
					groupsInTraining = new ArrayList<Long>();
			}
			Integer index = -1;
			if (allIdsInExerciseTable.contains(exerciseId)) {
				Long exerciseGroupId = exercise_group;
				
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
				if (!mapExerciseIndexes.containsKey(exerciseId))
					mapExerciseIndexes.put(exerciseId, index.intValue());
			}
		}
		}
		return mapExerciseIndexes;
	}
}

