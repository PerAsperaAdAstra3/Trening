package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.ClientDTOtoClient;
import training.converter.ClientToClientDTO;
import training.dto.ClientDTO;
import training.model.Client;
import training.service.ClientService;

//@RestController
//@RequestMapping(path = "api/clients")
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
	//	return new ResponseEntity<>(clients, HttpStatus.OK);
		model.addAttribute("clients", clientToClientDTO.convert(clients));
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
	@RequestMapping(value = {"/addClient"} , method = RequestMethod.GET)
	public String goToAddClient(Model model){//@Valid @RequestBody Client client, Errors errors){
		
		ClientDTO clientDTO = new ClientDTO();
		model.addAttribute("clientDTO", clientDTO);
		return "addClient";
	}
	
	@RequestMapping(value = {"/addClient"} , method = RequestMethod.POST)
	public String addClient(Model model, @ModelAttribute("clientDTO") ClientDTO clientDTO){//@Valid @RequestBody Client client, Errors errors){
		
		Client newClient = clientDTOtoClient.convert(clientDTO);
	/*	String name = clientDTO.getName();
		String familyName = clientDTO.getFamilyName();
		
		if(name != null && name.length()>0 && familyName != null && familyName.length() >0) {
			Client client = new Client(name , familyName);
		}
		*/
		clientService.save(newClient);
		
		return "client";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String deleteClient(@PathVariable Long id) {
		Client clientDeleted = clientService.delete(id);
		return "client";
	}

/*	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id) {
		Client clientDeleted = clientService.delete(id);
		return new ResponseEntity<>(clientToClientDTO.convert(clientDeleted), HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
		public ResponseEntity<ClientDTO> edit(@PathVariable Long id, @RequestBody Client client){
			Client newClient = clientService.edit(id, client);
			return new ResponseEntity<>(clientToClientDTO.convert(newClient), HttpStatus.OK);
		}
	
	/*@RequestMapping(value= {"/message"}, method = RequestMethod.GET)
	public String name(Model model) {
		model.addAttribute("person", person);
	}
	
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String message(Model model) {
		model.addAttribute("message", message);
		return "client";
	} */
}
