package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import training.converter.ExerciseToExerciseDTO;
import training.dto.ExerciseDTO;
import training.model.Exercise;
import training.service.ExerciseService;

//@RestController
//@RequestMapping(value = "/api/exercises")
@Controller
public class ExerciseController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(value = {"/exerciseList"}, method=RequestMethod.GET)
	public String getExercises(Model model) {
		List<Exercise> exercises = exerciseService.findAll();
		System.out.println("Exercise controller");
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exercises));
		//return new ResponseEntity<>( exerciseToExerciseDTO.convert(exercises), HttpStatus.OK);
		return "exercise";
	}
	/*
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
		Exercise exercise = exerciseService.findOne(id);

		return new ResponseEntity<>(exerciseToExerciseDTO.convert(exercise), HttpStatus.OK);
	}
*/
	@RequestMapping(value = "/addExercise", method = RequestMethod.GET)
	public String gotToAddExercise(Model model) {
		ExerciseDTO exerciseDTO = new ExerciseDTO();
		model.addAttribute("exerciseDTO", exerciseDTO);
		return "addExercise";
	}
	
	@RequestMapping(value = "/addExercise", method = RequestMethod.POST)
	public ResponseEntity<ExerciseDTO> addExercise(@RequestBody Exercise exercise) {
		exerciseService.save(exercise);
		return new ResponseEntity<>(exerciseToExerciseDTO.convert(exercise), HttpStatus.OK);
	}
/*
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
	*/
}
