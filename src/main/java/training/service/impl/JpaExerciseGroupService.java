package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.ExerciseGroup;
import training.repository.ExerciseGroupRepository;
import training.service.ExerciseGroupService;

@Service
@Transactional
public class JpaExerciseGroupService implements ExerciseGroupService {

	@Autowired
	private ExerciseGroupRepository exerciseGroupRepository;

	@Override
	public ExerciseGroup findOne(Long id) {
		return exerciseGroupRepository.findOne(id);
	}

	@Override
	public List<ExerciseGroup> findAll() {
		return exerciseGroupRepository.findAll();
	}

	@Override
	public ExerciseGroup save(ExerciseGroup exerciseGroup) {
		return exerciseGroupRepository.save(exerciseGroup);
	}

	@Override
	public List<ExerciseGroup> save(List<ExerciseGroup> exerciseGroups) {
		return exerciseGroupRepository.save(exerciseGroups);
	}

	@Override
	public ExerciseGroup delete(Long id) {
		ExerciseGroup exerciseGroup = exerciseGroupRepository.findOne(id);
		if (exerciseGroup == null) {
			throw new IllegalStateException("Exercise group does not exist!");
		}
		exerciseGroupRepository.delete(exerciseGroup);
		return exerciseGroup;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
	
	@Override
	public ExerciseGroup edit(Long id, ExerciseGroup exerciseGroup) {
		ExerciseGroup oldExerciseGroup = exerciseGroupRepository.findOne(id);
		oldExerciseGroup.setName(exerciseGroup.getName());
		exerciseGroupRepository.save(oldExerciseGroup);
		return oldExerciseGroup;
	}

	@Override
	public List<ExerciseGroup> filter(ExerciseGroup exerciseGroup) {
		return exerciseGroupRepository.findByNameIgnoreCaseContaining(exerciseGroup.getName());
	}
}
