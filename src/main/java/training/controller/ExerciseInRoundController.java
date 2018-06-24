package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.ExerciseInRoundToExerciseInRoundDTO;
import training.dto.ExerciseInRoundDTO;
import training.model.Exercise;
import training.model.ExerciseInRound;
import training.service.ExerciseInRoundService;

@RestController
@RequestMapping(path = "api/exercisesInRound")
public class ExerciseInRoundController {
		
	@Autowired
	private ExerciseInRoundService exerciseInRoundService;
	
	@Autowired
	private ExerciseInRoundToExerciseInRoundDTO exerciseInRoundToExerciseInRoundDTO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ExerciseInRoundDTO>> getExercisesInRound(){
			List<ExerciseInRound> execInRound = exerciseInRoundService.findAll();
			System.out.println("Baaaaaa");
			System.out.println("SIZE : " + execInRound.size());
			System.out.println("First exerc :" + execInRound.get(0).getExercises().get(0).getName());
			System.out.println("Second exerc :" + execInRound.get(0).getExercises().get(1).getName());
		return new ResponseEntity<>(exerciseInRoundToExerciseInRoundDTO.convert(execInRound), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ExerciseInRoundDTO> getExerciseInRound(@PathVariable Long id){
			ExerciseInRound exerciseInRound =  exerciseInRoundService.findOne(id);
		return new ResponseEntity<>(exerciseInRoundToExerciseInRoundDTO.convert(exerciseInRound), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<ExerciseInRoundDTO> edit(@PathVariable Long id, ExerciseInRound exerciseInRound){
			ExerciseInRound exerciseInRoundOld = exerciseInRoundService.findOne(id);
			exerciseInRoundOld.setDifficulty(exerciseInRound.getDifficulty());
			exerciseInRoundOld.setNumberOfRepetitions(exerciseInRound.getNumberOfRepetitions());
			
			for(Exercise exercise : exerciseInRound.getExercises()) {
				exerciseInRoundOld.addExercise(exercise);
			}
			return new ResponseEntity<>(exerciseInRoundToExerciseInRoundDTO.convert(exerciseInRoundOld), HttpStatus.OK );
	}
	
	@RequestMapping(value = "/{id}", method =  RequestMethod.POST)
	public ResponseEntity<ExerciseInRoundDTO> delete(@PathVariable Long id){
		ExerciseInRound exerciseInRound = exerciseInRoundService.delete(id);
		return new ResponseEntity<>(exerciseInRoundToExerciseInRoundDTO.convert(exerciseInRound), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ExerciseInRound exerciseInRound, Errors errors){
		if(errors.hasErrors()) {
			System.out.println(errors.getAllErrors());
			return new ResponseEntity<String>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
		}
		ExerciseInRound newExerciseInRound = exerciseInRoundService.save(exerciseInRound);
		return new ResponseEntity<>( exerciseInRoundToExerciseInRoundDTO.convert(newExerciseInRound), HttpStatus.OK);
	}
}
