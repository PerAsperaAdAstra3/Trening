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
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingService.findAll()));
		return "training";
	}

	//Initialization of TrainingCreation page
	
	@RequestMapping(value = { "/trainingCreationHandler/{id}" }, method = RequestMethod.GET)
	public String createTraining(Model model, @PathVariable String id) {

		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(Long.parseLong(id));
		
	        Date date = new Date();
	   
	        DateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
	        System.out.println("~~~~~~~~~~~~~~~~~~~~~~Today is " + formatter.format(date));
		
		System.out.println("DATUM ::::::" + new Date());
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("hiddenRoundInTraining", "");
		model.addAttribute("trainingCreationDate", date);

		List<Round> roundList = new ArrayList<Round>();

		List<ExerciseInRound> exercisesInRound = new ArrayList<ExerciseInRound>();

		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			max = Math.max(training.getNumberOfTrainings(), max);
		}
		model.addAttribute("trainingNumberOfTraining", max + 1);

		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", id);
		model.addAttribute("hiddenTrainingId", "NONE");

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

		model.addAttribute("clientOfTheTraining", clientToClientDTO.convert(clientService.findOne(Long.parseLong(id))));
		model.addAttribute("roundsInTraining", roundList);

		return "trainingCreation";
	}
	
	//Saving of TrainingCreation page
	@RequestMapping(value = { "/saveTraining"}, method = RequestMethod.POST)
	public String saveTraining(Model model,  @RequestParam String hiddenClientTrainingId) {
		System.out.println("Usli smo u SAVE");
		TrainingDTO newTraining = new TrainingDTO();

		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			max = Math.max(training.getNumberOfTrainings(), max);
		}
		model.addAttribute("trainingNumberOfTraining", max + 1);
		
		Training trainingDefaultId = trainingDTOtoTraining.convert(newTraining);

		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(Long.parseLong(hiddenClientTrainingId));
		
		trainingDefaultId.setNumberOfTrainings((int) (max+1));
		trainingDefaultId.setClient(clientService.findOne(Long.parseLong(hiddenClientTrainingId)));
		trainingService.save(trainingDefaultId);
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("hiddenRoundInTraining", "");

		List<Round> roundList = new ArrayList<Round>();

		List<ExerciseInRound> exercisesInRound = new ArrayList<ExerciseInRound>();

		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", hiddenClientTrainingId);
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

		model.addAttribute("clientOfTheTraining", clientToClientDTO.convert(clientService.findOne(Long.parseLong(hiddenClientTrainingId))));
		model.addAttribute("roundsInTraining", roundList);

		return "trainingCreation";
	}

	//Adding a round in to Training
	@RequestMapping(value = { "/addRound" }, method = RequestMethod.POST)
	public String addRound(Model model, @RequestParam String hiddenClientTrainingId,
			@RequestParam String hiddenTrainingId) {
		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		/*	List<Round> rounds = roundService.findAll();
		int max1 = 0;
		for (Round round : rounds) {
			max1 = Math.max(round.getRoundSequenceNumber(), max1);
		}*/

		Round round = new Round(training.getRounds().size()+1);
		
		training.addRound(round);
		model.addAttribute("hiddenRoundInTraining", "");
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

		model.addAttribute("roundDTO", new RoundDTO());
		/// Testing something
		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());

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
																			

		return "trainingCreation";
	}

	// add ExerciseInRound
	
	// ##################   EXERCIS IN ROUND #####################
	
	@RequestMapping(value = { "/addExerciseInRound" }, method = RequestMethod.POST) 
	
	// ADD
	
	public String addExerciseInRound(Model model,	
			@ModelAttribute("exerciseInRoundDTO") ExerciseInRoundDTO exerciseInRoundDTO) {

		ExerciseInRound exerciseInRound = exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO);
		exerciseInRoundService.save(exerciseInRound);
		Training training = exerciseInRound.getRound().getTraining();
		Long trainingId = training.getId();
		Long clientId = training.getClient().getId();

		List<Round> rounds = roundService.findAll();
		int max1 = 0;
		for (Round round : rounds) {
			max1 = Math.max(round.getRoundSequenceNumber(), max1);
		}

		model.addAttribute("roundDTO", new RoundDTO());

		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training trainingIter : trainingList) {
			max = Math.max(trainingIter.getNumberOfTrainings(), max);
		}
		
		
		model.addAttribute("exercises", getExercisesForModel(clientId));

		model.addAttribute("trainingNumberOfTraining", max + 1);

		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());

		model.addAttribute("clientOfTheTraining",
				clientToClientDTO.convert(training.getClient()));
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));

		model.addAttribute("hiddenRoundInTraining", "");


		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : training.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("exercisesInRound", listExerciseInRound);

		return "trainingCreation";
	}

	
	//DELETE
	
	@RequestMapping(value = {"/deleteExerciseInRound/{id}/{hiddenTrainingId}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String id, @PathVariable String hiddenTrainingId){
		
		System.out.println("###############");

		System.out.println(hiddenTrainingId);
	
		
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

		model.addAttribute("roundDTO", new RoundDTO());

		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
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

		model.addAttribute("hiddenRoundInTraining", "");

		Training trainingTemp = trainingService.findOne(Long.parseLong(hiddenTrainingId));

		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : trainingTemp.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("exercisesInRound", listExerciseInRound);

		return "trainingCreation";
	}

	//DELETE ROUND
	@RequestMapping(value = {"/deleteRound/{id}/{hiddenTrainingId}"}, method = RequestMethod.GET)
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

		Training training = trainingService.findOne(Long.parseLong(hiddenTrainingId));
		// Hidden ID's
		model.addAttribute("hiddenClientTrainingId", trainingService.findOne(Long.parseLong(hiddenTrainingId)).getClient().getId());
		model.addAttribute("hiddenTrainingId", hiddenTrainingId);

		model.addAttribute("roundDTO", new RoundDTO());

		model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
		model.addAttribute("trainingDTOSearch", new TrainingDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
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

		model.addAttribute("hiddenRoundInTraining", "");

		Training trainingTemp = trainingService.findOne(Long.parseLong(hiddenTrainingId));

		List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
		for (Round roundIter : trainingTemp.getRounds()) {
			listExerciseInRound.addAll(roundIter.getExerciseInRound());
		}
		model.addAttribute("exercisesInRound", listExerciseInRound);

		return "trainingCreation";
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
	
}
