package training.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.PersonalTrainerDTO;
import training.model.PersonalTrainer;

@Component
public class PersonalTrainerToPersonalTrainerDTO implements Converter<PersonalTrainer, PersonalTrainerDTO> {

	@Override
	public PersonalTrainerDTO convert(PersonalTrainer source) {
		PersonalTrainerDTO personalTrainerDTO = new PersonalTrainerDTO();
		personalTrainerDTO.setName(source.getName());
		personalTrainerDTO.setFamilyName(source.getFamilyName());
		personalTrainerDTO.setEmail(source.getEmail());
		personalTrainerDTO.setPhoneNumber(source.getPhoneNumber());
		return personalTrainerDTO;
	}

}
