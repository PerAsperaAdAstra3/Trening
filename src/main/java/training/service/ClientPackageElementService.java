package training.service;

import java.util.List;

import training.model.Client;
import training.model.ClientPackage;
import training.model.ClientPackageElement;

public interface ClientPackageElementService {

	ClientPackageElement findOne(Long id);
	
	List<ClientPackageElement> filter(ClientPackage clientPackage);
	
	List<ClientPackageElement> findAll();
	
	ClientPackageElement save(ClientPackageElement clientPacakgeElement);
	
	List<ClientPackageElement> save(List<ClientPackageElement> clientPackageElements);
	
	ClientPackageElement delete(Long id);
	
	void delete(List<Long> ids);
	
	ClientPackageElement edit(Long id, ClientPackageElement clientPackageElement);
}
