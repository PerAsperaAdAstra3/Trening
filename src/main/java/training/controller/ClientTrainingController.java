package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import training.converter.ClientToClientDTO;
import training.converter.TrainingToTrainingDTO;
import training.dto.ClientDTO;
import training.model.Training;
import training.repository.TrainingRepository;
import training.service.ClientService;

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
	
@PostMapping(value = { "/clientTrainingList" })
public ResponseEntity<?> addExerciseInRound(@Valid @RequestBody ClientDTO clientDTO) {
		
		List<Training> trainingClient = trainingRepository.findAllByClientIdOrderByIdDesc(clientDTO.getId());
		for(Training training : trainingClient) {
			System.out.println(training.getDate());
		}
		JSONObject obj = new JSONObject();	
		return ResponseEntity.ok(obj.toString());
}
	
	@RequestMapping(value = {"/clientTrainingSubmit/{id}"}, method = RequestMethod.GET)
	public String selectPage(Model model, @PathVariable String id) {
			
			model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingRepository.findAllByClientIdOrderByIdDesc(Long.parseLong(id))));
			model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
			model.addAttribute("clientId", id);
			model.addAttribute("idOfCopiedTraining","");
			model.addAttribute("idOfClientToCopyTo","");
			
		return "clientTrainingsInFolderB";		
	}
}
