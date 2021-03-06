package training.service;

import java.util.List;

import training.model.Client;

public interface ClientService {

	Client findOne(Long id);
	
	List<Client> filter(Client client);
	
	List<Client> findAll();

	Client save(Client client);

	List<Client> save(List<Client> clients);

	Client delete(Long id);

	void delete(List<Long> ids);
	
	Client edit(Long id, Client client);
}
