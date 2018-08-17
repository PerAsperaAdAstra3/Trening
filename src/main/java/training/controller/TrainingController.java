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

import training.converter.ClientToClientDTO;
import training.converter.ExerciseInRoundDTOtoExerciseInRound;
import training.converter.ExerciseToExerciseDTO;
import training.converter.RoundToRoundDTO;
import training.converter.TrainingDTOtoTraining;
import training.converter.TrainingToTrainingDTO;
import training.dto.ExerciseDTO;
import training.dto.ExerciseInRoundDTO;
import training.dto.RoundDTO;
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
	private ClientToClientDTO clientToClientDTO;

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
	
	@RequestMapping(value = { "/trainingCreationHandler/{id}" }, method = RequestMethod.GET)
	public String createTraining(Model model, @PathVariable String id) {
		
		Long clinetId = Long.parseLong(id);
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
		trainingDTO.setClientId(id);
		trainingDTO.setNumberOfTrainings((int) (max + 1));
		trainingDTO.setDate(strDate);
		model.addAttribute("trainingDTO", trainingDTO);
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		return "trainingCreation";
		//return "redirect:/redirectToTrainingCreation/"+hiddenTrainingId;
	}
	
	//Saving of TrainingCreation page
	@RequestMapping(value = { "/saveTraining"}, method = RequestMethod.POST)
	public String saveTraining(Model model, @ModelAttribute("trainingDTO") TrainingDTO trainingDTO ) {
		Training training = trainingDTOtoTraining.convert(trainingDTO);
		trainingService.save(training);
		return "redirect:/redirectToTrainingCreation/"+training.getId();
	}

	//Adding a round in to Training
	@RequestMapping(value = { "/addRound"}, method = RequestMethod.POST)
	public String addRound(Model model, @RequestParam String hiddenTrainingId) {
		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		/// Testing something
		
		return "redirect:/redirectToTrainingCreation/"+hiddenTrainingId;
	}

	// add ExerciseInRound
	
	// ##################   EXERCIS IN ROUND #####################
	
	@RequestMapping(value = { "/addExerciseInRound" }, method = RequestMethod.POST) 
	
	// ADD
	
	public String addExerciseInRound(Model model,	
			@ModelAttribute("exerciseInRoundDTO") ExerciseInRoundDTO exerciseInRoundDTO, @RequestParam String hiddenTrainingId) {

		System.out.println(exerciseInRoundDTO.getExerciseInRoundExerciseId());
		ExerciseInRound exerciseInRound = exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO);
		exerciseInRoundService.save(exerciseInRound);
		Training training = exerciseInRound.getRound().getTraining();
		Long clientId = training.getClient().getId();		
		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : training.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		
		model.addAttribute("exercises", getExercisesForModel(clientId));

		return "redirect:/redirectToTrainingCreation/"+hiddenTrainingId;
	//	return "trainingCreation";
	}

	
	//DELETE
	
	@RequestMapping(value = {"/deleteExerciseInRound/{id}/{hiddenTrainingId}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String id, @PathVariable String hiddenTrainingId){

		exerciseInRoundService.delete(Long.parseLong(id));

		return "redirect:/redirectToTrainingCreation/"+hiddenTrainingId;
	}

	//DELETE ROUND
	@RequestMapping(value = {"/deleteRound/{id}/{hiddenTrainingId}"}, method = RequestMethod.GET)
	public String deleteRound(Model model, @PathVariable String id, @PathVariable String hiddenTrainingId){
		
		for(ExerciseInRound exerciseInRound : roundService.findOne(Long.parseLong(id)).getExerciseInRound()) {
			exerciseInRoundService.delete(exerciseInRound.getExecInRound_Id());
		}
		roundService.delete(Long.parseLong(id));
		//return "trainingCreation";
		return "redirect:/redirectToTrainingCreation/"+hiddenTrainingId;
		//hiddenTrainingId
	}
	
	private List<ExerciseDTO> getExercisesForModel(Long clientId){
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(clientId);
		System.out.println(mapOfExercisesForClient);
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		return exercisesForModal;
	}		
	
	@RequestMapping(value = {"/redirectToTrainingCreation/{hiddenTrainingId}"}, method = RequestMethod.GET)
	public String redirectToTrainingCreation(Model model, @PathVariable String hiddenTrainingId){
	
		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : training.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		
		model.addAttribute("hiddenTrainingId", hiddenTrainingId);
		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		model.addAttribute("exercises", getExercisesForModel(trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient().getId()));
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
		model.addAttribute("hiddenTrainingId", training.getId());
		model.addAttribute("exercisesInRound", listExerciseInRound);
		
//		model.addAttribute("exercises", getExercisesForModel(clientId)); // Iz add exercisInRound
		//model.addAttribute("trainingNumberOfTraining", training.getNumberOfTrainings());
		
		model.addAttribute("exercises", getExercisesForModel(training.getClient().getId()));
		
		return "trainingCreation";
	}
	
}
