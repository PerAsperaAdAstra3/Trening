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
import training.model.Training;
import training.service.ClientService;
import training.util.LoggingUtil;

@Component
public class TrainingDTOtoTraining implements Converter<TrainingDTO,Training>{

	@Autowired
	private ClientService clientService;
	
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
			logger.error(e.getMessage());
			logger.error(LoggingUtil.LoggingMethod(e));
			e.printStackTrace();
		}
	
		training.setNumberOfTrainings(source.getNumberOfTrainings());
		training.setClient(clientService.findOne(Long.parseLong(source.getClientId())));
		return training;
	}

}
