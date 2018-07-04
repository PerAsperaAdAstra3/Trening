package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.TaskDTO;
import training.model.Task;

@Component
public class TaskToTaskDTO implements Converter<Task,TaskDTO> {

	@Override
	public TaskDTO convert(Task source) {
		if(source == null) {
			return null;
		}
		ModelMapper methodMapper = new ModelMapper();
		TaskDTO taskDTO = methodMapper.map(source, TaskDTO.class);
		return taskDTO;
	}
	
	public List<TaskDTO> convert(List<Task> sources){
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		for(Task task : sources) {
			taskDTOs.add(convert(task));
		}
		return taskDTOs;
	}
}
