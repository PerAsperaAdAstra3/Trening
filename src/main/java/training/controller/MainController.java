package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import training.converter.ClientToClientDTO;
import training.model.Client;
import training.service.ClientService;

@Controller
public class MainController {

//    @Value("${welcome.message}")
//    private String messageX;	
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ClientToClientDTO clietToClientDTO;
	
	@RequestMapping(value = { "/message" }, method = RequestMethod.GET)
    public String messagew(Model model) {
 
        model.addAttribute("messageX", "shiiiiiiiet");
 
        return "/message";
    }

/*	@RequestMapping(value = { "/client" }, method = RequestMethod.GET)
    public String clent(Model model) {
		List<Client> clientList = clientService.findAll();
        model.addAttribute("clients", clietToClientDTO.convert(clientList));
        return "/client";
    }*/
	
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Welcome to Training management application!");
        return "index";
    }
    
}
