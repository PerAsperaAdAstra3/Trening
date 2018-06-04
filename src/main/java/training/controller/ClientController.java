package training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import training.model.Client;
import training.service.ClientService;

@RestController
@RequestMapping(path = "api/client")
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<Client>> findClients(){
		List<Client> clients = clientService.findAll();
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Client> addClient(@RequestBody Client client){
		clientService.save(client);
		return new ResponseEntity<>(client, HttpStatus.OK);
	}
}
