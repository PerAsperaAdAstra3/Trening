package training.service;

import java.util.List;

import training.model.*;

public interface TrainingService {

	Training findOne(Long id);

	List<Training> findAll();
	
	Training save(Training training);
	
	List<Training> save(List<Training> trainings);
	
	Training delete(Long id);
	
	void delete(List<Long> ids);
}
