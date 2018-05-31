package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import training.model.Client;
import training.repository.ClientRepository;
import training.service.ClientService;

public class JpaClientService implements ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public Client findOne(Long id) {
		return clientRepository.findOne(id);
	}

	@Override
	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public List<Client> save(List<Client> clients) {
		return clientRepository.save(clients);
	}

	@Override
	public Client delete(Long id) {
		Client client = clientRepository.findOne(id);
		if (client == null) {
			throw new IllegalStateException("Client not found!");
		}
		clientRepository.delete(client);
		return client;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
}
