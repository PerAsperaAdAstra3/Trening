package training.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import training.converter.ClientToClientDTO;
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
	
	@RequestMapping(value = { "/trainingList" }, method = RequestMethod.GET)
	public String getTrainings(Model model) {
		model.addAttribute("trainingDTO", new TrainingDTO());
		model.addAttribute("trainings", trainingToTrainingDTO.convert(trainingService.findAll()));
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		model.addAttribute("idOfCopiedTraining","");
		model.addAttribute("idOfClientToCopyTo","");
		return "training";
	}
	
	@RequestMapping(value = { "/deleteTraining/{id}" }, method = RequestMethod.GET)
	public String getTrainings(Model model, @PathVariable String id) {
		trainingService.delete(Long.parseLong(id));
		return "redirect:/trainingList";
	}

	//Initialization of TrainingCreation page
	
	@RequestMapping(value = { "/trainingCreationHandler/{clientId}" }, method = RequestMethod.GET)
	public String createTraining(Model model, @PathVariable String clientId) {
		model.addAttribute("trainingDTO", createTraining(clientId));
		model.addAttribute("exerciseInRoundDTO", new ExerciseInRoundDTO());
		
		return "trainingCreation";
	}
	
	@RequestMapping(value = { "/saveTraining"}, method = RequestMethod.POST)
	public String saveTraining(Model model, @ModelAttribute("trainingDTO") TrainingDTO trainingDTO, @RequestParam String mode) {
		Long id = saveOrEditTraining(trainingDTO , mode);
		return "redirect:/getTraining/"+id;
	}

	//Adding a round in to Training
	@RequestMapping(value = { "/addRound"}, method = RequestMethod.POST)
	public String addRound(Model model, @RequestParam String id,
		RedirectAttributes redir) {
	
		Long newAddedRoundId = addRound(id);
		redir.addFlashAttribute("selectedRoundId", newAddedRoundId);
		return "redirect:/getTraining/"+id;
	}

	// ADD EXERCISE IN ROUND
	
	@RequestMapping(value = { "/addExerciseInRound" }, method = RequestMethod.POST) 
	public String addExerciseInRound(Model model,
			@ModelAttribute("exerciseInRoundDTO") ExerciseInRoundDTO exerciseInRoundDTO, @RequestParam String modeEIR,
			RedirectAttributes redir) {

		Long newAddedRoundId = addExerciseInRound(exerciseInRoundDTO, modeEIR);
		Long trainingId = roundService.findOne(exerciseInRoundDTO.getRoundId()).getTraining().getId();
		redir.addFlashAttribute("selectedRoundId", newAddedRoundId);
		
		return "redirect:/getTraining/"+trainingId;
	}

	
	//DELETE EXERCISE IN ROUND
	
	@RequestMapping(value = {"/deleteExerciseInRound/{exerciseInRoundId}/{id}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String exerciseInRoundId, @PathVariable String id,
			RedirectAttributes redir){

		ExerciseInRound exerciseInRound = exerciseInRoundService.delete(Long.parseLong(exerciseInRoundId));
		redir.addFlashAttribute("selectedRoundId", exerciseInRound.getRound().getId());

		return "redirect:/getTraining/"+id;
	}

	//DELETE ROUND
	@RequestMapping(value = {"/deleteRound/{roundId}/{id}"}, method = RequestMethod.GET)
	public String deleteRound(Model model, @PathVariable String roundId, @PathVariable String id){
	
		deleteRound(roundId);
		//TODO Select the previous round if it exists, the next one is this is the first round
		// nothing if this is the only round
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
	
	private Long addRound(String id) {
		Training training = trainingService.findOne(Long.parseLong(id));
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		return round.getId();
	}
	
	private Long addExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {

		ExerciseInRound exerciseInRound;//= exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO);
	//	exerciseInRoundService.save(exerciseInRound);

	if("add".equals(mode)) {
			exerciseInRoundDTO.setId(null);
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
		List<Training> trainingList = trainingService.findAll();
		Long max = 0l;
		for (Training training : trainingList) {
			if (training.getClient().getId() == clinetId)
				max = Math.max(training.getNumberOfTrainings(), max);
		}
		TrainingDTO trainingDTO = new TrainingDTO();
		trainingDTO.setClient(client.getName());
		trainingDTO.setClientFamilyName(client.getFamilyName());
		trainingDTO.setClientId(clientId);
		trainingDTO.setNumberOfTrainings((int) (max + 1));
		trainingDTO.setDate(strDate);
		
		return trainingDTO;
	}
	
	private void deleteRound(String id) {
		Training training = roundService.findOne(Long.parseLong(id)).getTraining();		
		int sequenceNumber = roundService.findOne(Long.parseLong(id)).getRoundSequenceNumber();
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
			training =	trainingService.edit(trainingDTO.getId(), trainingDTOtoTraining.convert(trainingDTO));
		training.getId();
		}

		return training.getId();
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
	
	@RequestMapping(value = {"/printPDF/{id}"}, method = RequestMethod.GET)
	public String pdf(Model model, @PathVariable String id) throws Exception{
		 Map<String,Object> data = new HashMap<String,Object>();
		 Training training = trainingService.findOne(Long.parseLong(id));
		 String imePrezime = training.getClient().getName() + " " + training.getClient().getFamilyName();
		 String date = training.getDate().toString();
		 String[] parts = date.split(" ");
		 
		 List<Round> rounds = training.getRounds();
		 
		 Map<Long,List<ExerciseInRound>> exercisesInRoundMap = new HashMap<Long,List<ExerciseInRound>>();
		 
		 for(Round roundIter : rounds) {
			 exercisesInRoundMap.put(new Long(roundIter.getRoundSequenceNumber()) , roundIter.getExerciseInRound());
		 }
		 
		 date = parts[0];
		 // Page Title/header
		 String trainingNumber = ""+training.getNumberOfTrainings();
		 data.put("name", imePrezime);
		 data.put("trainingNumber", trainingNumber);
		 data.put("date", date);
		 data.put("exercisesInRoundMap", exercisesInRoundMap);
		 		 
		 pdfGenaratorUtil.createPdf("PDFTemplate",data); 
		 return "redirect:/trainingList";
	}

	@RequestMapping(value = {"/copyTraining"}, method = RequestMethod.GET)
	public String copyTraining(Model model, @RequestParam String idOfClientToCopyTo, @RequestParam String idOfCopiedTraining){
		
		Training copiedTraining = trainingService.findOne(Long.parseLong(idOfCopiedTraining));
		Training trainingNew = new Training(copiedTraining);
		
		trainingService.save(copiedTraining);

		trainingNew.setClient(clientService.findOne(Long.parseLong(idOfClientToCopyTo)));

		for(Round round : copiedTraining.getRounds()) {
			Round newRound = new Round(round.getRoundSequenceNumber());
			roundService.save(newRound);
			newRound.setRoundSequenceNumber(round.getRoundSequenceNumber());
			
			for(ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
				ExerciseInRound newExerciseInRound = new ExerciseInRound();
				exerciseInRoundService.save(newExerciseInRound);
				newExerciseInRound.setDifficulty(exerciseInRound.getDifficulty());
				newExerciseInRound.setExerciseName(exerciseInRound.getExerciseName());
				newExerciseInRound.setNumberOfRepetitions(exerciseInRound.getNumberOfRepetitions());

				newExerciseInRound.setExerciseName(exerciseInRound.getExerciseName());
				newExerciseInRound.setExerciseId(exerciseInRound.getExerciseId());
				
				newExerciseInRound.setNote(exerciseInRound.getNote());

				newRound.setExerciseInRound(newExerciseInRound);
				exerciseInRoundService.save(newExerciseInRound);
			
//				exerciseInRound.setExerciseId( ); exerciseInRoundService
			}
					
			trainingNew.addRound(newRound);
			roundService.save(round);
		}
		
		trainingService.save(trainingNew);
		
		System.out.println("Id of client to copy : "+idOfClientToCopyTo);
		System.out.println("Id of copied training : "+idOfCopiedTraining);
		return "redirect:/getTraining/"+trainingNew.getId();
	}
}
