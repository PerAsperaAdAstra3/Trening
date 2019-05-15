package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.PersonalTrainer;
import training.repository.PersonalTrainerRepository;
import training.service.PersonalTrainerService;

@Service
@Transactional
public class JpaPersonalTrainerService implements PersonalTrainerService {
	
	@Autowired
	private PersonalTrainerRepository personalTrainerRepository;

	@Override
	public PersonalTrainer findOne(Long id) {
		return personalTrainerRepository.findOne(id);
	}

	@Override
	public List<PersonalTrainer> filer(PersonalTrainer personalTrainer) {
		return null;
	}

	@Override
	public List<PersonalTrainer> findAll() {
		return personalTrainerRepository.findAll();
	}

	@Override
	public PersonalTrainer save(PersonalTrainer personalTrainer) {
		return personalTrainerRepository.save(personalTrainer);
	}

	@Override
	public List<PersonalTrainer> save(List<PersonalTrainer> personalTrainers) {
		return personalTrainerRepository.save(personalTrainers);
	}

	@Override
	public PersonalTrainer delete(Long id) {
		PersonalTrainer personalTrainer = personalTrainerRepository.findOne(id);
		if(personalTrainer == null) {
			throw new IllegalStateException("Personal trainer not found!");
		}
		personalTrainerRepository.delete(personalTrainer);
		return personalTrainer;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids) {
			this.delete(id);
		}
	}

	@Override
	public PersonalTrainer edit(Long id, PersonalTrainer personalTrainer) {
		PersonalTrainer oldPersonalTrainer = personalTrainerRepository.findOne(id);
		oldPersonalTrainer.setName(personalTrainer.getName());
		oldPersonalTrainer.setFamilyName(personalTrainer.getFamilyName());
		oldPersonalTrainer.setEmail(personalTrainer.getEmail());
		oldPersonalTrainer.setPhoneNumber(personalTrainer.getPhoneNumber());
		personalTrainerRepository.save(oldPersonalTrainer);
		return oldPersonalTrainer;
	}

}
