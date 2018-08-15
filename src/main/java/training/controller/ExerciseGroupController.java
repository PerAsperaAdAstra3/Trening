package training.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.ExerciseGroupDTOtoExerciseGroup;
import training.converter.ExerciseGroupToExerciseGroupDTO;
import training.converter.ExerciseToExerciseDTO;
import training.dto.ExerciseDTO;
import training.dto.ExerciseGroupDTO;
import training.model.ExerciseGroup;
import training.service.ExerciseGroupService;

@Controller
public class ExerciseGroupController {
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseGroupDTO;
	
	@Autowired
	private ExerciseGroupDTOtoExerciseGroup exerciseGroupDTOtoExerciseGroup;
	
	@Autowired
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseDTO;
	
	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@RequestMapping(value = {"/exerciseGroupList"}, method = RequestMethod.GET)
	public String listAll(Model model) {
		model.addAttribute("exerciseGroupDTO", new ExerciseGroupDTO());
		model.addAttribute("exerciseGroupDTOSearch", new ExerciseGroupDTO());
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupService.findAll()));
		return "exerciseGroup";
	}
	
	@RequestMapping(value = {"/deleteExerciseGroup/{id}"}, method = RequestMethod.GET)
	public String delete(@PathVariable String id){
		exerciseGroupService.delete(Long.parseLong(id));
		return "redirect:/exerciseGroupList";
	}

	
	@RequestMapping(value = {"/filterExcerInGroup/{id}"}, method = RequestMethod.GET)
	public String filterExcerInGroup(Model model, @PathVariable String id){
		List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
		exerciseList.add(exerciseGroupService.findOne(Long.parseLong(id)));
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseDTO.convert(exerciseList));
		model.addAttribute("exercises",  exerciseToExerciseDTO.convert(exerciseGroupService.findOne(Long.parseLong(id)).getExercises()));		
		model.addAttribute("hiddenExerciseGroupId", id) ;
		return "exercise";
	}
	
	@RequestMapping(value = {"/addExerciseGroup"} ,method = RequestMethod.POST)
	public String addExerciseGroup(Model model, @ModelAttribute("exerciseGroupDTO") ExerciseGroupDTO exerciseGroupDTO, @RequestParam String mode){
		
		if("add".equals(mode)) {
			exerciseGroupDTO.setId(null);
			exerciseGroupService.save(exerciseGroupDTOtoExerciseGroup.convert(exerciseGroupDTO));
		} else {
			exerciseGroupService.edit(exerciseGroupDTO.getId(), exerciseGroupDTOtoExerciseGroup.convert(exerciseGroupDTO));
		}
		return "redirect:/exerciseGroupList";
	}
	
	@RequestMapping(value = {"/filterExerciseGroup"}, method = RequestMethod.POST)
	public String filterExercisesGroup(Model model, @ModelAttribute("exerciseGroupDTOSearch") ExerciseGroupDTO exerciseGroupDTOSearch) {
		model.addAttribute("exerciseGroupDTO", new ExerciseDTO());
		model.addAttribute("exerciseGroupDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseGroups", exerciseGroupService.filter(exerciseGroupDTOtoExerciseGroup.convert(exerciseGroupDTOSearch)));
		return "exerciseGroup";
	}
}
