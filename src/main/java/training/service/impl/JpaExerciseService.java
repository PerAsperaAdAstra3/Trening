package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import training.model.Exercise;
import training.repository.ExerciseRepository;
import training.service.ExerciseService;

public class JpaExerciseService implements ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Override
	public Exercise findOne(Long id) {
		return exerciseRepository.findOne(id);
	}

	@Override
	public List<Exercise> findAll() {
		return exerciseRepository.findAll();
	}

	@Override
	public Exercise save(Exercise exercise) {
		return exerciseRepository.save(exercise);
	}

	@Override
	public List<Exercise> save(List<Exercise> exercises) {
		return exerciseRepository.save(exercises);
	}

	@Override
	public Exercise delete(Long id) {
		Exercise exercise = exerciseRepository.findOne(id);
		if(exercise == null){
			throw new IllegalStateException("Exercise does not exists");
		}
		exerciseRepository.delete(exercise);
		return exercise;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids)
			this.delete(id);
	}
}
