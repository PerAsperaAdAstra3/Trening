package training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

	@RequestMapping(value = { "/indexSelect" }, method = RequestMethod.GET)
	public String selectPage(@RequestParam String mode) {
		if ("exercise".equals(mode)) {
			String hiddenExerciseGroupId = "0";
			return "redirect:/exerciseList/"+hiddenExerciseGroupId;
		} else if ("exerciseGroup".equals(mode)) {
			return "redirect:/exerciseGroupList";
		} else if ("client".equals(mode)) {
			return "redirect:/clientList";
		}

		return "redirect:/trainingList";

	}
}
