package training.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.converter.ClientToClientDTO;
import training.dto.ClientDTO;
import training.model.Client;
import training.service.ClientService;

@RestController
@RequestMapping(path = "api/clients")
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@Autowired
    ClientToClientDTO clientToClientDTO;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Client>> getClients() {
		List<Client> clients = clientService.findAll();
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
		Client client = clientService.findOne(id);
		return new ResponseEntity<>(clientToClientDTO.convert(client), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<?> addClient(@Valid @RequestBody Client client, Errors errors){
		if(errors.hasErrors()) {
			System.out.println(errors.getAllErrors());
			return new ResponseEntity<String>(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
		}
		Client newClient = clientService.save(client);
		return new ResponseEntity<>(clientToClientDTO.convert(newClient), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ClientDTO> delete(@PathVariable Long id) {
		Client clientDeleted = clientService.delete(id);
		return new ResponseEntity<>(clientToClientDTO.convert(clientDeleted), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
		public ResponseEntity<ClientDTO> edit(@PathVariable Long id, @RequestBody Client client){
			Client newClient = clientService.edit(id, client);
			return new ResponseEntity<>(clientToClientDTO.convert(newClient), HttpStatus.OK);
		}
}
