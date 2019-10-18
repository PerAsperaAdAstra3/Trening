package training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String getTest(Model model) {
		System.out.println("Testing Testing u konzoli!");
		//model.addAttribute(attributeName, attributeValue)
		return "test";
	}
}
