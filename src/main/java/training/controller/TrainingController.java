package training.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.ExerciseInRoundDTOtoExerciseInRound;
import training.converter.ExerciseToExerciseDTO;
import training.converter.RoundToRoundDTO;
import training.converter.TrainingDTOtoTraining;
import training.converter.TrainingToTrainingDTO;
import training.dto.ExerciseDTO;
import training.dto.ExerciseInRoundDTO;
import training.dto.TrainingDTO;
import training.model.Client;
import training.model.ExerciseInRound;
import training.model.Round;
import training.model.Training;
import training.service.ClientService;
import training.service.ExerciseInRoundService;
import training.service.ExerciseService;
import training.service.RoundService;
import training.service.TrainingService;

@Controller
public class TrainingController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private TrainingService trainingService;

	@Autowired
	private TrainingToTrainingDTO trainingToTrainingDTO;

	@Autowired
	private RoundService roundService;

	@Autowired
	private RoundToRoundDTO roundToRoundDTO;

	@Autowired
	private TrainingDTOtoTraining trainingDTOtoTraining;

	@Autowired
	private ExerciseInRoundDTOtoExerciseInRound exerciseInRoundDTOtoExerciseInRound;
	
	@Autowired
	private ExerciseInRoundService exerciseInRoundService;

	@RequestMapping(value = { "/trainingList" }, method = RequestMethod.GET)
	public String getTrainings(Model model) {
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingService.findAll()));
		return "training";
	}

	//Initialization of TrainingCreation page
	
	@RequestMapping(value = { "/trainingCreationHandler/{clientId}" }, method = RequestMethod.GET)
	public String createTraining(Model model, @PathVariable String clientId) {
		
		model.addAttribute("trainingDTO", createTraining(clientId));
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		
		return "trainingCreation";
	}
	
	@RequestMapping(value = { "/saveTraining"}, method = RequestMethod.POST)
	public String saveTraining(Model model, @ModelAttribute("trainingDTO") TrainingDTO trainingDTO ) {

		Training training = trainingService.save(trainingDTOtoTraining.convert(trainingDTO));

		return "redirect:/getTraining/"+training.getId();
	}

	//Adding a round in to Training
	@RequestMapping(value = { "/addRound"}, method = RequestMethod.POST)
	public String addRound(Model model, @RequestParam String id) {
	
		addRound(id);

		return "redirect:/getTraining/"+id;
	}

	// ADD EXERCISE IN ROUND
	
	@RequestMapping(value = { "/addExerciseInRound" }, method = RequestMethod.POST) 
	public String addExerciseInRound(Model model,
			@ModelAttribute("exerciseInRoundDTO") ExerciseInRoundDTO exerciseInRoundDTO, @RequestParam String id) {

		addExerciseInRound(exerciseInRoundDTO);
		
		return "redirect:/getTraining/"+id;
	}

	
	//DELETE EXERCISE IN ROUND
	
	@RequestMapping(value = {"/deleteExerciseInRound/{exerciseInRoundId}/{id}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String exerciseInRoundId, @PathVariable String id){

		exerciseInRoundService.delete(Long.parseLong(exerciseInRoundId));

		return "redirect:/getTraining/"+id;
	}

	//DELETE ROUND
	@RequestMapping(value = {"/deleteRound/{roundId}/{id}"}, method = RequestMethod.GET)
	public String deleteRound(Model model, @PathVariable String roundId, @PathVariable String id){
		
		roundService.delete(Long.parseLong(roundId));
		
		return "redirect:/getTraining/"+id;

	}
	
	private List<ExerciseDTO> getExercisesForModel(Long clientId){
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(clientId);
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		return exercisesForModal;
	}		
	
	private void addRound(String id) {
		Training training = trainingService.findOne(Long.parseLong(id));
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
	}
	
	private void addExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO) {

		ExerciseInRound exerciseInRound = exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO);
		exerciseInRoundService.save(exerciseInRound);
		Training training = exerciseInRound.getRound().getTraining();
		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : training.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
	}
	
	private TrainingDTO createTraining(String clientId) {
		Long clinetId = Long.parseLong(clientId);
		Client client = clientService.findOne(clinetId);
		
	    Date date = new Date();
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String strDate = formatter.format(date);

		//TODO napisati query da se vade samo treninzi tog klijenta
		// uzeti od njih max rednog broja i to je to
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			if (training.getClient().getId() == clinetId)
				max = Math.max(training.getNumberOfTrainings(), max);
		}
		TrainingDTO trainingDTO = new TrainingDTO();
		trainingDTO.setClient(client.getName());
		trainingDTO.setClientId(clientId);
		trainingDTO.setNumberOfTrainings((int) (max + 1));
		trainingDTO.setDate(strDate);
		
		return trainingDTO;
	}
	
	@RequestMapping(value = {"/getTraining/{id}"}, method = RequestMethod.GET)
	public String getTraining(Model model, @PathVariable String id){
	
		Training training = trainingService.findOne(Long.parseLong(id));
		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : training.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("id", id);
		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		model.addAttribute("exercises", getExercisesForModel(trainingService.findOne(Long.parseLong(id)).getClient().getId()));
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
		model.addAttribute("exercisesInRound", listExerciseInRound);
		model.addAttribute("exercises", getExercisesForModel(training.getClient().getId()));
		
		return "trainingCreation";
	}
	
}
