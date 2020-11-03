package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Exercise;
import training.repository.ExerciseRepository;
import training.service.ExerciseService;

@Service
@Transactional
public class JpaExerciseService implements ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Override
	public Exercise findOne(Long id) {
		return exerciseRepository.findById(id).get();
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
		return exerciseRepository.saveAll(exercises);
	}

	@Override
	public Exercise delete(Long id) {
		Exercise exercise = exerciseRepository.findById(id).get();
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
	
	@Override
	public Exercise edit(Long id, Exercise exercise) {
		
		Exercise exerciseOld = exerciseRepository.findById(id).get();
		exerciseOld.setName(exercise.getName());
		exerciseOld.setDescription(exercise.getDescription());
		exerciseRepository.save(exerciseOld);
		return exerciseOld;
	}

	@Override
	public List<Exercise> filter(Exercise exercise) {
		return exerciseRepository.findByNameIgnoreCaseContainingAndDescriptionIgnoreCaseContaining(exercise.getName() , exercise.getDescription()) ;
	}
}
