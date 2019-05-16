package training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

	@RequestMapping(value = { "/indexSelect" }, method = RequestMethod.GET)
	public String selectPage(@RequestParam String mode) {
		int isThereError = 0;
		if ("exercise".equals(mode)) {
			String hiddenExerciseGroupId = "-1";
			return "redirect:/exerciseList/"+hiddenExerciseGroupId;
		} else if ("exerciseGroup".equals(mode)) {
			return "redirect:/exerciseGroupList";
		} else if ("client".equals(mode)) {
			return "redirect:/clientList";
		} else if ("calendar".equals(mode)) {
			return "redirect:/calendar";
		} else if ("personalTrainer".equals(mode)) {
			return "redirect:/personalTrainer";
		}

		return "redirect:/trainingList/"+isThereError;

	}
}
