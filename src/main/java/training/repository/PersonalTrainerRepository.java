package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.model.PersonalTrainer;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {

}
