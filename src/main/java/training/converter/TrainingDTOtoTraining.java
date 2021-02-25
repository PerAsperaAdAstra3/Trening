package training.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import training.dto.TrainingDTO;
import training.model.Client;
import training.model.Training;
import training.repository.ClientRepository;
import training.service.ClientService;
import training.util.LoggingUtil;

@Component
public class TrainingDTOtoTraining implements Converter<TrainingDTO,Training>{

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientRepository clientRepository;
	
	Logger logger = LoggerFactory.getLogger(TrainingDTOtoTraining.class);
	
	@Override
	public Training convert(TrainingDTO source) {
		
		if (source == null) {
			return null;
		}

		Training training = new Training();
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = formatter.parse(source.getDate());
			training.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LoggingUtil.LoggingMethod(logger, e);
		}
	
		training.setNumberOfTrainings(source.getNumberOfTrainings());
		training.setClient(clientRepository.findById(Long.parseLong(source.getClientId())).get()); //getOne(Long.parseLong(source.getClientId()))); //clientService.findOne(Long.parseLong(source.getClientId())));
		training.setStatus(source.getStatus());
		training.setTrainingCreator(source.getTrainingCreator());
		training.setTrainingExecutor(source.getTrainingExecutor());
		return training;
	}

public Training convertAlternate(TrainingDTO source, Client client) {
		
		if (source == null) {
			return null;
		}

		Training training = new Training();
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = formatter.parse(source.getDate());
			training.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LoggingUtil.LoggingMethod(logger, e);
		}
	
		training.setNumberOfTrainings(source.getNumberOfTrainings());
		training.setClient(client); //clientRepository.getOne(Long.parseLong(source.getClientId()))); //clientService.findOne(Long.parseLong(source.getClientId())));
		training.setStatus(source.getStatus());
		training.setCircularYN(source.isCircularYN());
		training.setTrainingCreator(source.getTrainingCreator());
		training.setTrainingExecutor(source.getTrainingExecutor());
		return training;
	}
	
}
