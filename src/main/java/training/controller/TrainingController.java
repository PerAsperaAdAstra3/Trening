package training.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import training.converter.ClientToClientDTO;
import training.converter.ExerciseGroupToExerciseGroupDTO;
import training.converter.ExerciseInRoundDTOtoExerciseInRound;
import training.converter.ExerciseToExerciseDTO;
import training.converter.RoundToRoundDTO;
import training.converter.TrainingDTOtoTraining;
import training.converter.TrainingToTrainingDTO;
import training.dto.ExerciseDTO;
import training.dto.ExerciseInRoundDTO;
import training.dto.TrainingDTO;
import training.enumerations.ClientPackageStateEnum;
import training.enumerations.Roles;
import training.enumerations.TrainingStatusEnum;
import training.model.Client;
import training.model.ClientPackage;
import training.model.Exercise;
import training.model.ExerciseInRound;
import training.model.Operator;
import training.model.Round;
import training.model.Training;
import training.repository.ClientRepository;
import training.repository.ExerciseGroupRepository;
import training.repository.ExerciseRepository;
import training.repository.OperatorRepository;
import training.repository.RoundRepository;
import training.repository.TrainingRepository;
import training.service.ClientService;
import training.service.ExerciseGroupService;
import training.service.ExerciseInRoundService;
import training.service.ExerciseService;
import training.service.RoundService;
import training.service.TrainingService;
import training.util.ExceptionMessageToStringList;
import training.util.LoggingUtil;
import training.util.PdfGenaratorUtil;

