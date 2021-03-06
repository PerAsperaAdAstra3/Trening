package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Client;
import training.model.ClientPackage;
import training.model.ClientPackageElement;
import training.repository.ClientPackageElementRepository;
import training.service.ClientPackageElementService;

@Service
@Transactional
public class JpaClientPackageElementService implements ClientPackageElementService {

	@Autowired
	ClientPackageElementRepository clientPackageElementRepository;
	
	@Override
	public ClientPackageElement findOne(Long id) {
		return clientPackageElementRepository.findById(id).get();
	}

	@Override
	public List<ClientPackageElement> filter(List<ClientPackage> clientPackage){
		return clientPackageElementRepository.findByClientPackageIn(clientPackage) ;
	}
	
	@Override
	public List<ClientPackageElement> findAll() {
		return clientPackageElementRepository.findAll();
	}

	@Override
	public ClientPackageElement save(ClientPackageElement clientPacakgeElement) {
		return clientPackageElementRepository.save(clientPacakgeElement);
	}

	@Override
	public List<ClientPackageElement> save(List<ClientPackageElement> clientPackageElements) {
		return clientPackageElementRepository.saveAll(clientPackageElements);
	}

	@Override
	public ClientPackageElement delete(Long id) {
		ClientPackageElement clientPackageElement = clientPackageElementRepository.findById(id).get();
		if(clientPackageElement == null) {
			throw new IllegalStateException("ClientPackageElement does not exist!");
		}
		clientPackageElementRepository.delete(clientPackageElement);
		return clientPackageElement;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids) {
			this.delete(id);
		}
	}

	@Override
	public ClientPackageElement edit(Long id, ClientPackageElement clientPackageElement) {
		ClientPackageElement oldClientPackageElement = clientPackageElementRepository.findById(id).get();
		oldClientPackageElement.setCounter(clientPackageElement.getCounter());
		oldClientPackageElement.setClientPackage(clientPackageElement.getClientPackage());
		clientPackageElementRepository.save(oldClientPackageElement);
		return null;
	}

}
