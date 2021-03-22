package training.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import training.converter.ExerciseDTOtoExercise;
import training.converter.ExerciseInRoundDTOtoExerciseInRound;
import training.converter.ExerciseInRoundToExerciseInRoundDTO;
import training.converter.ExerciseToExerciseDTO;
import training.dto.ExerciseDTO;
import training.dto.ExerciseInRoundDTO;
import training.dto.ExerciseInRoundDTOAjax;
import training.dto.ExerciseInRoundDTOAjaxAddRound;
import training.dto.FilterLastExerciseInRoundDTOAjax;
import training.dto.MultipleExercisetoRoundDTO;
import training.dto.RoundDTOAjax;
import training.dto.TrainingDTO;
import training.model.Exercise;
import training.model.ExerciseInRound;
import training.model.Round;
import training.model.Training;
import training.repository.ExerciseInRoundCrudRepository;
import training.repository.ExerciseInRoundRepository;
import training.repository.ExerciseRepository;
import training.repository.RoundRepository;
import training.repository.TrainingRepository;
import training.service.ExerciseInRoundService;
import training.service.ExerciseService;
import training.service.RoundService;
import training.service.TrainingService;
import training.util.LoggingUtil;
import training.util.PdfGenaratorUtil;

@RestController
public class RestTrainingController {

	private Long newExerciseInRoundExecId = -1l;

	@Autowired
	private ExerciseInRoundRepository exerciseInRoundRepository;
	
	@Autowired
	private ExerciseInRoundCrudRepository exerciseInRoundRepositroy;
	
	@Autowired
	private RoundService roundService;
	
	@Autowired
	private RoundRepository roundRepository;

	@Autowired
	private ExerciseInRoundService exerciseInRoundService;

	@Autowired
	private ExerciseInRoundDTOtoExerciseInRound exerciseInRoundDTOtoExerciseInRound;

	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private PdfGenaratorUtil pdfGenaratorUtil;
	
	@Autowired
	private ExerciseDTOtoExercise exerciseDTOtoExercise;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	@Autowired
	private ExerciseInRoundToExerciseInRoundDTO exerciseInRoundToExerciseInRoundDTO;
	
	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	Logger logger = LoggerFactory.getLogger(RestTrainingController.class);
	
	// Add Exercise In Round #######################################################

