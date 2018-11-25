package training.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import training.converter.ExerciseInRoundDTOtoExerciseInRound;
import training.dto.ExerciseInRoundDTO;
import training.dto.ExerciseInRoundDTOAjax;
import training.dto.RoundDTOAjax;
import training.dto.TrainingDTO;
import training.model.ExerciseInRound;
import training.model.Round;
import training.model.Training;
import training.service.ExerciseInRoundService;
import training.service.RoundService;
import training.service.TrainingService;
import training.util.PdfGenaratorUtil;

@RestController
public class RestTrainingController {

	private Long newExerciseInRoundExecId = -1l;

	@Autowired
	private RoundService roundService;

	@Autowired
	private ExerciseInRoundService exerciseInRoundService;

	@Autowired
	private ExerciseInRoundDTOtoExerciseInRound exerciseInRoundDTOtoExerciseInRound;

	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private PdfGenaratorUtil pdfGenaratorUtil;

	// Add Exercise In Round #######################################################

	@PostMapping(value = { "/addExerciseInRoundAjax" })
	public ResponseEntity<?> addExerciseInRound(@Valid @RequestBody ExerciseInRoundDTOAjax exerciseInRoundDTOAjax) {

		JSONObject obj = new JSONObject();
		try {
			obj.put("exerciseInRoundExerciseName", exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			obj.put("exerciseInRoundExerciseId", exerciseInRoundDTOAjax.getExerciseInRoundExerciseId());
			obj.put("exerciseInRoundNote", exerciseInRoundDTOAjax.getExerciseInRoundNote());
			obj.put("exerciseInRoundNumberOfRepetitions",
					exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			obj.put("exerciseInRoundDifficulty", exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
		} catch (Exception e) {

		}

		Long trainingId = -1l;
		try {
			ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();

			exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			exerciseInRoundDTO.setExerciseInRoundExerciseId(
					Long.parseLong(exerciseInRoundDTOAjax.getExerciseInRoundExerciseId()));
			exerciseInRoundDTO.setNote(exerciseInRoundDTOAjax.getExerciseInRoundNote());
			exerciseInRoundDTO.setNumberOfRepetitions(exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			exerciseInRoundDTO.setDifficulty(exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
			exerciseInRoundDTO.setRoundId(Long.parseLong(exerciseInRoundDTOAjax.getRoundId()));

			Long newAddedRoundId = addExerciseInRound(exerciseInRoundDTO, "add");
			trainingId = roundService.findOne(exerciseInRoundDTO.getRoundId()).getTraining().getId();
			obj.put("roundId", newAddedRoundId);
			obj.put("exerciseExecId", newExerciseInRoundExecId);

		} catch (Exception e) {
			e.printStackTrace();
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				messageList.add(trace[i].toString());
			}
			return ResponseEntity.badRequest().body("Desila se greska!!"); // "errorPage";
		}
		return ResponseEntity.ok(obj.toString());

	}

	private Long addExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {

		ExerciseInRound exerciseInRound;

		exerciseInRoundDTO.setId(null);
		exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		newExerciseInRoundExecId = exerciseInRound.getExecInRound_Id();
		return exerciseInRound.getRound().getId();
	}
	
	private Long editExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {

		ExerciseInRound exerciseInRound;
		
		System.out.println("exerciseInRoundDTO : " + exerciseInRoundDTO.getId());
		System.out.println("exerciseInRoundDTO convert : " + exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		
		exerciseInRound = exerciseInRoundService.edit(exerciseInRoundDTO.getId(), exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		return exerciseInRound.getRound().getId();
	}

	// changeExerciseInRoundAjax
	// #######################################################

	@PostMapping(value = { "/changeExerciseInRoundAjax" })
	public ResponseEntity<?> changeExerciseInRound(@Valid @RequestBody ExerciseInRoundDTOAjax exerciseInRoundDTOAjax) {
		System.out.println("Change Exercise In Round Ajax");
		System.out.println(exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
		System.out.println(exerciseInRoundDTOAjax.getExerciseInRoundExerciseId());
		System.out.println(exerciseInRoundDTOAjax.getExerciseInRoundNote());
		System.out.println(exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
		System.out.println(exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
		System.out.println(exerciseInRoundDTOAjax.getRoundId());
		System.out.println(exerciseInRoundDTOAjax.getExerciseExecId());
		System.out.println("Change Exercise In Round Ajax");
		JSONObject obj = new JSONObject();
		try {
			obj.put("exerciseInRoundExerciseName", exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			obj.put("exerciseInRoundExerciseId", exerciseInRoundDTOAjax.getExerciseInRoundExerciseId());
			obj.put("exerciseInRoundNote", exerciseInRoundDTOAjax.getExerciseInRoundNote());
			obj.put("exerciseInRoundNumberOfRepetitions",
					exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			obj.put("exerciseInRoundDifficulty", exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
		} catch (Exception e) {

		}

		Long trainingId = -1l;
		try {
			ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();

			exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			exerciseInRoundDTO.setExerciseInRoundExerciseId(
					Long.parseLong(exerciseInRoundDTOAjax.getExerciseInRoundExerciseId()));
			exerciseInRoundDTO.setNote(exerciseInRoundDTOAjax.getExerciseInRoundNote());
			exerciseInRoundDTO.setNumberOfRepetitions(exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			exerciseInRoundDTO.setDifficulty(exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
			exerciseInRoundDTO.setRoundId(Long.parseLong(exerciseInRoundDTOAjax.getRoundId()));
			exerciseInRoundDTO.setId(Long.parseLong(exerciseInRoundDTOAjax.getExerciseExecId()));

			Long newAddedRoundId = editExerciseInRound(exerciseInRoundDTO, "edit");
			trainingId = roundService.findOne(exerciseInRoundDTO.getRoundId()).getTraining().getId();
			obj.put("roundId", newAddedRoundId);
			obj.put("exerciseExecId", exerciseInRoundDTOAjax.getExerciseExecId()); //trainingId); //newExerciseInRoundExecId); // exerciseExecId
			// redir.addFlashAttribute("selectedRoundId", newAddedRoundId);

		} catch (Exception e) {
			e.printStackTrace();
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				messageList.add(trace[i].toString());
			}
			return ResponseEntity.badRequest().body("Desila se greska!!"); // "errorPage";
		}
		return ResponseEntity.ok(obj.toString());

	}

	private Long changeExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {

		ExerciseInRound exerciseInRound;

		exerciseInRoundDTO.setId(null);
		exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		newExerciseInRoundExecId = exerciseInRound.getExecInRound_Id();

		return exerciseInRound.getRound().getId();
	}

	// Add Round video #######################################################

	// public ResponseEntity<?> add
	// Adding a round in to Training
	@PostMapping(value = { "/addRoundAjax" })
	public ResponseEntity<?> addRound(@Valid @RequestBody RoundDTOAjax roundDTOAjax) {

		JSONObject obj = new JSONObject();

		try {

			Long newAddedRoundId = addRoundCalc(roundDTOAjax.getId());
			// redir.addFlashAttribute("selectedRoundId", newAddedRoundId);

			obj.put("roundRoundSequenceNumber", roundService.findOne(newAddedRoundId).getRoundSequenceNumber());
			obj.put("selectedRoundId", newAddedRoundId);

		} catch (Exception e) {
			e.printStackTrace();
			List<String> messageList = new ArrayList<>();
			StackTraceElement[] trace = e.getStackTrace();
			for (int i = 0; i < trace.length; i++) {
				messageList.add(trace[i].toString());
			}
			return ResponseEntity.badRequest().body("Desila se greska!!"); // "errorPage";
		}
		return ResponseEntity.ok(obj.toString());
	}

	private Long addRoundCalc(String id) {
		Training training = trainingService.findOne(Long.parseLong(id));
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		return round.getId();
	}

	//DELETE ROUND
	@PostMapping(value = {"/deleteRoundAjax"})
	public ResponseEntity<?> deleteRoundAjax(@Valid @RequestBody RoundDTOAjax roundDTOAjax) { //Model model, @PathVariable String roundId, @PathVariable String id){
		System.out.println("U Delete Round-u smo!!!");
		JSONObject obj = new JSONObject();
		//obj.put("selectedRoundId", "asdada");
	try {
		deleteRound(roundDTOAjax.getId());//roundId);		
	} catch(Exception e) {
		e.printStackTrace();
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
				
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
	//	model.addAttribute("errorMessage", messageList);
		return ResponseEntity.badRequest().body("Desila se greska!!"); //return "errorPage";
	}
		//TODO Select the previous round if it exists, the next one is this is the first round
		// nothing if this is the only round*/
		return ResponseEntity.ok(obj.toString());
	//	return "redirect:/getTraining/"+roundDTOAjax.getId();
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
	
	//DELETE EXERCISE IN ROUND
	
	@PostMapping(value = {"/deleteExerciseInRoundAjax"})
	public ResponseEntity<?> delete(@Valid @RequestBody RoundDTOAjax roundDTOAjax){
		JSONObject obj = new JSONObject();
		try {
			
		ExerciseInRound exerciseInRound = exerciseInRoundService.delete(Long.parseLong(roundDTOAjax.getId()));
//		redir.addFlashAttribute("selectedRoundId", exerciseInRound.getRound().getId());

		} catch(Exception e) {
				e.printStackTrace();
				List<String> messageList = new ArrayList<>();
				StackTraceElement[] trace = e.getStackTrace();
				for(int i=0; i < trace.length; i++ ) {
				messageList.add(trace[i].toString());
			}
	//		model.addAttribute("errorMessage", messageList);
			//return "errorPage";
		}
		 return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = {"/printPDFAjax"})
	public ResponseEntity<?> pdfFromCreatePage(@Valid @RequestBody TrainingDTO trainingDTO) throws Exception{
		int isThereError = 0;
		JSONObject obj = new JSONObject();
		System.out.println("Training DTO get id : " + trainingDTO.getId());
		try {
		 Map<String,Object> data = new HashMap<String,Object>();
		 Training training = trainingService.findOne(trainingDTO.getId());
		 String imePrezime = training.getClient().getName() + " " + training.getClient().getFamilyName();
		 String date = training.getDate().toString();
		 String[] parts = date.split(" ");
		 
		 List<Round> rounds = training.getRounds();
		 
		 Map<Long,List<ExerciseInRound>> exercisesInRoundMap = new HashMap<Long,List<ExerciseInRound>>();
		 		 
		 for(Round roundIter : rounds) {
			 for(ExerciseInRound exerciseInRound : roundIter.getExerciseInRound()) {
				 exerciseInRound.setDifficulty(filterLocalCharacters(exerciseInRound.getDifficulty()));
				 exerciseInRound.setExerciseName(filterLocalCharacters(exerciseInRound.getExerciseName()));
				 exerciseInRound.setNote(filterLocalCharacters(exerciseInRound.getNote()));
				 exerciseInRound.setNumberOfRepetitions(filterLocalCharacters(exerciseInRound.getNumberOfRepetitions()));
			 }
			 
			 exercisesInRoundMap.put(new Long(roundIter.getRoundSequenceNumber()) , roundIter.getExerciseInRound());
		 }
		 
		 date = parts[0];
		 // Page Title/header
		 String trainingNumber = ""+training.getNumberOfTrainings();

		 imePrezime = filterLocalCharacters(imePrezime);
		 
		 data.put("name", imePrezime);
		 data.put("trainingNumber", trainingNumber);
		 data.put("date", date);
		 data.put("exercisesInRoundMap", exercisesInRoundMap);
		 		 		 
		 isThereError = pdfGenaratorUtil.createPdf("PDFTemplate",data); 
		 
	} catch(Exception e) {
		e.printStackTrace();
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		return ResponseEntity.badRequest().body("Desila se greska!!"); 
	//	model.addAttribute("errorMessage", messageList);
	//	return "errorPage";
	}
		 
	//	 return "redirect:/trainingList/"+isThereError;
		return ResponseEntity.ok(obj.toString());
	}

	private String filterLocalCharacters(String imePrezime) {
		
		 imePrezime = imePrezime.replaceAll("ć", "c");
		 imePrezime = imePrezime.replaceAll("đ", "dj");
		 imePrezime = imePrezime.replaceAll("č", "c");

		 imePrezime = imePrezime.replaceAll("Ć", "C");
		 imePrezime = imePrezime.replaceAll("Đ", "Dj");
		 imePrezime = imePrezime.replaceAll("Č", "C");
		 
		return imePrezime;
	}
	
}
