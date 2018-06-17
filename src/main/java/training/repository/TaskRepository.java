package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
