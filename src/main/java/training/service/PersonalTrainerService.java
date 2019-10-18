package training.service;

import java.util.List;

import training.model.PersonalTrainer;

public interface PersonalTrainerService {

	PersonalTrainer findOne(Long id);
	
	List<PersonalTrainer> filer(PersonalTrainer personalTrainer);
	
	List<PersonalTrainer> findAll();
	
	PersonalTrainer save(PersonalTrainer personalTrainer);
	
	List<PersonalTrainer> save(List<PersonalTrainer> personalTrainer);
	
	PersonalTrainer delete(Long id);
	
	void delete(List<Long> ids);
	
	PersonalTrainer edit(Long id, PersonalTrainer personalTrainer);
}
