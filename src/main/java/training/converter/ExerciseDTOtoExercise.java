package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.ExerciseDTO;
import training.model.Exercise;
import training.model.ExerciseGroup;
import training.repository.ExerciseGroupRepository;
import training.service.ExerciseGroupService;

@Component
public class ExerciseDTOtoExercise implements Converter<ExerciseDTO,Exercise> {
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupRepository exerciseGroupRepository;
	
	@Override
	public Exercise convert(ExerciseDTO source) {
		
		if(source == null) {
			return null;
		}
		Exercise exercise = new Exercise();
		exercise.setId(source.getId());
		exercise.setName(source.getName());
		exercise.setDescription(source.getDescription());
		exercise.setExerciseGroup(exerciseGroupService.findOne(source.getExerciseGroupId()));
		return exercise;
	}
	
	public List<Exercise> convert(List<ExerciseDTO> source) {
		
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		
		List<Long> exerciseGroupIdList = new ArrayList<Long>();
		List<ExerciseGroup> exerciseGroupList = new ArrayList<ExerciseGroup>();
		
		for(ExerciseDTO exerciseDTOiter : source) {
			exerciseGroupIdList.add(exerciseDTOiter.getExerciseGroupId());
		}
		exerciseGroupList = exerciseGroupRepository.getExerciseGroupsInIdList(exerciseGroupIdList);
		
		for(ExerciseDTO exerciseDTOiter : source) {
			Exercise exercise = new Exercise();
			exercise.setId(exerciseDTOiter.getId());
			exercise.setName(exerciseDTOiter.getName());
			exercise.setDescription(exerciseDTOiter.getDescription());
			
			for(ExerciseGroup exerciseGroupTemp : exerciseGroupList) {
				if(exerciseGroupTemp.getId() == exerciseDTOiter.getExerciseGroupId()) {
					exercise.setExerciseGroup(exerciseGroupTemp);
				}
			}
			exerciseList.add(exercise);
		}
		return exerciseList;
	}
}
