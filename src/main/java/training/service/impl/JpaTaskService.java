package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import training.model.Task;
import training.repository.TaskRepository;
import training.service.TaskService;

public class JpaTaskService implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task findOne(Long id) {
		return taskRepository.findOne(id);
	}

	@Override
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public List<Task> save(List<Task> tasks) {
		return taskRepository.save(tasks);
	}

	@Override
	public Task delete(Long id) {
		Task task = taskRepository.findOne(id);
		if(task == null){
			throw new IllegalArgumentException("Missing task!");
		}
		taskRepository.delete(task);
		return task;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids)
			this.delete(id);
	}
}
