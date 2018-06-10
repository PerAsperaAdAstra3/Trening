package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.dto.ExerciseDTO;
import training.model.Exercise;
import training.service.ExerciseService;

@RestController
@RequestMapping(value = "/api/exercise")
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(value="/getExercises", method=RequestMethod.GET)
	public ResponseEntity<List<Exercise>> getExercises() {
		List<Exercise> exercises = exerciseService.findAll();
		return new ResponseEntity<>(exercises, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getExercise", method=RequestMethod.GET)
	public ResponseEntity<ExerciseDTO> getExercise() {
	//	List<Exercise> exercises = exerciseService.findAll();
		ExerciseDTO exerciseDTO = new ExerciseDTO("NJEE", "BUU");
	//	Exercise exercise = exercises.get(0);

		return new ResponseEntity<>(exerciseDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Exercise> addCity(@RequestBody Exercise exercise) {
		exerciseService.save(exercise);
		return new ResponseEntity<>(exercise, HttpStatus.OK);
	}

}
