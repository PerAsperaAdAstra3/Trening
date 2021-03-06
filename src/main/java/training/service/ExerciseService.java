package training.service;

import java.util.List;

import training.model.Exercise;

public interface ExerciseService {

	Exercise findOne(Long id);

	List<Exercise> filter(Exercise exercise);
	
	List<Exercise> findAll();

	Exercise save(Exercise exercise);

	List<Exercise> save(List<Exercise> exercises);

	Exercise delete(Long id);

	void delete(List<Long> ids);
	
	Exercise edit(Long id, Exercise exercise);
}
