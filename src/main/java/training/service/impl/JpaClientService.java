package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Client;
import training.repository.ClientRepository;
import training.service.ClientService;

@Service
@Transactional
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
	
	@Override
	public Client edit(Long id, Client client) {
		Client oldClient = clientRepository.findOne(id);
		oldClient.setName(client.getName());
		oldClient.setFamilyName(client.getFamilyName());
		oldClient.setEmail(client.getEmail());
		oldClient.setPhoneNumber(client.getPhoneNumber());
		clientRepository.save(oldClient);
		return oldClient;
	}

	@Override
	public List<Client> filter(Client client) {
		return clientRepository.findByNameIgnoreCaseContainingAndFamilyNameIgnoreCaseContaining(client.getName(), client.getFamilyName());
	}
}
