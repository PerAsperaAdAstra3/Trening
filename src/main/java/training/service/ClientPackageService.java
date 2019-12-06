package training.service;

import java.util.List;

import training.model.ClientPackage;

public interface ClientPackageService {

	ClientPackage findOne(Long id);
	
	List<ClientPackage> filter(ClientPackage clientPackage);
	
	List<ClientPackage> findAll();
	
	ClientPackage save(ClientPackage clientPackage);
	
	List<ClientPackage> save(List<ClientPackage> clientPackages);
	
	ClientPackage delete(Long id);
	
	void delete(List<Long> ids);
	
	ClientPackage edit(Long id, ClientPackage clientPackage);
}
