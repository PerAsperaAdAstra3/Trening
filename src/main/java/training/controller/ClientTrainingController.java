package training.controller;
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
			model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingRepository.findAllByClientIdOrderByIdDesc(Long.parseLong(id))));
		} catch (NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		}
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		model.addAttribute("clientId", id);
		model.addAttribute("idOfCopiedTraining",""); //TODO change these to null.
		model.addAttribute("idOfClientToCopyTo","");
		return "clientTrainingsInFolderB";		
	}
}
