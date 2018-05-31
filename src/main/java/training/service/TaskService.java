package training.service;

import java.util.List;

import training.model.Task;

public interface TaskService {
	
	Task findOne(Long id);

	List<Task> findAll();

	Task save(Task task);

	List<Task> save(List<Task> tasks);

	Task delete(Long id);

	void delete(List<Long> ids);
}
