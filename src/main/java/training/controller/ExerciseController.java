package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.ExerciseToExerciseDTO;
import training.dto.ExerciseDTO;
import training.model.Exercise;
import training.service.ExerciseService;

@RestController
@RequestMapping(value = "/api/exercises")
public class ExerciseController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ExerciseDTO>> getExercises() {
		List<Exercise> exercises = exerciseService.findAll();
		return new ResponseEntity<>( exerciseToExerciseDTO.convert(exercises), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
		Exercise exercise = exerciseService.findOne(id);

		return new ResponseEntity<>(exerciseToExerciseDTO.convert(exercise), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ExerciseDTO> addCity(@RequestBody Exercise exercise) {
		exerciseService.save(exercise);
		return new ResponseEntity<>(exerciseToExerciseDTO.convert(exercise), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ExerciseDTO> delete(@PathVariable Long id){
		Exercise exerciseDeleted = exerciseService.delete(id);
		return new ResponseEntity<>(exerciseToExerciseDTO.convert(exerciseDeleted), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<ExerciseDTO> edit(@PathVariable Long id, @RequestBody Exercise exercise){
		Exercise newExercise = exerciseService.edit(id, exercise);
		return new ResponseEntity<>(exerciseToExerciseDTO.convert(newExercise),HttpStatus.OK);
	}
	
}
