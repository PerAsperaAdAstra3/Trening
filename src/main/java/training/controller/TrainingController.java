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
import training.repository.ExerciseGroupRepository;
import training.repository.ExerciseRepository;
import training.repository.OperatorRepository;
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
		Training training = trainingRepository.findOne(Long.parseLong(id));
		System.out.println(id);
		Long longId = Long.parseLong(id);
		System.out.println("Long parsed id : "+longId);
		trainingService.delete(Long.parseLong(id));
		
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingRepository.findAllByClientIdOrderByIdDesc(training.getClient().getId())));
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
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
	
	try {
		TrainingDTO trainingDTO = createTraining(clientId);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  Operator operator = operatorRepository.findOneByUserName(((UserDetails)principal).getUsername());
		  trainingDTO.setTrainingCreator(operator);
		  trainingDTO.setTrainingExecutor(operator);
		}
		trainingDTO.setStatus(TrainingStatusEnum.READY);
		Training training = trainingService.save(trainingDTOtoTraining.convert(trainingDTO));
		trainingDTO = trainingToTrainingDTO.convert(training);
		
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
				
		List<Operator> operators = operatorRepository.findAll();
		
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
		model.addAttribute("trainingListTest", tablesShowingOldTrainings(clientId, training.getId().toString()));
		model.addAttribute("trainingDTO", trainingDTO);
		model.addAttribute("operators", operators);
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("selectedRoundId", training.getRounds().get(0).getId());
		model.addAttribute("exercises", getExercisesForModel(training));
		model.addAttribute("circularYesNo", "Postojeće kombinacije");
		
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
	
	@RequestMapping(value = { "/circularTrainingCreationHandler/{clientId}" }, method = RequestMethod.GET)
	public String createCircularTraining(Model model, @PathVariable String clientId) {
	
	try {
		
		TrainingDTO trainingDTO = createTraining(clientId);
		Training training = trainingService.save(trainingDTOtoTraining.convert(trainingDTO));
		trainingDTO = trainingToTrainingDTO.convert(training);
		
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		
		List<Operator> operators = operatorRepository.findAll();
		
		model.addAttribute("roundsInTraining", roundToRoundDTO.convert(training.getRounds()));
		model.addAttribute("trainingListTest", tablesShowingOldTrainings(clientId, training.getId().toString()));
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
		return "trainingCreation";
	}
	//TODO pretvoriti u "Query method"
	private List<Training> tablesShowingOldTrainings(String clientId, String trainingId){
		Client client = clientService.findOne(Long.parseLong(clientId));
		List<Training> trainingList = client.getTrainingList();
		
		Training training = trainingService.findOne(Long.parseLong(trainingId));
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
	}
	
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
		List<Exercise> listOfAllExercises = exerciseRepository.getAllExercises();
		List<ExerciseDTO> exercisesForModal = exerciseToExerciseDTO.convert(listOfAllExercises);
		Map<Long,Integer> mapOfExercisesForClient = trainingService.exercisesLastTraining(training, listOfAllExercises);
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
			exerciseInRoundDTO.setExercise(exerciseService.findOne(exerciseInRoundDTO.getExerciseInRoundExerciseId()));
			exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		} else {
			exerciseInRound = exerciseInRoundService.edit(exerciseInRoundDTO.getId(), exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		} 
		
		return exerciseInRound.getRound().getId();
	}
	
	private TrainingDTO createTraining(String clientId) {
		Long clinetId = Long.parseLong(clientId);
		Client client = clientService.findOne(clinetId);
		
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
			TrainingStatusEnum trainingStatusEnumOld = trainingRepository.findOne(trainingDTO.getId()).getStatus();
			if(trainingStatusEnumOld != trainingDTO.getStatus() && trainingDTO.getStatus() == TrainingStatusEnum.DONE) {
				statusChangedToDone = true;
			}
			training =	trainingService.edit(trainingDTO.getId(), trainingDTOtoTraining.convert(trainingDTO));
		training.getId();
		}

		return training.getId();
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
			
			for(Operator operator :operators) {
				System.out.println(operator.getUserName());
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
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("hiddenExerciseGroupId", "-1");
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupService.findAll()));
			
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
	
	private Long getNumberOfTrainings(Long clinetId) {
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			if (training.getClient().getId() == clinetId)
				max = Math.max(training.getNumberOfTrainings(), max);
		}
		return max;
	}
	
}
