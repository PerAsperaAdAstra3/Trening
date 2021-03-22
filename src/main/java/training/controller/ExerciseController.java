package training.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import training.model.ExerciseGroup;
import training.service.ExerciseGroupService;
import training.service.ExerciseService;
import training.util.LoggingUtil;

@Controller
public class ExerciseController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private ExerciseDTOtoExercise exerciseDTOtoExercise;
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseGroupDTO;
	
	@Autowired
	private ExerciseService exerciseService;
	
	Logger logger = LoggerFactory.getLogger(ExerciseController.class);

	@RequestMapping(value = {"/exerciseList/{hiddenExerciseGroupId}"}, method=RequestMethod.GET)
	public String getExercises(Model model, @PathVariable String hiddenExerciseGroupId) {		
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("pageTitle", "Vežbe u sistemu : ");
		if(hiddenExerciseGroupId.equals("-1")|| hiddenExerciseGroupId == null) {
			model.addAttribute("hiddenExerciseGroupId", "-1");
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupService.findAll()));
			model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.findAll()));
		} else {
			ExerciseGroup exerciseGroup = exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId));
			model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseGroup.getExercises()));
			model.addAttribute("hiddenExerciseGroupId", hiddenExerciseGroupId);
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroup));
		}
		
		return "exercise";
	}
	
	@RequestMapping(value = {"/addExercise"}, method = RequestMethod.POST)
	public String addExercise(Model model, @ModelAttribute("exerciseDTO") ExerciseDTO exerciseDTO, @RequestParam String mode, @RequestParam String hiddenExerciseGroupId) {
		if("add".equals(mode)) {
			exerciseDTO.setId(null);
			exerciseService.save(exerciseDTOtoExercise.convert(exerciseDTO));
		} else {
			exerciseService.edit(exerciseDTO.getId() , exerciseDTOtoExercise.convert(exerciseDTO));
		}
		return "redirect:/exerciseList/"+hiddenExerciseGroupId;
	}
	
	@RequestMapping(value = {"/addExerciseTest"}, method = RequestMethod.POST)
	public String addExerciseTest(Model model, @ModelAttribute("exerciseDTO") ExerciseDTO exerciseDTO, @RequestParam String mode, @RequestParam String hiddenExerciseGroupId) {
		if("add".equals(mode)) {
			exerciseDTO.setId(null);
			exerciseService.save(exerciseDTOtoExercise.convert(exerciseDTO));
		} else {
			exerciseService.edit(exerciseDTO.getId() , exerciseDTOtoExercise.convert(exerciseDTO));
		}
		return "trainingCreation";
	}

	@RequestMapping(value = {"/deleteExercise/{id}/{hiddenExerciseGroupId}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String id, @PathVariable String hiddenExerciseGroupId){
		try {
			exerciseService.delete(Long.parseLong(id));
		} catch (NumberFormatException numberFormatException) {
			LoggingUtil.LoggingMethod(logger, numberFormatException);
		} catch (IllegalArgumentException illegalArgumentException) {
			LoggingUtil.LoggingMethod(logger, illegalArgumentException);
		} catch (Exception e) {
			LoggingUtil.LoggingMethod(logger, e);
		}
		return "redirect:/exerciseList/"+hiddenExerciseGroupId;
	}
	 
}
