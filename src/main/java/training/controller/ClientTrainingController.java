package training.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import training.converter.ClientToClientDTO;
import training.converter.TrainingToTrainingDTO;
import training.model.Training;
import training.repository.TrainingRepository;
import training.service.ClientService;
import training.util.LoggingUtil;

@Controller
public class ClientTrainingController {

	@Autowired
	private TrainingToTrainingDTO trainingToTrainingDTO;

	@Autowired
	private ClientToClientDTO clientToClientDTO;

	@Autowired
	private ClientService clientService;

	@Autowired
	private TrainingRepository trainingRepository;
	
	Logger logger = LoggerFactory.getLogger(ClientTrainingController.class);

	@RequestMapping(value = {"/clientTrainingSubmit/{id}"}, method = RequestMethod.GET)
	public String selectPage(Model model, @PathVariable String id) {
		try {
			List<Training> trainingList = trainingRepository.findAllByClientIdOrderByIdDesc(Long.parseLong(id));
			for(Training training : trainingList) {
				if(training.isCircularYN() == null) {
					training.setCircularYN(false);
				}
			}
			model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingList));
		} catch (NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		} catch (IllegalArgumentException illegalArgumentException) {
			LoggingUtil.LoggingMethod(logger, illegalArgumentException);
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		model.addAttribute("clientId", id);
		model.addAttribute("idOfCopiedTraining",""); //TODO change these to null.
		model.addAttribute("idOfClientToCopyTo","");
		return "clientTrainingsInFolderB";		
	}
}
