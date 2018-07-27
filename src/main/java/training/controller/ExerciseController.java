package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.ExerciseDTOtoExercise;
import training.converter.ExerciseGroupToExerciseGroupDTO;
import training.converter.ExerciseToExerciseDTO;
import training.dto.ExerciseDTO;
import training.service.ExerciseGroupService;
import training.service.ExerciseService;

@Controller
public class ExerciseController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private ExerciseDTOtoExercise ExerciseDTOtoExercise;
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseDTO;
	
	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(value = {"/exerciseList"}, method=RequestMethod.GET)
	public String getExercises(Model model) {		
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseDTO.convert(exerciseGroupService.findAll()));
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.findAll()));
		model.addAttribute("isExecGroupFiltered", 1);
		return "exercise";
	}
	/*
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
		Exercise exercise = exerciseService.findOne(id);

		return new ResponseEntity<>(exerciseToExerciseDTO.convert(exercise), HttpStatus.OK);
	}
*/
	
	@RequestMapping(value = {"/addExercise"}, method = RequestMethod.POST)
	public String addExercise(Model model, @ModelAttribute("exerciseDTO") ExerciseDTO exerciseDTO, @RequestParam String mode) {
		System.out.println("DUGME : " +mode);
		System.out.println("ExerciseGroup ID " + exerciseDTO.getExerciseGroupId());
		if("add".equals(mode)) {
			System.out.println("Usli smo u add");
			exerciseDTO.setId(null);
			System.out.println("POSLE SET ID-a");
			exerciseService.save(ExerciseDTOtoExercise.convert(exerciseDTO));
			System.out.println("POSLE SAVE-a");
		} else {
			System.out.println("Usli smo u EDIT");
			exerciseService.edit(exerciseDTO.getId() , ExerciseDTOtoExercise.convert(exerciseDTO));
		}
		return "redirect:/exerciseList";
	}

	@RequestMapping(value = {"/deleteExercise/{id}"}, method = RequestMethod.GET)
	public String delete(@PathVariable String id){
		exerciseService.delete(Long.parseLong(id));
		return "redirect:/exerciseList";
	}
	
	@RequestMapping(value = {"/filterExercise"}, method = RequestMethod.POST)
	public String filterExercises(Model model, @ModelAttribute("exerciseDTOSearch") ExerciseDTO exerciseDTOSearch) {
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exercises", exerciseService.filter( ExerciseDTOtoExercise.convert(exerciseDTOSearch)));
		return "exercise";
	}
}
