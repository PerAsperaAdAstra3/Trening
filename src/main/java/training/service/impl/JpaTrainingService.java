package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		for(Long id : ids)
			this.delete(id);
	}
}
