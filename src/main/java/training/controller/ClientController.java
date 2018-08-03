package training.controller;

import java.util.List;

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
	
/*	@Value("${message}")
	private String message = "rtetetertr";
*/
	@RequestMapping(value = { "/clientList" }, method = RequestMethod.GET)
	public String getClients(Model model) {
		List<Client> clients = clientService.findAll();
		ClientDTO clientDTO = new ClientDTO();
		ClientDTO clientDTOSearch = new ClientDTO();
		model.addAttribute("clientDTOSearch", clientDTOSearch);
		model.addAttribute("clientDTO", clientDTO);
		model.addAttribute("clients", clientToClientDTO.convert(clients));
		System.out.println("Client list je tu");
		return "/client";
	}
	
	
	@RequestMapping(value = { "/addTrainingToClient" }, method = RequestMethod.GET)
	public String createTraining() {
		return "redirect:/clientList";
	}
	
/*	@RequestMapping(value = {"search"}, method = RequestMethod.GET)
	public String searchFields(Model model) {
		ClientDTO clientDTOSearch
	}*/

	
	@RequestMapping(value = { "/filterClients" }, method = RequestMethod.POST)
	public String filterClients(Model model, @ModelAttribute("clientDTOSearch") ClientDTO clientDTOSearch) {
		System.out.println("IMEEEEEEEEEEEE : "+clientDTOSearch.getName());
		model.addAttribute("clients", clientService.filter(clientDTOtoClient.convert(clientDTOSearch)));
		model.addAttribute("clientDTOSearch", new ClientDTO());
		model.addAttribute("clientDTO", new ClientDTO());
		System.out.println("U FILTERU SMO");
		return "/client";
	}
	/*
	 	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Client>> getClients() {
		List<Client> clients = clientService.findAll();
		return new ResponseEntity<>(clients, HttpStatus.OK);
		
	}
	 */
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
		Client client = clientService.findOne(id);
		return new ResponseEntity<>(clientToClientDTO.convert(client), HttpStatus.OK);
	}
	
	//@RequestMapping(method = RequestMethod.POST, consumes="application/json")
/*	@RequestMapping(value = {"/addClient"} , method = RequestMethod.GET)
	public String goToAddClient(Model model){//@Valid @RequestBody Client client, Errors errors){
		
		ClientDTO clientDTO = new ClientDTO();
		model.addAttribute("clientDTO", clientDTO);
		System.out.println("REDIREKCIJA NA SAJT GDE CEMO UNETI NOVOG KLIJENTA");
		return "addClient";
	}*/
	
	@RequestMapping(value = {"/addClient"} , method = RequestMethod.POST)
	public String addClient(Model model, @ModelAttribute("clientDTO") ClientDTO clientDTO, @RequestParam String mode){
		System.out.println("DUGME : " +mode);

		if("add".equals(mode)) {
			clientDTO.setId(null);
			clientService.save(clientDTOtoClient.convert(clientDTO));
		} else {
			clientService.edit(clientDTO.getId(), clientDTOtoClient.convert(clientDTO));
		}
		return "redirect:/clientList";
	}
	
	@RequestMapping(value = {"/deleteClient/{id}"}, method = RequestMethod.GET)
	public String deleteClient(@PathVariable String id ) {
		clientService.delete(Long.parseLong(id));
		return "redirect:/clientList";
	}

/*	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id) {
		Client clientDeleted = clientService.delete(id);
		return new ResponseEntity<>(clientToClientDTO.convert(clientDeleted), HttpStatus.OK);
	}*/
	

}
