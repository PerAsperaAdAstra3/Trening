package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PersonalTrainerDTO;
import training.model.PersonalTrainer;

@Component
public class PersonalTrainerDTOtoPersonalTrainer implements Converter<PersonalTrainerDTO, PersonalTrainer> {
	
	@Override
	public PersonalTrainer convert(PersonalTrainerDTO source) {
		
		PersonalTrainer personalTrainer = new PersonalTrainer();
		personalTrainer.setName(source.getName());
		personalTrainer.setFamilyName(source.getFamilyName());
		personalTrainer.setEmail(source.getEmail());
		personalTrainer.setPhoneNumber(source.getPhoneNumber());		
		return personalTrainer;
	}

}