	@PostMapping(value = { "/addMultipleExerciseInRound" })
	public ResponseEntity<?> addMultipleExerciseInRound(@Valid @RequestBody MultipleExercisetoRoundDTO multipleExercisetoRoundDTO) {
		JSONObject obj = new JSONObject();
		Training training = trainingService.findOne(multipleExercisetoRoundDTO.getTrainingId());
		int sizeDifference =  multipleExercisetoRoundDTO.getExerciseIDList().size() - training.getRounds().size();
		if(null != multipleExercisetoRoundDTO.getCircularRoundYN() && multipleExercisetoRoundDTO.getCircularRoundYN()) {
			Long newRoundId = -1l;
			List<ExerciseInRound> roundsList;
			List<ExerciseInRoundDTO> exercisesInRoundDTO = new ArrayList<ExerciseInRoundDTO>();
			
			List<ExerciseInRound> eirList =  exerciseInRoundRepository.previousExerciseOfSameTypeForClientList(training.getClient().getId().toString(), multipleExercisetoRoundDTO.getExerciseIDList());
			List<ExerciseInRound> exerciseInRoundLatestOnes = new ArrayList<ExerciseInRound>();
		
			List<Exercise> exerciseListForNames = exerciseRepository.findAllById(multipleExercisetoRoundDTO.getExerciseIDList());
			List<String> exerciseNames = new ArrayList<String>();
			
			for(int j=0; j< multipleExercisetoRoundDTO.getExerciseIDList().size(); j++) {
				for(int i =0; i < eirList.size(); i++) {
					if(eirList.get(i).getExerciseId().longValue() ==  multipleExercisetoRoundDTO.getExerciseIDList().get(j).longValue()) {
						exerciseInRoundLatestOnes.add(eirList.get(i));
						break;
					}
				}
				for(int z=0; z < exerciseListForNames.size() ; z++) {
					if(exerciseListForNames.get(z).getId().longValue() ==  multipleExercisetoRoundDTO.getExerciseIDList().get(j).longValue()) {
						exerciseNames.add(exerciseListForNames.get(z).getName());
					}
				}
			}	
			for(int j=0; j< multipleExercisetoRoundDTO.getExerciseIDList().size(); j++) {
				ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
				Long trainingId = -1l;

				exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseNames.get(j));
				exerciseInRoundDTO.setExerciseInRoundExerciseId(multipleExercisetoRoundDTO.getExerciseIDList().get(j));
				
				for(int k=0; k < exerciseInRoundLatestOnes.size(); k++) {
					if(exerciseInRoundLatestOnes.get(k).getExercise() != null) {
						if(multipleExercisetoRoundDTO.getExerciseIDList().get(j) == exerciseInRoundLatestOnes.get(k).getExercise().getId()) {
							autoNewExercisefieldsWithValuesFromPrevious(exerciseInRoundDTO, exerciseInRoundLatestOnes.get(k));
						}
					}
					if(exerciseInRoundLatestOnes.get(k).getExercise() == null && exerciseInRoundLatestOnes.get(k).getExerciseId() != null) {
						if(multipleExercisetoRoundDTO.getExerciseIDList().get(j) == exerciseInRoundLatestOnes.get(k).getExerciseId()) {
							autoNewExercisefieldsWithValuesFromPrevious(exerciseInRoundDTO, exerciseInRoundLatestOnes.get(k));
						}
					}
				}
				exerciseInRoundDTO.setRoundId(training.getRounds().get(0).getId());

				exercisesInRoundDTO.add(exerciseInRoundDTO);
				obj.put("exerciseExecId", newExerciseInRoundExecId);
			}

			roundsList = addExercisesInRound(exercisesInRoundDTO, "add");

			List<Long> roundIds = new ArrayList<Long>();
			for(ExerciseInRound exerciseInRound : roundsList) {
				roundIds.add(exerciseInRound.getRound().getId());
			}
			JSONArray jsonArray = new JSONArray();
			for(int i = 0; i < roundsList.size() ; i++) {

				ExerciseInRoundDTO exInRound = exerciseInRoundToExerciseInRoundDTO.convert(roundsList.get(i));
				JSONObject exerciseInRoundJSON = new JSONObject(exInRound);
				jsonArray.put(i, exerciseInRoundJSON);

			}

			obj.put("exerciseInRoundJSON", jsonArray);
			obj.put("roundId", roundsList.get(0).getRound().getId());
			obj.put("roundSecNumber", roundsList.get(0).getRound().getRoundSequenceNumber());
			obj.put("circularYN", "yes");
		}
		else {
			if (training.getRounds().size() < multipleExercisetoRoundDTO.getExerciseIDList().size() ) {
				/** Size difference - sizeDifference - shows how many new rounds need to be added when the number of exercises selected is greater then the number of rounds that currently exist in a training*/
				List<Long> trainingsIdList = new ArrayList<Long>();
				addRoundsCalc(training, sizeDifference);
				commonElementsAddMeltipleExercisesInRound(obj, multipleExercisetoRoundDTO, training, sizeDifference);
			} else if(multipleExercisetoRoundDTO.getExerciseIDList().size() == 1) {
				ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
				ExerciseInRound eir =  exerciseInRoundRepository.previousExerciseOfSameTypeForClient(training.getClient().getId().toString(), multipleExercisetoRoundDTO.getExerciseIDList().get(0).toString());					
				Long trainingId = -1l;
				exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseService.findOne(multipleExercisetoRoundDTO.getExerciseIDList().get(0)).getName());
				exerciseInRoundDTO.setExerciseInRoundExerciseId(multipleExercisetoRoundDTO.getExerciseIDList().get(0));
				if(multipleExercisetoRoundDTO.getHighlightedRoundId() == null) {
					exerciseInRoundDTO.setRoundId(training.getRounds().get(0).getId());
				} else {
					exerciseInRoundDTO.setRoundId(multipleExercisetoRoundDTO.getHighlightedRoundId());
				}
				autoNewExercisefieldsWithValuesFromPrevious(exerciseInRoundDTO, eir);
				ExerciseInRoundDTO exInRound = exerciseInRoundToExerciseInRoundDTO.convert(addExerciseInRoundReturn(exerciseInRoundDTO, "add"));

				JSONObject exerciseInRoundJSON = new JSONObject(exInRound);
				obj.put("exercInRound", exerciseInRoundJSON);
				obj.put("roundId", exInRound.getRoundId());
				obj.put("exerciseExecId", newExerciseInRoundExecId);
			} else {
				commonElementsAddMeltipleExercisesInRound(obj, multipleExercisetoRoundDTO, training, sizeDifference);
			}
		}
		Long trainingId = -1l;
		return ResponseEntity.ok(obj.toString());
	}
	
	private void commonElementsAddMeltipleExercisesInRound(JSONObject obj, MultipleExercisetoRoundDTO multipleExercisetoRoundDTO, Training training, int sizeDifference) {
		List<ExerciseInRound> roundsList;
		List<ExerciseInRoundDTO> exercisesInRoundDTO = new ArrayList<ExerciseInRoundDTO>();
		
		List<ExerciseInRound> eirList =  exerciseInRoundRepository.previousExerciseOfSameTypeForClientList(training.getClient().getId().toString(), multipleExercisetoRoundDTO.getExerciseIDList());
		List<ExerciseInRound> exerciseInRoundLatestOnes = new ArrayList<ExerciseInRound>();
		
		List<Exercise> exerciseListForNames = exerciseRepository.findAllById(multipleExercisetoRoundDTO.getExerciseIDList());
		List<String> exerciseNames = new ArrayList<String>();
		for(int j=0; j< multipleExercisetoRoundDTO.getExerciseIDList().size(); j++) {
			for(int i =0; i < eirList.size(); i++) {
				if(eirList.get(i).getExerciseId().longValue() == multipleExercisetoRoundDTO.getExerciseIDList().get(j).longValue()) {
					exerciseInRoundLatestOnes.add(eirList.get(i));
				}
			}

			for(int z=0; z < exerciseListForNames.size() ; z++) {
				if(exerciseListForNames.get(z).getId().longValue() == multipleExercisetoRoundDTO.getExerciseIDList().get(j).longValue()) {
					exerciseNames.add(exerciseListForNames.get(z).getName());
				}
			}
		}
		for(int j=0; j< multipleExercisetoRoundDTO.getExerciseIDList().size(); j++) {
			ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
			exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseNames.get(j));
			exerciseInRoundDTO.setExerciseInRoundExerciseId(multipleExercisetoRoundDTO.getExerciseIDList().get(j));
			exerciseInRoundDTO.setRoundId(training.getRounds().get(j).getId());
			for(int jj = 0; jj < exerciseInRoundLatestOnes.size(); jj++) {
				if(exerciseInRoundDTO.getExerciseInRoundExerciseId() == exerciseInRoundLatestOnes.get(jj).getExerciseId().longValue()) {
					autoNewExercisefieldsWithValuesFromPrevious(exerciseInRoundDTO, exerciseInRoundLatestOnes.get(jj));
				}
			}
			exercisesInRoundDTO.add(exerciseInRoundDTO);
			obj.put("exerciseExecId", newExerciseInRoundExecId);
		} 
		roundsList = addExercisesInRound(exercisesInRoundDTO, "add");
		List<Long> roundIds = new ArrayList<Long>();
		List<Integer> roundSecNumber = new ArrayList<Integer>();
		
		JSONArray jsonArray = new JSONArray();
				
		for(int i = 0; i < roundsList.size() ; i++) {
			
			ExerciseInRoundDTO exInRound = exerciseInRoundToExerciseInRoundDTO.convert(roundsList.get(i));
			JSONObject exerciseInRoundJSON = new JSONObject(exInRound);
			jsonArray.put(i, exerciseInRoundJSON);
			roundIds.add(roundsList.get(i).getRound().getId());
			roundSecNumber.add(roundsList.get(i).getRound().getRoundSequenceNumber());
		}
		obj.put("roundIds", roundIds);
		obj.put("roundSecNumber", roundSecNumber);
		obj.put("exerciseInRoundJSON", jsonArray);
		obj.put("sizeDifference", sizeDifference);
		obj.put("circularYN", "no");
	}
	
	private void autoNewExercisefieldsWithValuesFromPrevious(ExerciseInRoundDTO exerciseInRoundDTO, ExerciseInRound eir) {
		if(eir != null) {
			try {
				if(null != eir.getDifficulty()) {
					exerciseInRoundDTO.setDifficulty(eir.getDifficulty());
				}
			} catch(Exception e){
				LoggingUtil.LoggingMethod(logger, e);
			}
			try {
				if(null != eir.getNumberOfRepetitions()) {	
					exerciseInRoundDTO.setNumberOfRepetitions(eir.getNumberOfRepetitions());
				}
			} catch(Exception e){
				LoggingUtil.LoggingMethod(logger, e);
			}
			try {
				if(null != eir.getNote()) {
					exerciseInRoundDTO.setNote(eir.getNote()); 
				}
			} catch(Exception e){
				LoggingUtil.LoggingMethod(logger, e);
			}
		}
	}
	
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
			LoggingUtil.LoggingMethod(logger, e);
		}
		Long trainingId = -1l;
		try {
			ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
			exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			exerciseInRoundDTO.setExerciseInRoundExerciseId(Long.parseLong(exerciseInRoundDTOAjax.getExerciseInRoundExerciseId()));
			exerciseInRoundDTO.setNote(exerciseInRoundDTOAjax.getExerciseInRoundNote());
			exerciseInRoundDTO.setNumberOfRepetitions(exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			exerciseInRoundDTO.setDifficulty(exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
			exerciseInRoundDTO.setRoundId(Long.parseLong(exerciseInRoundDTOAjax.getRoundId()));
			Long newRoundId = addExerciseInRound(exerciseInRoundDTO, "add");
			obj.put("roundId", newRoundId);
			obj.put("exerciseExecId", newExerciseInRoundExecId);

		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return exceptionHandling(e);
		}
		return ResponseEntity.ok(obj.toString());
	}

	@PostMapping(value = { "/addExerciseInRoundAjaxAddRound" })
	public ResponseEntity<?> addExerciseInRoundAddRound(@Valid @RequestBody ExerciseInRoundDTOAjaxAddRound exerciseInRoundDTOAjaxAddRound) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("exerciseInRoundExerciseName", exerciseInRoundDTOAjaxAddRound.getExerciseInRoundExerciseName());
			obj.put("exerciseInRoundExerciseId", exerciseInRoundDTOAjaxAddRound.getExerciseInRoundExerciseId());
			obj.put("exerciseInRoundNote", exerciseInRoundDTOAjaxAddRound.getExerciseInRoundNote());
			obj.put("exerciseInRoundNumberOfRepetitions",
					exerciseInRoundDTOAjaxAddRound.getExerciseInRoundNumberOfRepetitions());
			obj.put("exerciseInRoundDifficulty", exerciseInRoundDTOAjaxAddRound.getExerciseInRoundDifficulty());
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		ExerciseDTO exerciseDTO = new ExerciseDTO();
		exerciseDTO.setName(exerciseInRoundDTOAjaxAddRound.getName());
		exerciseDTO.setDescription(exerciseInRoundDTOAjaxAddRound.getDescription());
		exerciseDTO.setExerciseGroupId(Long.parseLong(exerciseInRoundDTOAjaxAddRound.getExerciseGroupId()));
		exerciseDTO.setId(null);
		Exercise exercise = exerciseService.save(exerciseDTOtoExercise.convert(exerciseDTO));
		Long trainingId = -1l;
		try {
			ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
			exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseInRoundDTOAjaxAddRound.getExerciseInRoundExerciseName());
			exerciseInRoundDTO.setExerciseInRoundExerciseId(exercise.getId());
			obj.put("exerciseInRoundExerciseId", exercise.getId());
			exerciseInRoundDTO.setNote(exerciseInRoundDTOAjaxAddRound.getExerciseInRoundNote());
			exerciseInRoundDTO.setNumberOfRepetitions(exerciseInRoundDTOAjaxAddRound.getExerciseInRoundNumberOfRepetitions());
			exerciseInRoundDTO.setDifficulty(exerciseInRoundDTOAjaxAddRound.getExerciseInRoundDifficulty());
			exerciseInRoundDTO.setRoundId(Long.parseLong(exerciseInRoundDTOAjaxAddRound.getRoundId()));
			Long newRoundId = addExerciseInRound(exerciseInRoundDTO, "add");
			obj.put("roundId", newRoundId);
			obj.put("exerciseExecId", newExerciseInRoundExecId);
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return exceptionHandling(e);
		}
		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = { "/addExerciseInRoundFillFields" })
	public ResponseEntity<?> addExerciseInRoundFillFields(@Valid @RequestBody FilterLastExerciseInRoundDTOAjax filterLastExerciseInRoundDTOAjax) {
		String exerciseId = filterLastExerciseInRoundDTOAjax.getExerciseId();
		String clientId = filterLastExerciseInRoundDTOAjax.getClientId();
		ExerciseInRound eir =  exerciseInRoundRepository.previousExerciseOfSameTypeForClient(clientId, exerciseId);
		JSONObject obj = new JSONObject();
		//TODO add logging for error in future PR.
		try {
			if(eir != null) {
				if(eir.getExercise() != null) {
					obj.put("exerciseInRoundExerciseName", eir.getExercise().getName());
				} else {
					obj.put("exerciseInRoundExerciseName", eir.getExerciseName());
				}
				obj.put("exerciseInRoundDifficulty", eir.getDifficulty());
				obj.put("exerciseInRoundNote", eir.getNote());
				obj.put("exerciseInRoundNumberOfRepetitions", eir.getNumberOfRepetitions());
			}
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		return ResponseEntity.ok(obj.toString());
	}
	
	private Long addExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {
		ExerciseInRound exerciseInRound;
		exerciseInRoundDTO.setExercise(exerciseToExerciseDTO.convert(exerciseRepository.findById(exerciseInRoundDTO.getExerciseInRoundExerciseId()).get()));
		exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		newExerciseInRoundExecId = exerciseInRound.getExecInRound_Id();
		return exerciseInRound.getRound().getId();
	}
	
	private ExerciseInRound addExerciseInRoundReturn(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {
		ExerciseInRound exerciseInRound;
		exerciseInRoundDTO.setExercise(exerciseToExerciseDTO.convert(exerciseRepository.findById(exerciseInRoundDTO.getExerciseInRoundExerciseId()).get()));
		exerciseInRound = exerciseInRoundService.save(exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		newExerciseInRoundExecId = exerciseInRound.getExecInRound_Id();
		return exerciseInRound;
	}
	
	private List<ExerciseInRound> addExercisesInRound(List<ExerciseInRoundDTO> exercisesInRoundDTO, String mode) {
		List<ExerciseInRound> exercisesInRound = new ArrayList<ExerciseInRound>();
		List<Long> id = new ArrayList<Long>();
		for(int j =0; j < exercisesInRoundDTO.size(); j++) {
			id.add(exercisesInRoundDTO.get(j).getExerciseInRoundExerciseId());
		}
		List<Exercise> exercisesInList = exerciseRepository.getAllExerciseInIds(id);
		for(int i =0; i < exercisesInRoundDTO.size(); i++) {
			exercisesInRoundDTO.get(i).setExercise(exerciseToExerciseDTO.convert(exercisesInList.get(i)));
		}
		List<ExerciseInRound> exerciseInRoundList = exerciseInRoundDTOtoExerciseInRound.convert(exercisesInRoundDTO);
		exercisesInRound = (List<ExerciseInRound>) exerciseInRoundRepositroy.saveAll(exerciseInRoundList);
		return exercisesInRound;
	}
	
	private Long editExerciseInRound(ExerciseInRoundDTO exerciseInRoundDTO, String mode) {
		ExerciseInRound exerciseInRound;
		exerciseInRound = exerciseInRoundService.edit(exerciseInRoundDTO.getId(), exerciseInRoundDTOtoExerciseInRound.convert(exerciseInRoundDTO));
		return exerciseInRound.getRound().getId();
	}

	// changeExerciseInRoundAjax
	// #######################################################

	@PostMapping(value = { "/changeExerciseInRoundAjax" })
	public ResponseEntity<?> changeExerciseInRound(@Valid @RequestBody ExerciseInRoundDTOAjax exerciseInRoundDTOAjax) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("exerciseInRoundExerciseName", exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			obj.put("exerciseInRoundExerciseId", exerciseInRoundDTOAjax.getExerciseInRoundExerciseId());
			obj.put("exerciseInRoundNote", exerciseInRoundDTOAjax.getExerciseInRoundNote());
			obj.put("exerciseInRoundNumberOfRepetitions",
					exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			obj.put("exerciseInRoundDifficulty", exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		Long trainingId = -1l;
		try {
			ExerciseInRoundDTO exerciseInRoundDTO = new ExerciseInRoundDTO();
			exerciseInRoundDTO.setExerciseInRoundExerciseName(exerciseInRoundDTOAjax.getExerciseInRoundExerciseName());
			exerciseInRoundDTO.setExerciseInRoundExerciseId(Long.parseLong(exerciseInRoundDTOAjax.getExerciseInRoundExerciseId()));
			exerciseInRoundDTO.setNote(exerciseInRoundDTOAjax.getExerciseInRoundNote());
			exerciseInRoundDTO.setNumberOfRepetitions(exerciseInRoundDTOAjax.getExerciseInRoundNumberOfRepetitions());
			exerciseInRoundDTO.setDifficulty(exerciseInRoundDTOAjax.getExerciseInRoundDifficulty());
			exerciseInRoundDTO.setRoundId(Long.parseLong(exerciseInRoundDTOAjax.getRoundId()));
			exerciseInRoundDTO.setId(Long.parseLong(exerciseInRoundDTOAjax.getExerciseExecId()));
			Long newRoundId = editExerciseInRound(exerciseInRoundDTO, "edit");
			obj.put("roundId", newRoundId);
			obj.put("exerciseExecId", exerciseInRoundDTOAjax.getExerciseExecId());
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return	exceptionHandling(e);
		}
		return ResponseEntity.ok(obj.toString());
	}

	// Add Round video #######################################################

	// Adding a round in to Training
	@PostMapping(value = { "/addRoundAjax" })
	public ResponseEntity<?> addRound(@Valid @RequestBody RoundDTOAjax roundDTOAjax) {
		JSONObject obj = new JSONObject();
		try {
			Long newRoundId = addRoundCalc(roundDTOAjax.getId());
			obj.put("roundRoundSequenceNumber", roundService.findOne(newRoundId).getRoundSequenceNumber());
			obj.put("selectedRoundId", newRoundId);
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return exceptionHandling(e);
		}
		return ResponseEntity.ok(obj.toString());
	}

	/**Input is training ID - adds Round and returns round ID*/
	private Long addRoundCalc(String id) {
		Training training = trainingService.findOne(Long.parseLong(id));
		Round round = new Round(training.getRounds().size() + 1);
		training.addRound(round);
		roundService.save(round);
		trainingService.save(training);
		return round.getId();
	}
	
	/**Input is training ID - adds Round and returns round ID*/
	
	private void addRoundsCalc(Training training, int sizeDifference) {
		List<Round> roundList = new ArrayList<Round>();
		for(int i=0; i < sizeDifference; i++) {
			Round round = new Round(training.getRounds().size() + 1);
			training.addRound(round);
			roundList.add(round);	
		}
		roundRepository.saveAll(roundList);
		trainingRepository.save(training);
	}

	//DELETE ROUND
	@PostMapping(value = {"/deleteRoundAjax"})
	public ResponseEntity<?> deleteRoundAjax(@Valid @RequestBody RoundDTOAjax roundDTOAjax) {
		JSONObject obj = new JSONObject();
		try {
			deleteRound(roundDTOAjax.getId());
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return	exceptionHandling(e);
		}
		//TODO Select the previous round if it exists, the next one is this is the first round
		// nothing if this is the only round*/
		return ResponseEntity.ok(obj.toString());
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
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return exceptionHandling(e);
		}
		return ResponseEntity.ok(obj.toString());
	}
	
	@PostMapping(value = {"/printPDFAjax"})
	public ResponseEntity<?> pdfFromCreatePage(@Valid @RequestBody TrainingDTO trainingDTO) throws Exception{
		int isThereError = 0;
		JSONObject obj = new JSONObject();
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			Training training = trainingService.findOne(trainingDTO.getId());
			String nameSurname = training.getClient().getName() + " " + training.getClient().getFamilyName();
			String date = training.getDate().toString();
			String[] parts = date.split(" ");
			List<Round> rounds = training.getRounds();
			Map<Long,List<ExerciseInRound>> exercisesInRoundMap = new HashMap<Long,List<ExerciseInRound>>();
			for(Round roundIter : rounds) {
				for(ExerciseInRound exerciseInRound : roundIter.getExerciseInRound()) {
					if(null != exerciseInRound.getNumberOfRepetitions()) {
						exerciseInRound.setDifficulty(filterLocalCharacters(exerciseInRound.getDifficulty()));
					} else {
						exerciseInRound.setDifficulty(filterLocalCharacters(""));
					}
					exerciseInRound.setExerciseName(filterLocalCharacters(exerciseInRound.getExerciseName()));
					if(null != exerciseInRound.getNumberOfRepetitions()) {
						exerciseInRound.setNote(filterLocalCharacters(exerciseInRound.getNote()));
					} else {
						exerciseInRound.setNote(filterLocalCharacters(""));
					}
					if(null != exerciseInRound.getNumberOfRepetitions()) {
						exerciseInRound.setNumberOfRepetitions(filterLocalCharacters(exerciseInRound.getNumberOfRepetitions()));
					}else {
						exerciseInRound.setNumberOfRepetitions(filterLocalCharacters(""));
					}
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
			if(isThereError != 0) {
				return ResponseEntity.badRequest().body("Desila se greska!!!");
			}
		} catch(Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
			return exceptionHandling(e);
		}
		return ResponseEntity.ok(obj.toString());
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
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(StackTraceElement traceTemp : trace) {
			messageList.add(traceTemp.toString());
		}
		return ResponseEntity.badRequest().body("Desila se greska!!");
	}
}
