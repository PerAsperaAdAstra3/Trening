package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import training.converter.ClientDTOtoClient;
import training.converter.ClientToClientDTO;
import training.dto.ClientDTO;
import training.model.Client;
import training.service.ClientService;

@Controller
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@Autowired
    ClientToClientDTO clientToClientDTO;
	
	@Autowired
	ClientDTOtoClient clientDTOtoClient;
	
	@RequestMapping(value = { "/clientList" }, method = RequestMethod.GET)
	public String getClients(Model model) {
		model.addAttribute("clientDTOSearch", new ClientDTO());
		model.addAttribute("clientDTO", new ClientDTO());
		model.addAttribute("clients", clientToClientDTO.convert(clientService.findAll()));
		return "client";
	}
	
	
	@RequestMapping(value = { "/addTrainingToClient" }, method = RequestMethod.GET)
	public String createTraining() {
		return "redirect:/clientList";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
		Client client = clientService.findOne(id);
		return new ResponseEntity<>(clientToClientDTO.convert(client), HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/addClient"} , method = RequestMethod.POST)
	public String addClient(Model model, @ModelAttribute("clientDTO") ClientDTO clientDTO, @RequestParam String mode){

		if("add".equals(mode)) {
			clientDTO.setId(null);
			clientService.save(clientDTOtoClient.convert(clientDTO));
		} else {
			clientService.edit(clientDTO.getId(), clientDTOtoClient.convert(clientDTO));
		}
		return "redirect:/clientList";
	}
	
	@RequestMapping(value = {"/deleteClient/{id}"}, method = RequestMethod.GET)
	public String deleteClient(@PathVariable String id) {
		clientService.delete(Long.parseLong(id));
		return "redirect:/clientList";
	}
}
