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
	private ExerciseGroupToExerciseGroupDTO exerciseGroupToExerciseGroupDTO;
	
	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(value = {"/exerciseList"}, method=RequestMethod.GET)
	public String getExercises(Model model) {		
		System.out.println("EXERCISE LIST");
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupService.findAll()));
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.findAll()));
		model.addAttribute("hiddenExerciseGroupId", "0");
		return "exercise";
	}
	
	@RequestMapping(value = {"/addExercise"}, method = RequestMethod.POST)
	public String addExercise(Model model, @ModelAttribute("exerciseDTO") ExerciseDTO exerciseDTO, @RequestParam String mode, @RequestParam String hiddenExerciseGroupId) {
		int modeRedirect = 1;
		if("add".equals(mode)) {
			exerciseDTO.setId(null);
			exerciseService.save(exerciseDTOtoExercise.convert(exerciseDTO));
		} else {
			exerciseService.edit(exerciseDTO.getId() , exerciseDTOtoExercise.convert(exerciseDTO));
		}
		Long exerciseDTOGroupId = exerciseDTO.getExerciseGroupId();
		return "redirect:/retirectToExercisePage/"+modeRedirect+"/"+hiddenExerciseGroupId+"/"+exerciseDTOGroupId;
	}
	
	

	@RequestMapping(value = {"/deleteExercise/{id}/{hiddenExerciseGroupId}"}, method = RequestMethod.GET)
	public String delete(Model model, @PathVariable String id, @PathVariable String hiddenExerciseGroupId){
		Long exerciseDTOGroupId = 0l;
		int modeRedirect = 3;
		exerciseService.delete(Long.parseLong(id));
		return "redirect:/retirectToExercisePage/"+modeRedirect+"/"+hiddenExerciseGroupId+"/"+exerciseDTOGroupId;
	}
	
	@RequestMapping(value = {"/filterExercise"}, method = RequestMethod.POST)
	public String filterExercises(Model model, @ModelAttribute("exerciseDTOSearch") ExerciseDTO exerciseDTOSearch, @RequestParam String hiddenExerciseGroupId) {
		
		if(!hiddenExerciseGroupId.equals("")){
			List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
			exerciseList.add(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)));
			List<ExerciseDTO> exerciseListToFilter = exerciseToExerciseDTO.convert(exerciseService.filter( exerciseDTOtoExercise.convert(exerciseDTOSearch)));
			List<ExerciseDTO> exerciseListToFilterRefined = new ArrayList<ExerciseDTO>();
			for(ExerciseDTO exerciseDTO : exerciseListToFilter) {
				if(exerciseDTO.getExerciseGroupId().equals(Long.parseLong(hiddenExerciseGroupId))) {
					exerciseListToFilterRefined.add(exerciseDTO);
				}
			}
			return "exercise";
		}	
		int id = 3;
		return "redirect:/retirectToExercisePage/"+id;
	}
	
	@RequestMapping(value = {"/retirectToExercisePage/{modeRedirect}/{hiddenExerciseGroupId}/{exerciseDTOGroupId}"}, method = RequestMethod.GET)
	public String redirectToExercise(Model model, @PathVariable String modeRedirect, @PathVariable String hiddenExerciseGroupId, @PathVariable String exerciseDTOGroupId) {
		
		// DELETE
		System.out.println("###PATH VARIABLE### : "+modeRedirect);
		System.out.println("###PATH VARIABLE### : "+hiddenExerciseGroupId);
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		if(modeRedirect.equals("3")) {
	

		if(!hiddenExerciseGroupId.equals("0")){
 			List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
 			exerciseList.add(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)));
 			
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseList));

			// u sustini hiddenExerciseGroupId
 			List<ExerciseDTO> list = exerciseToExerciseDTO.convert(exerciseGroupService.findOne(Long.parseLong(hiddenExerciseGroupId)).getExercises());
			model.addAttribute("exercises", list);		
			model.addAttribute("hiddenExerciseGroupId", hiddenExerciseGroupId);

 			return "exercise";
 		}
		return "redirect:/exerciseList";
		}
		
		/*model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseList));
		model.addAttribute("exercises", exerciseListToFilterRefined);		
		model.addAttribute("hiddenExerciseGroupId", hiddenExerciseGroupId);
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.filter( exerciseDTOtoExercise.convert(exerciseDTOSearch))));

		

		
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseList));
		model.addAttribute("exercises", list);		
		model.addAttribute("hiddenExerciseGroupId", hiddenExerciseGroupId);
		*/
		// ADD EXERCISE
		
		if(!hiddenExerciseGroupId.equals("")){
 			List<ExerciseGroup> exerciseList = new ArrayList<ExerciseGroup>();
 			exerciseList.add(exerciseGroupService.findOne(Long.parseLong(exerciseDTOGroupId)));
			model.addAttribute("exerciseDTO", new ExerciseDTO());
			model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
			model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseList));
			model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseGroupService.findOne(Long.parseLong(exerciseDTOGroupId)).getExercises()));		
			model.addAttribute("hiddenExerciseGroupId", Long.parseLong(exerciseDTOGroupId));

 			return "exercise";
 		}
		return "redirect:/exerciseList";
	/*	
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseList));
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseGroupService.findOne(exerciseDTO.getExerciseGroupId()).getExercises()));		
		model.addAttribute("hiddenExerciseGroupId", exerciseDTO.getExerciseGroupId());
		
		// GET EXERCISES
		System.out.println("KRAJ");
		model.addAttribute("exerciseDTO", new ExerciseDTO());
		model.addAttribute("exerciseDTOSearch", new ExerciseDTO());
		model.addAttribute("exerciseGroups", exerciseGroupToExerciseGroupDTO.convert(exerciseGroupService.findAll()));
		model.addAttribute("exercises", exerciseToExerciseDTO.convert(exerciseService.findAll()));
		model.addAttribute("hiddenExerciseGroupId", "0");
		
		return "exercise";*/
		//return "redirect:/exerciseList";
	}
}
