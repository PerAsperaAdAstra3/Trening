package training.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.TrainingDTO;
import training.model.Training;
import training.service.ClientService;

@Component
public class TrainingDTOtoTraining implements Converter<TrainingDTO,Training>{

	@Autowired
	private ClientService clientService;
	
	@Override
	public Training convert(TrainingDTO source) {
		
		if (source == null) {
			return null;
		}

		Training training = new Training();
		//client.setId(source.getId());
		training.setDate(source.getDate());
		training.setNumberOfTrainings(source.getNumberOfTrainings());
		if(source.getClientId() != null) {
			training.setClient(clientService.findOne(Long.parseLong(source.getClientId())));
		}
		return training;
	}

}
