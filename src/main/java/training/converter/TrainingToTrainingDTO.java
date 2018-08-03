package training.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.TrainingDTO;
import training.model.Training;

@Component
public class TrainingToTrainingDTO implements Converter<Training,TrainingDTO> {

	@Override
	public TrainingDTO convert(Training source) {
		if(source == null) {
			return null;
		}
		TrainingDTO trainingDTO = new TrainingDTO(); 
		trainingDTO.setId(source.getId());
		trainingDTO.setDate(source.getDate());
		trainingDTO.setNumberOfTrainings(source.getNumberOfTrainings());
		trainingDTO.setClient(source.getClient().getName());
	/*	ModelMapper modelMapper = new ModelMapper();
		TrainingDTO modelDTO = modelMapper.map(source, TrainingDTO.class);
		*/return trainingDTO;
	}
	
	public List<TrainingDTO> convert(List<Training> trainings){
		List<TrainingDTO> trainingDTOs = new ArrayList<TrainingDTO>();
		for(Training training : trainings) {
			trainingDTOs.add(convert(training));
		}
		return trainingDTOs;
	}

}
