package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.TaskToTaskDTO;
import training.dto.TaskDTO;
import training.model.Task;
import training.service.TaskService;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskToTaskDTO taskToTaskDTO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TaskDTO>> findAll(){
		List<Task> tasks = taskService.findAll();
		return new ResponseEntity<>(taskToTaskDTO.convert(tasks) , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<TaskDTO> findOne(@PathVariable Long id){
		Task task = taskService.findOne(id);
		return new ResponseEntity<>(taskToTaskDTO.convert(task) , HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json") 
	public ResponseEntity<TaskDTO> add(@RequestBody Task task){
		taskService.save(task);
		return new ResponseEntity<>(taskToTaskDTO.convert(task) , HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<TaskDTO> delete(@PathVariable Long id){
		Task task = taskService.delete(id);
		return new ResponseEntity<>(taskToTaskDTO.convert(task) , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<TaskDTO> edit(@PathVariable Long id, @RequestBody Task task){
		Task newTask = taskService.edit(id, task);
		return new ResponseEntity<>(taskToTaskDTO.convert(newTask) , HttpStatus.OK);
	}
}
