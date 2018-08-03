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

import training.converter.ExerciseDTOtoExercise;
import training.converter.ExerciseGroupToExerciseGroupDTO;
import training.converter.ExerciseToExerciseDTO;
import training.dto.ExerciseDTO;
import training.model.Exercise;
import training.model.ExerciseGroup;
import training.service.ExerciseGroupService;
import training.service.ExerciseService;

@Controller
public class ExerciseController {

	@Autowired
	private ExerciseToExerciseDTO exerciseToExerciseDTO;
	
	@Autowired
	private ExerciseDTOtoExercise exerciseDTOtoExercise;
	
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
		model.addAttribute("hiddenExerciseGroupId", "") ;
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
	public String addExercise(Model model, @ModelAttribute("exerciseDTO") ExerciseDTO exerciseDTO, @RequestParam String mode, @RequestParam String hiddenExerciseGroupId) {

		System.out.println("Exercise group parameter : "+hiddenExerciseGroupId);
		if("add".equals(mode)) {
			exerciseDTO.setId(null);
			exerciseService.save(exerciseDTOtoExercise.convert(exerciseDTO));
		} else {
			System.out.println("Usli smo u EDIT");
			exerciseService.edit(exerciseDTO.getId() , exerciseDTOtoExercise.convert(exerciseDTO));
		}
		
		if(!hiddenExerciseGroupId.equals("")){
			List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
			exerciseList.add(exerciseGroupService.findOne(exerciseDTO.getExerciseGroupId()));
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseDTO.convert(exerciseList));
			model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseGroupService.findOne(exerciseDTO.getExerciseGroupId()).getExercises()));		
			model.addAttribute("hiddenExerciseGroupId", exerciseDTO.getExerciseGroupId());
			return "exercise";
			
	//		System.out.println("U add-u smo, usli smo u proveru da li smo dosli sa grupe");
	//		return "filterExcerInGroup/{id}"; // "redirect:/filterExcerInGroup/?id="+hiddenExerciseGroupId; ///filterExcerInGroup/{id}
		}
		
		return "redirect:/exerciseList";
	}
	
	

	@RequestMapping(value = {"/deleteExercise/{id}/{hiddenExerciseGroupId}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String id, @PathVariable String hiddenExerciseGroupId){
		
		System.out.println("+++ USLI SMO U DELETE +++");
		
		exerciseService.delete(Long.parseLong(id));

		if(!hiddenExerciseGroupId.equals("")){
			List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
			exerciseList.add(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)));
			
			System.out.println("+++ USLI SMO U DELETE +++");
			
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseDTO.convert(exerciseList));
			List<ExerciseDTO> list = exerciseToExerciseDTO.convert(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)).getExercises());
			model.addAttribute("exercises", list);		
			model.addAttribute("hiddenExerciseGroupId", hiddenExerciseGroupId);
			return "exercise";
			
	//		System.out.println("U add-u smo, usli smo u proveru da li smo dosli sa grupe");
	//		return "filterExcerInGroup/{id}"; // "redirect:/filterExcerInGroup/?id="+hiddenExerciseGroupId; ///filterExcerInGroup/{id}
		}
		return "redirect:/exerciseList";
	}
	
	@RequestMapping(value = {"/filterExercise"}, method = RequestMethod.POST)
	public String filterExercises(Model model, @ModelAttribute("exerciseDTOSearch") ExerciseDTO exerciseDTOSearch, @RequestParam String hiddenExerciseGroupId) {
		
		if(!hiddenExerciseGroupId.equals("")){
			List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
			exerciseList.add(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)));
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseDTO.convert(exerciseList));
	//		List<ExerciseDTO> exerciseListToFilter = exerciseToExerciseDTO.convert(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)).getExercises());
			List<ExerciseDTO> exerciseListToFilter = exerciseToExerciseDTO.convert(exerciseService.filter( exerciseDTOtoExercise.convert(exerciseDTOSearch)));
		//	exerciseListToFilter ;
			
			List<ExerciseDTO> exerciseListToFilterRefined = new ArrayList<ExerciseDTO>();
			for(ExerciseDTO exerciseDTO : exerciseListToFilter) {
				if(exerciseDTO.getExerciseGroupId().equals(Long.parseLong(hiddenExerciseGroupId))) {
					exerciseListToFilterRefined.add(exerciseDTO);
				}
			}

			model.addAttribute("exercises", exerciseListToFilterRefined);		
			
			model.addAttribute("hiddenExerciseGroupId", hiddenExerciseGroupId);
			return "exercise";
		}	
	//		System.out.println("U add-u smo, usli smo u proveru da li smo dosli sa grupe");
	//		return "filterExcerInGroup/{id}"; // "redirect:/filterExcerInGroup/?id="+hiddenExerciseGroupId; ///filterExcerInGroup/{id}
		
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.filter( exerciseDTOtoExercise.convert(exerciseDTOSearch))));
		return "exercise";
	}
}
