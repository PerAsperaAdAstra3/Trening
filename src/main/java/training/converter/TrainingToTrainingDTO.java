package training.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String strDate = formatter.format(source.getDate());
		trainingDTO.setDate(strDate);
		trainingDTO.setNumberOfTrainings(source.getNumberOfTrainings());
		if(source.getClient() != null) {
			trainingDTO.setClient(source.getClient().getName());
			trainingDTO.setClientFamilyName(source.getClient().getFamilyName());
			trainingDTO.setClientId(source.getClient().getId().toString());
		}
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
