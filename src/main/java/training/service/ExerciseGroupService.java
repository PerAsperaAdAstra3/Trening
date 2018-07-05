package training.service;

import java.util.List;

import training.model.ExerciseGroup;

public interface ExerciseGroupService {

	ExerciseGroup findOne(Long id);

	List<ExerciseGroup> findAll();

	ExerciseGroup save(ExerciseGroup exerciseGroup);

	List<ExerciseGroup> save(List<ExerciseGroup> exerciseGroups);

	ExerciseGroup delete(Long id);

	void delete(List<Long> ids);
	
	ExerciseGroup edit(Long id, ExerciseGroup exerciseGroup);
}
