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
import training.dto.ClientDTO;
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
public class TrainingControllerDirect {

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

	@RequestMapping(value = { "/trainingListDirect" }, method = RequestMethod.GET)
	public String getTrainings(Model model) {
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingService.findAll()));
		return "training";
	}
	
	//Initialization of TrainingCreation page
	
	@RequestMapping(value = { "/trainingCreationHandlerDirect" }, method = RequestMethod.GET)
	public String createTraining(Model model) {

	//	Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(Long.parseLong(id));
		
	        Date date = new Date();
	   
	        DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
	        System.out.println("~~~~~~~~~~~~~~~~~~~~~~Today is " + formatter.format(date));
		
		System.out.println("DATUM ::::::" + new Date());
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("trainingCreationDate", date);

		List<Round> roundList = new ArrayList<Round>();

		
		
		List<ExerciseInRound> exercisesInRound = new ArrayList<ExerciseInRound>();

		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			max = Math.max(training.getNumberOfTrainings(), max);
		}
		model.addAttribute("trainingNumberOfTraining", max + 1);
		
		List<Client> clientList = clientService.findAll();
		model.addAttribute("clientList", clientList);
		
		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", "");
		model.addAttribute("hiddenTrainingId", "NONE");

		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		
	/*	for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}*/
		
		model.addAttribute("exercises", exercisesForModal);
		model.addAttribute("exercisesInRound", exercisesInRound);

		model.addAttribute("clientOfTheTraining", new ClientDTO());
		model.addAttribute("roundsInTraining", roundList);

		return "trainingCreationDirect";
	}
	
	//Saving of TrainingCreation page
	@RequestMapping(value = { "/saveTrainingDirect"}, method = RequestMethod.POST)
	public String saveTraining(Model model, @ModelAttribute("clientOfTheTraining") ClientDTO clientOfTheTraining) {
		System.out.println("Usli smo u SAVE");
		System.out.println("+++++++++++++ ClientDOT ID : " + clientOfTheTraining.getId());
		
		TrainingDTO newTraining = new TrainingDTO();
		
		List<Client> clientList = clientService.findAll();
		model.addAttribute("clientList", clientList);
		
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			max = Math.max(training.getNumberOfTrainings(), max);
		}
		model.addAttribute("trainingNumberOfTraining", max + 1);
		
		Training trainingDefaultId = trainingDTOtoTraining.convert(newTraining);

		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(clientOfTheTraining.getId());
		
		trainingDefaultId.setNumberOfTrainings((int) (max+1));
		trainingDefaultId.setClient(clientService.findOne(clientOfTheTraining.getId()));
		trainingService.save(trainingDefaultId);
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());

		List<Round> roundList = new ArrayList<Round>();

		List<ExerciseInRound> exercisesInRound = new ArrayList<ExerciseInRound>();

		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", clientOfTheTraining.getId());
		model.addAttribute("hiddenTrainingId", trainingDefaultId.getId());

		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		
		model.addAttribute("exercises", exercisesForModal);
		model.addAttribute("exercisesInRound", exercisesInRound);

		model.addAttribute("clientOfTheTraining", clientToClientDTO.convert(clientService.findOne(clientOfTheTraining.getId())));
		model.addAttribute("roundsInTraining", roundList);

		return "trainingCreationDirect";
	}

	//Adding a round in to Training
	@RequestMapping(value = { "/addRoundDirect" }, method = RequestMethod.POST)
	public String addRound(Model model, @RequestParam String hiddenClientTrainingId,
			@RequestParam String hiddenTrainingId) {
		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		/*	List<Round> rounds = roundService.findAll();
		int max1 = 0;
		for (Round round : rounds) {
			max1 = Math.max(round.getRoundSequenceNumber(), max1);
		}*/

		List<Client> clientList = clientService.findAll();
		model.addAttribute("clientList", clientList);
		
		Round round = new Round(training.getRounds().size()+1);
		
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(Long.parseLong(hiddenClientTrainingId));
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		model.addAttribute("exercises", exercisesForModal);

		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", hiddenClientTrainingId);
		model.addAttribute("hiddenTrainingId", hiddenTrainingId);

		/// Testing something
		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));

		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training trainingIter : trainingList) {
			max = Math.max(trainingIter.getNumberOfTrainings(), max);
		}

		model.addAttribute("trainingNumberOfTraining", max + 1);

		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());

		model.addAttribute("clientOfTheTraining",
				clientToClientDTO.convert(clientService.findOne(Long.parseLong(hiddenClientTrainingId))));
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds())); 
		/// Testing something
		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		System.out.println("BROJ KRUGOVA U TRENINGU : " + training.getRounds().size());
		for (Round roundIter : training.getRounds()) {
			if (roundIter.getExerciseInRound() != null) {
				listExerciseInRound.addAll(roundIter.getExerciseInRound());
			}
		}
		model.addAttribute("exercisesInRound", round.getExerciseInRound());
																			

		return "trainingCreationDirect";
	}

	// add ExerciseInRound
	
	// ##################   EXERCIS IN ROUND #####################
	
	@RequestMapping(value = { "/addExerciseInRoundDirect" }, method = RequestMethod.POST) 
	
	// ADD
	
	public String addExerciseInRound(Model model,	
			@ModelAttribute("exerciseInRoundDTO") ExerciseInRoundDTO exerciseInRoundDTO,
			@RequestParam String roundInTraining, @RequestParam String hiddenClientTrainingId,
			@RequestParam String hiddenTrainingId) {

		Round roundFromExerInRound = roundService.findOne(Long.parseLong(roundInTraining));
		ExerciseInRound exerciseInRound = exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO);

		roundFromExerInRound.setExerciseInRound(exerciseInRound);

		exerciseInRoundService.save(exerciseInRound);

		List<Client> clientList = clientService.findAll();
		model.addAttribute("clientList", clientList);
		
		List<Round> rounds = roundService.findAll();
		int max1 = 0;
		for (Round round : rounds) {
			max1 = Math.max(round.getRoundSequenceNumber(), max1);
		}

		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", hiddenClientTrainingId);
		model.addAttribute("hiddenTrainingId", hiddenTrainingId);

		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training trainingIter : trainingList) {
			max = Math.max(trainingIter.getNumberOfTrainings(), max);
		}
		
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(Long.parseLong(hiddenClientTrainingId));
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		model.addAttribute("exercises", exercisesForModal);

		model.addAttribute("trainingNumberOfTraining", max + 1);

		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());

		model.addAttribute("clientOfTheTraining",
				clientToClientDTO.convert(clientService.findOne(Long.parseLong(hiddenClientTrainingId))));
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));

		Training trainingTemp = trainingService.findOne(Long.parseLong(hiddenTrainingId));

		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : trainingTemp.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("exercisesInRound", listExerciseInRound);

		return "trainingCreationDirect";
	}
	
	//DELETE
	
	@RequestMapping(value = {"/deleteExerciseInRoundDirect/{id}/{hiddenTrainingId}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String id, @PathVariable String hiddenTrainingId){
		
		System.out.println("###############");

		System.out.println(hiddenTrainingId);
	
		List<Client> clientList = clientService.findAll();
		model.addAttribute("clientList", clientList);
		
		exerciseInRoundService.delete(Long.parseLong(id));

		List<Round> rounds = roundService.findAll();
		int max1 = 0;
		for (Round round : rounds) {
			max1 = Math.max(round.getRoundSequenceNumber(), max1);
		}

		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient().getId());
		model.addAttribute("hiddenTrainingId", hiddenTrainingId);

		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training trainingIter : trainingList) {
			max = Math.max(trainingIter.getNumberOfTrainings(), max);
		}
		
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient().getId());
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		model.addAttribute("exercises", exercisesForModal);
		model.addAttribute("trainingNumberOfTraining", max + 1);
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("clientOfTheTraining",
				clientToClientDTO.convert(trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient()));
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));

		Training trainingTemp = trainingService.findOne(Long.parseLong(hiddenTrainingId));

		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : trainingTemp.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("exercisesInRound", listExerciseInRound);

		return "trainingCreationDirect";
	}

	//DELETE ROUND
	@RequestMapping(value = {"/deleteRoundDirect/{id}/{hiddenTrainingId}"}, method = RequestMethod.GET)
	public String deleteRound(Model model, @PathVariable String id, @PathVariable String hiddenTrainingId){
		
		System.out.println("###############");

		System.out.println(hiddenTrainingId);
	
		for(ExerciseInRound exerciseInRound : roundService.findOne(Long.parseLong(id)).getExerciseInRound()) {
			exerciseInRoundService.delete(exerciseInRound.getExecInRound_Id());
		}
		
		roundService.delete(Long.parseLong(id));

		List<Round> rounds = roundService.findAll();
		int max1 = 0;
		for (Round round : rounds) {
			max1 = Math.max(round.getRoundSequenceNumber(), max1);
		}

		List<Client> clientList = clientService.findAll();
		model.addAttribute("clientList", clientList);
		
		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient().getId());
		model.addAttribute("hiddenTrainingId", hiddenTrainingId);

		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training trainingIter : trainingList) {
			max = Math.max(trainingIter.getNumberOfTrainings(), max);
		}
		
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(exerciseService.findAll());
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient().getId());
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		model.addAttribute("exercises", exercisesForModal);

		model.addAttribute("trainingNumberOfTraining", max + 1);

		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());

		model.addAttribute("clientOfTheTraining",
				clientToClientDTO.convert(trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient()));
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));

		Training trainingTemp = trainingService.findOne(Long.parseLong(hiddenTrainingId));

		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : trainingTemp.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("exercisesInRound", listExerciseInRound);

		return "trainingCreationDirect";
	}
	
}
