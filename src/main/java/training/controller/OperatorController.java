package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.OperatorDTOtoOperator;
import training.converter.OperatorToOperatorDTO;
import training.dto.OperatorDTO;
import training.service.OperatorService;

@Controller
public class OperatorController {

	@Autowired
	private OperatorToOperatorDTO operatorToOperatorDTO;

	@Autowired
	private OperatorService operatorService;

	@Autowired
	private OperatorDTOtoOperator operatorDTOtoOperator;
	
	@RequestMapping(value = { "/operatorList" }, method = RequestMethod.GET)
	public String getClients(Model model) {
		model.addAttribute("operatorDTOSearch", new OperatorDTO());
		model.addAttribute("operatorDTO", new OperatorDTO());
		model.addAttribute("operators", operatorToOperatorDTO.convert(operatorService.findAll()));
		return "operator";
	}
	
	@RequestMapping(value = {"/addOperator"}, method = RequestMethod.POST)
	public String addOperator(Model model, @ModelAttribute("operatorDTO") OperatorDTO operatorDTO, @RequestParam String mode){
		if("add".equals(mode)) {
			operatorDTO.setId(null);
			operatorService.save(operatorDTOtoOperator.convert(operatorDTO));
		} else {
			operatorService.edit(operatorDTO.getId(), operatorDTOtoOperator.convert(operatorDTO));
		}
		return "redirect:/operatorList";
	}
	
	@RequestMapping(value = {"/deleteOperator/{id}"}, method = RequestMethod.GET)
	public String deleteOperator(@PathVariable String id ) {
		operatorService.delete(Long.parseLong(id));
		return "redirect:/operatorList";
	}
}