@Controller
public class TrainingController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ExerciseService exerciseService;

	@Autowired
	private TrainingService trainingService;

	@Autowired
	private TrainingToTrainingDTO trainingToTrainingDTO;

	@Autowired
	private RoundService roundService;
	
	@Autowired
	private RoundRepository roundRepository;

	@Autowired
	private RoundToRoundDTO roundToRoundDTO;

	@Autowired
	private TrainingDTOtoTraining trainingDTOtoTraining;

	@Autowired
	private ExerciseInRoundDTOtoExerciseInRound exerciseInRoundDTOtoExerciseInRound;
	
	@Autowired
	private ExerciseInRoundService exerciseInRoundService;

	@Autowired
	private PdfGenaratorUtil pdfGenaratorUtil;

	@Autowired
	private ClientToClientDTO clientToClientDTO;
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseGroupDTO;
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	@Autowired
	private ExerciseGroupRepository exerciseGroupRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private OperatorRepository operatorRepository;
	
	Logger logger = LoggerFactory.getLogger(TrainingController.class);
	
	private boolean statusChangedToDone = false;

	@RequestMapping(value = { "/trainingList/{isThereError}" }, method = RequestMethod.GET)
	public String getTrainings(Model model, @PathVariable int isThereError) {
	try {
		
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		model.addAttribute("idOfCopiedTraining","");
		model.addAttribute("idOfClientToCopyTo","");
		model.addAttribute("errorMessage",isThereError);
		model.addAttribute("pageTitle", "Treninzi u sistemu");
			
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		model.addAttribute("errorMessage", ExceptionMessageToStringList.createErrorMessageListForPrinting(e));
		return "errorPage";
	}
		
		return "training";
	}
	
	@RequestMapping(value = { "/deleteTraining/{id}" }, method = RequestMethod.GET)
	public String getTrainings(Model model, @PathVariable String id) {
	try {
		Training training = trainingRepository.findById(Long.parseLong(id)).get();
		Long longId = Long.parseLong(id);
		trainingService.delete(Long.parseLong(id));
		
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingRepository.findAllByClientIdOrderByIdDesc(training.getClient().getId())));
		//model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		model.addAttribute("clients", clientToClientDTO.convert(clientRepository.findAll()));
		model.addAttribute("clientId", training.getClient().getId());
		model.addAttribute("idOfCopiedTraining","");
		model.addAttribute("idOfClientToCopyTo","");
		
	} catch(NumberFormatException numberFormatException) {
		LoggingUtil.LoggingMethod(logger, numberFormatException);
	} catch(IllegalArgumentException illegalArgumentException) {
		LoggingUtil.LoggingMethod(logger, illegalArgumentException);
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
	    return "clientTrainingsInFolderB";
	}

	//Initialization of TrainingCreation page
	
	@RequestMapping(value = { "/trainingCreationHandler/{clientId}" }, method = RequestMethod.GET)
	public String createTraining(Model model, @PathVariable String clientId) {
		System.out.println("Start !!" + new Date());
		Training training = new Training();
	try {
		Operator operator = new Operator();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			 operator = operatorRepository.findOneByUserName(((UserDetails)principal).getUsername());
		}
		Long clinetId = Long.parseLong(clientId);
		Client client = clientRepository.findById(clinetId).get(); //getOne(clinetId);
		TrainingDTO trainingDTO = createTraining(client, operator, principal);
		trainingDTO.setCircularYN(false);
		/*if (principal instanceof UserDetails) { 
		  trainingDTO.setTrainingCreator(operator);
		  trainingDTO.setTrainingExecutor(operator);
		}*/
		trainingDTO.setStatus(TrainingStatusEnum.READY);
		//Training training = trainingService.save(trainingDTOtoTraining.convert(trainingDTO));
		training = trainingRepository.save(trainingDTOtoTraining.convertAlternate(trainingDTO, client)); //.convert(trainingDTO));
		trainingDTO = trainingToTrainingDTO.convert(training);
		
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		//roundService.save(round);
		roundRepository.save(round);
		//trainingService.save(training);
		trainingRepository.save(training);
		List<Operator> operators = operatorRepository.findByAuthoritiesNot(Roles.FRONTDESK.getNameText());
		
		for(Operator operatorIter : operators) {
			if(operatorIter.getAuthorities().equals(Roles.SUPERUSER.getNameText())) {
				
				operators.remove(operatorIter);
			}
		}
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
		model.addAttribute("trainingListTest", tablesShowingOldTrainings(client, training));
		model.addAttribute("trainingDTO", trainingDTO);
		model.addAttribute("operators", operators);
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("selectedRoundId", training.getRounds().get(0).getId());
		model.addAttribute("exercises", getExercisesForModel(training));
		model.addAttribute("circularYesNo", "Postojeće kombinacije");
		model.addAttribute("circularYN", false);
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		//return "trainingCreation";
		return "redirect:/changeTrainingChoice/"+ training.getId() +"/"+ false;
	}
	
	@RequestMapping(value = { "/circularTrainingCreationHandler/{clientId}" }, method = RequestMethod.GET)
	public String createCircularTraining(Model model, @PathVariable String clientId) {
		Training training = new Training();
	try {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Operator operator = new Operator();
		if (principal instanceof UserDetails) {
			 operator = operatorRepository.findOneByUserName(((UserDetails)principal).getUsername());
		}
	//	TrainingDTO trainingDTO = createTraining(clientId);
		Long clinetId = Long.parseLong(clientId);
		Client client = clientRepository.findById(clinetId).get(); //getOne(clinetId);
	//	client.toString();
		TrainingDTO trainingDTO = createTraining(client, operator, principal);
		trainingDTO.setCircularYN(true);
		training = trainingService.save(trainingDTOtoTraining.convertAlternate(trainingDTO, client)); //convert(trainingDTO));
		trainingDTO = trainingToTrainingDTO.convert(training);
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		
		List<Operator> operators = operatorRepository.findByAuthoritiesNot(Roles.FRONTDESK.getNameText());
		for(Operator operatorIter : operators) {
			if(operatorIter.getAuthorities().equals(Roles.SUPERUSER.getNameText())) {
				operators.remove(operatorIter);
			}
		}
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
		//TODO
		model.addAttribute("trainingListTest", tablesShowingOldTrainings(client, training)); //tablesShowingOldTrainings(clientId, training.getId().toString()));
		model.addAttribute("trainingDTO", trainingDTO);
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("selectedRoundId", training.getRounds().get(0).getId());
		model.addAttribute("exercises", getExercisesForModel(training));
		model.addAttribute("circularYesNo", "Kružni trening");
		model.addAttribute("circularYN", true);
		model.addAttribute("operators", operators);
		
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		//return "trainingCreation";
		return "redirect:/changeTrainingChoice/"+ training.getId() +"/"+ true;
	}
	//TODO pretvoriti u "Query method"
	private List<Training> tablesShowingOldTrainings(Client client, Training training){
	//	Client client = clientService.findOne(Long.parseLong(clientId));
		//Client client = clientRepository.findById(Long.parseLong(clientId)).get();
		List<Training> trainingList = client.getTrainingList();
		
	//	Training training = trainingService.findOne(Long.parseLong(trainingId));
	//	Training training = trainingRepository.findById(Long.parseLong(trainingId)).get();
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MM-uuuu" );
		for(int ii = 0; ii < trainingList.size() ; ii++) {
				if(trainingList.get(ii).getId() > training.getId()) {
				trainingList.remove(ii);
			}
		}
		
		//if(!trainingId.equals("") || trainingId != null) {
		if(training.getId() != null) {
			trainingList.remove(trainingList.size() - 1);
		}
		List<Training> trainingListTest = new ArrayList<Training>();
		
		if (trainingList.size() >= 1) {
			if(trainingList.size() <= 3) {
				for (int i = 0; i < trainingList.size(); i++) {
						trainingListTest.add(0, trainingList.get(i));
				}
			} else {
				for (int i = trainingList.size() - 3; i < trainingList.size(); i++) {
					trainingListTest.add(0, trainingList.get(i));
				}
			}
		}
		return trainingListTest;
	}
	
	private Map<Long, Integer> exercisesLastTraining(Training training, List<Exercise> allExercisesList) {
		List<Training> lastTrainings = trainingRepository.findTop10ByClientIdAndIdLessThanOrderByIdDesc(training.getClient().getId(), training.getId());
		List<Long> lastTrainingsId = new ArrayList<Long>();
		for(Training trainingx : lastTrainings) {
			lastTrainingsId.add(trainingx.getId());
		}

		Map<Long, Integer> mapExerciseIndexes = new HashMap<Long, Integer>();
		Map<Long, Integer> currentGroupIndexes = new HashMap<Long, Integer>();
		List<Long> allIdsInExerciseTable = new ArrayList<>();
		if(!lastTrainingsId.isEmpty()) {
			List<Object[]> rs = exerciseRepository.getAllExerciseGroupsFortrainingMULTIPLE(lastTrainingsId);

		for(Exercise exerciseTemp : allExercisesList) {
			allIdsInExerciseTable.add(exerciseTemp.getId());
		}

		Long trainingIdPrevious = -1l;
		List<Long> groupsInTraining = new ArrayList<Long>();
		for(int iter = 0; iter < rs.size(); iter++) {
			Long exercise_group = Long.parseLong(rs.get(iter)[0] + "");
			Long training_round = Long.parseLong(rs.get(iter)[1]+"");
			Long exerciseId = Long.parseLong(rs.get(iter)[2]+"");
			if(iter!=0) {
				trainingIdPrevious = Long.parseLong(rs.get(iter-1)[1]+"");
			} else {
				trainingIdPrevious = Long.parseLong(rs.get(iter)[1]+"");
			}
			
			if(iter!=0 && trainingIdPrevious.longValue() != training_round.longValue()) {
					groupsInTraining = new ArrayList<Long>();
			}
			Integer index = -1;
			if (allIdsInExerciseTable.contains(exerciseId)) {
				Long exerciseGroupId = exercise_group;
				
				if (groupsInTraining.contains(exerciseGroupId)) {
					index = currentGroupIndexes.get(exerciseGroupId);
				} else {
					groupsInTraining.add(exerciseGroupId);
					if (!currentGroupIndexes.containsKey(exerciseGroupId)) {
						index = 1;
					} else {
						index = currentGroupIndexes.get(exerciseGroupId) + 1;
					}
					currentGroupIndexes.put(exerciseGroupId, index);
				}
				if (!mapExerciseIndexes.containsKey(exerciseId))
					mapExerciseIndexes.put(exerciseId, index.intValue());
			}
		}
		}
		return mapExerciseIndexes;
	}
	
/*	private List<Training> tablesShowingOldTrainings(String clientId, String trainingId){
	//	Client client = clientService.findOne(Long.parseLong(clientId));
		Client client = clientRepository.findById(Long.parseLong(clientId)).get();
		List<Training> trainingList = client.getTrainingList();
		
	//	Training training = trainingService.findOne(Long.parseLong(trainingId));
		Training training = trainingRepository.findById(Long.parseLong(trainingId)).get();
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MM-uuuu" );
		for(int ii = 0; ii < trainingList.size() ; ii++) {
				if(trainingList.get(ii).getId() > training.getId()) {
				trainingList.remove(ii);
			}
		}
		
		if(!trainingId.equals("") || trainingId != null) {
			trainingList.remove(trainingList.size() - 1);
		}
		List<Training> trainingListTest = new ArrayList<Training>();
		
		if (trainingList.size() >= 1) {
			if(trainingList.size() <= 3) {
				for (int i = 0; i < trainingList.size(); i++) {
						trainingListTest.add(0, trainingList.get(i));
				}
			} else {
				for (int i = trainingList.size() - 3; i < trainingList.size(); i++) {
					trainingListTest.add(0, trainingList.get(i));
				}
			}
		}
		return trainingListTest;
	}*/
	
	private List<Training> tablesShowingOldTrainingsClientObject(Client client, Training trainingAttr){
		 //clientService.findOne(Long.parseLong(clientId));
		List<Training> trainingList = client.getTrainingList();
		Training training = trainingAttr;
		DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MM-uuuu" );
		for(int ii = 0; ii < trainingList.size() ; ii++) {
				if(trainingList.get(ii).getId() > training.getId()) {
				trainingList.remove(ii);
			}
		}
		
		if(trainingAttr.getId() != null) {
			trainingList.remove(trainingList.size() - 1);
		}
		List<Training> trainingListTest = new ArrayList<Training>();
		
		if (trainingList.size() >= 1) {
			if(trainingList.size() <= 3) {
				for (int i = 0; i < trainingList.size(); i++) {
						trainingListTest.add(0, trainingList.get(i));
				}
			} else {
				for (int i = trainingList.size() - 3; i < trainingList.size(); i++) {
					trainingListTest.add(0, trainingList.get(i));
				}
			}
		}
		return trainingListTest;
	}
	
	@RequestMapping(value = { "/saveTraining"}, method = RequestMethod.POST)
	public String saveTraining(Model model, @ModelAttribute("trainingDTO") TrainingDTO trainingDTO, @RequestParam String mode) {
		Long id = -1l;
	try {	
		 id = saveOrEditTraining(trainingDTO , mode);
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		
		return "redirect:/getTraining/"+id;
	}

	//Adding a round in to Training
	@RequestMapping(value = { "/addRound"}, method = RequestMethod.POST)
	public String addRound(Model model, @RequestParam String id,
		RedirectAttributes redir) {
	
		try {
			Long newRoundId = addRound(id);
			redir.addFlashAttribute("selectedRoundId", newRoundId);
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for(int i=0; i < trace.length; i++ ) {
				messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		
		return "redirect:/getTraining/"+id;
	}

	// ADD EXERCISE IN ROUND
	
	@RequestMapping(value = { "/addExerciseInRound" }, method = RequestMethod.POST) 
	public String addExerciseInRound(Model model,
			@ModelAttribute("exerciseInRoundDTO") ExerciseInRoundDTO exerciseInRoundDTO, @RequestParam String modeEIR,
			RedirectAttributes redir) {
		Long trainingId = -1l;
		try {
			Long newRoundId = addExerciseInRound(exerciseInRoundDTO, modeEIR);
			trainingId = roundService.findOne(exerciseInRoundDTO.getRoundId()).getTraining().getId();
			redir.addFlashAttribute("selectedRoundId", newRoundId);
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for(int i=0; i < trace.length; i++ ) {
				messageList.add(trace[i].toString());
			}
			model.addAttribute("errorMessage", messageList);
			return "errorPage";
		}
		return "redirect:/getTraining/"+trainingId;
	}
	
	//DELETE EXERCISE IN ROUND
	
	@RequestMapping(value = {"/deleteExerciseInRound/{exerciseInRoundId}/{id}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String exerciseInRoundId, @PathVariable String id,
			RedirectAttributes redir){
		try {
			ExerciseInRound exerciseInRound = exerciseInRoundService.delete(Long.parseLong(exerciseInRoundId));
			redir.addFlashAttribute("selectedRoundId", exerciseInRound.getRound().getId());
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for(int i=0; i < trace.length; i++ ) {
				messageList.add(trace[i].toString());
			}
			model.addAttribute("errorMessage", messageList);
			return "errorPage";
		}
		return "redirect:/getTraining/"+id;
	}

	//DELETE ROUND
	@RequestMapping(value = {"/deleteRound/{roundId}/{id}"}, method = RequestMethod.GET)
	public String deleteRound(Model model, @PathVariable String roundId, @PathVariable String id){
	try {
		deleteRound(roundId);
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		//TODO Select the previous round if it exists, the next one is this is the first round
		// nothing if this is the only round
		return "redirect:/getTraining/"+id;

	}
	
	private List<ExerciseDTO> getExercisesForModel(Training training){
		List<Exercise> listOfAllExercises = exerciseRepository.findAll(); //getAllExercises();
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(listOfAllExercises);
		Map<Long,Integer> mapOfExercisesForClient = /* trainingService. */ exercisesLastTraining(training, listOfAllExercises);
		for(ExerciseDTO exerciseDTO : exercisesForModal) {
			if(mapOfExercisesForClient.get(exerciseDTO.getId()) != null) {
				exerciseDTO.setColorCode(mapOfExercisesForClient.get(exerciseDTO.getId()));
			} else {
				exerciseDTO.setColorCode(60);
			}
		}
		return exercisesForModal;
	}
	
	private Long addRound(String id) {
		Training training = trainingService.findOne(Long.parseLong(id));
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		return round.getId();
	}
	
	private Long addExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {

		ExerciseInRound exerciseInRound;

	if("add".equals(mode)) {
			exerciseInRoundDTO.setId(null);
			exerciseInRoundDTO.setExercise(exerciseToExerciseDTO.convert(exerciseService.findOne(exerciseInRoundDTO.getExerciseInRoundExerciseId())));
			exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		} else {
			exerciseInRound = exerciseInRoundService.edit(exerciseInRoundDTO.getId(), exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		} 
		
		return exerciseInRound.getRound().getId();
	}
	
	private TrainingDTO createTraining(String clientId) {
		Long clinetId = Long.parseLong(clientId);
		Client client = clientRepository.findById(clinetId).get();	//getOne(clinetId); //clientService.findOne(clinetId);
		
	    Date date = new Date();
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String strDate = formatter.format(date);

		//TODO napisati query da se vade samo treninzi tog klijenta
		// uzeti od njih max rednog broja i to je to

		TrainingDTO trainingDTO = new TrainingDTO();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			Operator operator = operatorRepository.findOneByUserName(((UserDetails)principal).getUsername());
		    trainingDTO.setTrainingCreator(operator);
		    trainingDTO.setTrainingExecutor(operator);			  
		}
		trainingDTO.setClient(client.getName());
		trainingDTO.setClientFamilyName(client.getFamilyName());
		trainingDTO.setClientId(clientId);
		trainingDTO.setNumberOfTrainings((int) (getNumberOfTrainings(clinetId) + 1));
		trainingDTO.setDate(strDate);
		trainingDTO.setStatus(TrainingStatusEnum.READY);
		return trainingDTO;
	}
	
	private TrainingDTO createTraining(Client client, Operator operator, Object principal) {
		 //clientService.findOne(clinetId);
		
	    Date date = new Date();
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    String strDate = formatter.format(date);

		//TODO napisati query da se vade samo treninzi tog klijenta
		// uzeti od njih max rednog broja i to je to

		TrainingDTO trainingDTO = new TrainingDTO();
	//	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//if (principal instanceof UserDetails) {
		//	operator = operatorRepository.findOneByUserName(((UserDetails)principal).getUsername());
		    trainingDTO.setTrainingCreator(operator);
		    trainingDTO.setTrainingExecutor(operator);			  
	//	}
		trainingDTO.setClient(client.getName());
		trainingDTO.setClientFamilyName(client.getFamilyName());
		trainingDTO.setClientId(client.getId().toString());
		trainingDTO.setNumberOfTrainings((int) (getNumberOfTrainings(client.getId()) + 1));
		trainingDTO.setDate(strDate);
		trainingDTO.setStatus(TrainingStatusEnum.READY);
		return trainingDTO;
	}
	
	private void deleteRound(String id) {
		Round roundTemp = roundService.findOne(Long.parseLong(id));
		Training training = roundTemp.getTraining();		
		int sequenceNumber = roundTemp.getRoundSequenceNumber();
		roundService.delete(Long.parseLong(id));
		for(Round round : training.getRounds()) {
			if(round.getRoundSequenceNumber() > sequenceNumber) {
			round.setRoundSequenceNumber(round.getRoundSequenceNumber()-1);
			roundService.save(round);
			}
		}
	}
	
	private Long saveOrEditTraining(TrainingDTO trainingDTO, String mode) {
		Training training ;
		if("add".equals(mode)) {
			trainingDTO.setId(null);
			training = trainingService.save(trainingDTOtoTraining.convert(trainingDTO));
		} else {
			TrainingStatusEnum trainingStatusEnumOld = trainingRepository.findById(trainingDTO.getId()).get().getStatus();
			if(trainingStatusEnumOld != trainingDTO.getStatus() && trainingDTO.getStatus() == TrainingStatusEnum.DONE) {
				statusChangedToDone = true;
			}
			training =	trainingService.edit(trainingDTO.getId(), trainingDTOtoTraining.convert(trainingDTO));
		training.getId();
		}

		return training.getId();
	}

	@RequestMapping(value = {"/changeTrainingChoice/{id}/{circularYN}"}, method = RequestMethod.GET)
	public String getTraining(Model model, @PathVariable String id, @PathVariable Boolean circularYN){
		if(circularYN) {
			return "redirect:/getTrainingCircular/"+id;
		} else {
			return "redirect:/getTraining/"+id;
		}
	}
	
	@RequestMapping(value = {"/getTraining/{id}"}, method = RequestMethod.GET)
	public String getTraining(Model model, @PathVariable String id){
		long elapsed = 0l;
		try {
			Training training = trainingRepository.getOneTrainingById(Long.parseLong(id));
		
			List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
			for (Round roundIter : training.getRounds()) {
				listExerciseInRound.addAll(roundIter.getExerciseInRound());
			}
			
			List<Operator> operators = operatorRepository.findByAuthoritiesNot(Roles.FRONTDESK.getNameText()); //findAll();
			
			for(Operator operator : operators) {
				if(operator.getAuthorities().equals(Roles.SUPERUSER.getNameText())) {
					
					operators.remove(operator);
				}
			}
			
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("hiddenExerciseGroupId", "-1");
			
			model.addAttribute("operators", operators);
			
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupRepository.getExerciseGroupTest()));
			model.addAttribute("statusChangedToDone", statusChangedToDone);
			model.addAttribute("trainingListTest", tablesShowingOldTrainingsClientObject(training.getClient(), training));
			model.addAttribute("id", id);
			model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
			model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
			model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
			model.addAttribute("exercisesInRound", listExerciseInRound);
			model.addAttribute("exercises", getExercisesForModel(training));
		
		} catch(NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for(int i=0; i < trace.length; i++ ) {
				messageList.add(trace[i].toString());
			}
			model.addAttribute("errorMessage", messageList);
			return "errorPage";
		}
		statusChangedToDone = false;
		System.out.println("Training creation end !! " + new Date());
		return "trainingCreation";
	}
	
	// TODO move duplicate code to one method.
	
	@RequestMapping(value = {"/getTrainingCircular/{id}"}, method = RequestMethod.GET)
	public String getTrainingCircular(Model model, @PathVariable String id){
		try {
			Training training = trainingService.findOne(Long.parseLong(id));
			
			List<ExerciseInRound> listExerciseInRound = new ArrayList<ExerciseInRound>();
			for (Round roundIter : training.getRounds()) {
				listExerciseInRound.addAll(roundIter.getExerciseInRound());
			}
			
			List<Operator> operators = operatorRepository.findByAuthoritiesNot(Roles.FRONTDESK.getNameText()); //findAll();
			for(Operator operator : operators) {
				if(operator.getAuthorities().equals(Roles.SUPERUSER.getNameText())) {
					
					operators.remove(operator);
				}
			}
			
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("hiddenExerciseGroupId", "-1");
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupService.findAll()));
			
			model.addAttribute("operators", operators);
			
			model.addAttribute("trainingListTest", tablesShowingOldTrainingsClientObject(training.getClient(), training));
			model.addAttribute("id", id);
			model.addAttribute("trainingDTO", trainingToTrainingDTO.convert(training));
			model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
			model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
			model.addAttribute("exercisesInRound", listExerciseInRound);
			model.addAttribute("exercises", getExercisesForModel(training));
			model.addAttribute("circularYN", true);
		
		} catch (NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for(int i=0; i < trace.length; i++ ) {
				messageList.add(trace[i].toString());
			}
			model.addAttribute("errorMessage", messageList);
			return "errorPage";
		}
		
		return "trainingCreation";
	}
	
	@RequestMapping(value = {"/printPDF/{id}"}, method = RequestMethod.GET)
	public String pdf(Model model, @PathVariable String id) throws Exception{
		int isThereError = 0;
		try {
		 Map<String, Object> data = new HashMap<String, Object>();
		 Training training = trainingService.findOne(Long.parseLong(id));
		 String nameSurname = training.getClient().getName() + " " + training.getClient().getFamilyName();
		 String date = training.getDate().toString();
		 String[] parts = date.split(" ");
		 
		 List<Round> rounds = training.getRounds();
		 
		 Map<Long,List<ExerciseInRound>> exercisesInRoundMap = new HashMap<Long,List<ExerciseInRound>>();
		 		 
		 for(Round roundIter : rounds) {
			 for(ExerciseInRound exerciseInRound : roundIter.getExerciseInRound()) {
				 exerciseInRound.setDifficulty(filterLocalCharacters(exerciseInRound.getDifficulty()));
				 exerciseInRound.setExerciseName(filterLocalCharacters(exerciseInRound.getExercise().getName()));  //exerciseInRound.getExerciseName()));
				 exerciseInRound.setNote(filterLocalCharacters(exerciseInRound.getNote()));
				 exerciseInRound.setNumberOfRepetitions(filterLocalCharacters(exerciseInRound.getNumberOfRepetitions()));
			 }
			 
			 exercisesInRoundMap.put(new Long(roundIter.getRoundSequenceNumber()) , roundIter.getExerciseInRound());
		 }
		 
		 date = parts[0];
		 // Page Title/header
		 String trainingNumber = "" + training.getNumberOfTrainings();

		 nameSurname = filterLocalCharacters(nameSurname);
		 
		 data.put("name", nameSurname);
		 data.put("trainingNumber", trainingNumber);
		 data.put("date", date);
		 data.put("exercisesInRoundMap", exercisesInRoundMap);
		 		 		 
		 isThereError = pdfGenaratorUtil.createPdf("PDFTemplate",data); 
		 
	} catch (NumberFormatException numberFormatException) {
		LoggingUtil.LoggingMethod(logger, numberFormatException);
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		 
		 return "redirect:/trainingList/"+isThereError;
	}

	@RequestMapping(value = {"/printPDF/{id}/{pageSource}"}, method = RequestMethod.GET)
	public String pdfFromCreatePage(Model model, @PathVariable String id, @PathVariable String pageSource) throws Exception{
		int isThereError = 0;
		try {
		 Map<String, Object> data = new HashMap<String, Object>();
		 Training training = trainingService.findOne(Long.parseLong(id));
		 String nameSurname = training.getClient().getName() + " " + training.getClient().getFamilyName();
		 String date = training.getDate().toString();
		 String[] parts = date.split(" ");
		 
		 List<Round> rounds = training.getRounds();
		 
		 Map<Long,List<ExerciseInRound>> exercisesInRoundMap = new HashMap<Long,List<ExerciseInRound>>();
		 		 
		 for(Round roundIter : rounds) {
			 for(ExerciseInRound exerciseInRound : roundIter.getExerciseInRound()) {
				 exerciseInRound.setDifficulty(filterLocalCharacters(exerciseInRound.getDifficulty()));
				 exerciseInRound.setExerciseName(filterLocalCharacters(exerciseInRound.getExercise().getName())); //getExerciseName()));
				 exerciseInRound.setNote(filterLocalCharacters(exerciseInRound.getNote()));
				 exerciseInRound.setNumberOfRepetitions(filterLocalCharacters(exerciseInRound.getNumberOfRepetitions()));
			 }
			 
			 exercisesInRoundMap.put(new Long(roundIter.getRoundSequenceNumber()) , roundIter.getExerciseInRound());
		 }
		 
		 date = parts[0];
		 // Page Title/header
		 String trainingNumber = "" + training.getNumberOfTrainings();

		 nameSurname = filterLocalCharacters(nameSurname);
		 
		 data.put("name", nameSurname);
		 data.put("trainingNumber", trainingNumber);
		 data.put("date", date);
		 data.put("exercisesInRoundMap", exercisesInRoundMap);
		 		 		 
		 isThereError = pdfGenaratorUtil.createPdf("PDFTemplate",data); 
		 
	} catch (NumberFormatException numberFormatException) {
		LoggingUtil.LoggingMethod(logger, numberFormatException);
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		 
		 return "redirect:/trainingList/"+isThereError;
	}
	
	@RequestMapping(value = {"/copyTraining"}, method = RequestMethod.GET)
	public String copyTraining(Model model, @RequestParam String idOfClientToCopyTo, @RequestParam String idOfCopiedTraining){
		Long newTrainingId = -1l;
		try {
			
		Training copiedTraining = trainingService.findOne(Long.parseLong(idOfCopiedTraining));
		Training trainingNew = new Training(copiedTraining);
		
		trainingNew.setNumberOfTrainings((int)(getNumberOfTrainings(Long.parseLong(idOfClientToCopyTo)) + 1));
		
		trainingService.save(trainingNew);
		trainingNew.setClient(clientService.findOne(Long.parseLong(idOfClientToCopyTo)));

		trainingNew.setTrainingCreator(copiedTraining.getTrainingCreator());
		trainingNew.setTrainingExecutor(copiedTraining.getTrainingExecutor());
		trainingNew.setCircularYN(copiedTraining.isCircularYN()); //setTrainingExecutor(copiedTraining.getTrainingExecutor());
		trainingNew.setStatus(TrainingStatusEnum.READY);
		
		for(Round round : copiedTraining.getRounds()) {
			Round newRound = new Round(round.getRoundSequenceNumber());
			roundService.save(newRound);
			newRound.setRoundSequenceNumber(round.getRoundSequenceNumber());
			
			for(ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
				ExerciseInRound newExerciseInRound = new ExerciseInRound();
				exerciseInRoundService.save(newExerciseInRound);
				newExerciseInRound.setDifficulty(exerciseInRound.getDifficulty());
				newExerciseInRound.setExerciseName(exerciseInRound.getExercise().getName()); //getExerciseName());
				newExerciseInRound.setNumberOfRepetitions(exerciseInRound.getNumberOfRepetitions());

				newExerciseInRound.setExerciseName(exerciseInRound.getExercise().getName()); //(exerciseInRound.getExerciseName());
				newExerciseInRound.setExerciseId(exerciseInRound.getExerciseId());
				newExerciseInRound.setExercise(exerciseInRound.getExercise());
				
				newExerciseInRound.setNote(exerciseInRound.getNote());

				newRound.setExerciseInRound(newExerciseInRound);
				exerciseInRoundService.save(newExerciseInRound);
			}
					
			trainingNew.addRound(newRound);
			roundService.save(round);
		}
		
		trainingService.save(trainingNew);
		newTrainingId = trainingNew.getId();
		
	} catch(Exception e) {
		LoggingUtil.LoggingMethod(logger, e);
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		model.addAttribute("errorMessage", messageList);
		return "errorPage";
	}
		
		return "redirect:/getTraining/"+newTrainingId;
	}
	
	private String filterLocalCharacters(String nameSurname) {
		
		 nameSurname = nameSurname.replaceAll("ć", "c");
		 nameSurname = nameSurname.replaceAll("đ", "dj");
		 nameSurname = nameSurname.replaceAll("č", "c");

		 nameSurname = nameSurname.replaceAll("Ć", "C");
		 nameSurname = nameSurname.replaceAll("Đ", "Dj");
		 nameSurname = nameSurname.replaceAll("Č", "C");
		 
		return nameSurname;
	}
	
	private Long getNumberOfTrainings(Long clientId) {
	//	List<Training> trainingList = trainingService.findAll();
		
		Long max = trainingRepository.getClientsTrainings(clientId);
	/*	Long max = 0l;
		for (Training training : trainingList) {
			if (training.getClient().getId() == clinetId)
				max = Math.max(training.getNumberOfTrainings(), max);
		}*/
		return max;
	}
	
	/*private Long getNumberOfTrainings(Long clinetId) {
		System.out.println("Shiiiiit " + new Date());
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			if (training.getClient().getId() == clinetId)
				max = Math.max(training.getNumberOfTrainings(), max);
		}
		System.out.println("Shiiiiit " + new Date());
		return max;
	}*/
}
