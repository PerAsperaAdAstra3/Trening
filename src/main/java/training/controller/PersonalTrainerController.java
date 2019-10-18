package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.PersonalTrainerDTOtoPersonalTrainer;
import training.converter.PersonalTrainerToPersonalTrainerDTO;
import training.dto.PersonalTrainerDTO;
import training.service.PersonalTrainerService;

@Controller
public class PersonalTrainerController {
	
	@Autowired
	PersonalTrainerService personalTrainerService;
	
	@Autowired
	PersonalTrainerDTOtoPersonalTrainer personalTrainerDTOtoPersonalTrainer;
	
	@Autowired
	PersonalTrainerToPersonalTrainerDTO personalTrainerToPersonalTrainerDTO;
	
	@RequestMapping(value = "/personalTrainer", method = RequestMethod.GET)
	public String getPersonalTrainer(Model model) {
		model.addAttribute("personalTrainerDTOSearch", new PersonalTrainerDTO());
		model.addAttribute("personalTrainerDTO", new PersonalTrainerDTO());
		model.addAttribute("personalTrainers", personalTrainerService.findAll());
		return "personalTrainer";
	}
	
	@RequestMapping(value = "/addPersonalTrainers", method = RequestMethod.POST)
	public String addPersonalTrainer(Model model, @ModelAttribute("personalTrainerDTO") PersonalTrainerDTO personalTrainerDTO, @RequestParam String mode) {
		
		if("add".equals(mode)) {
			personalTrainerService.save(personalTrainerDTOtoPersonalTrainer.convert(personalTrainerDTO));
		} else {
			personalTrainerService.edit(personalTrainerDTO.getId(), personalTrainerDTOtoPersonalTrainer.convert(personalTrainerDTO));
		}
		return "redirect:/personalTrainer";
	}
	
	@RequestMapping(value = {"/deletePersonalTrainer/{id}"}, method = RequestMethod.GET)
	public String deletePersonalTrainer(@PathVariable String id) {
		personalTrainerService.delete(Long.parseLong(id));
		return "redirect:/personalTrainer";
	}
}