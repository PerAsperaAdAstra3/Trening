package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import training.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
