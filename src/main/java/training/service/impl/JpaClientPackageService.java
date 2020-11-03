package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.Client;
import training.model.ClientPackage;
import training.repository.ClientPackageRepository;
import training.service.ClientPackageService;

@Service
@Transactional
public class JpaClientPackageService implements ClientPackageService {

	@Autowired
	private ClientPackageRepository clientPackageRepository;
	
	@Override
	public ClientPackage findOne(Long id) {
		return clientPackageRepository.findById(id).get();
	}

	@Override
	public List<ClientPackage> filter(Long id) {
		return clientPackageRepository.findByClientId(id);//client);
	}

	@Override
	public List<ClientPackage> findAll() {
		return clientPackageRepository.findAll();
	}

	@Override
	public ClientPackage save(ClientPackage clientPackage) {
		return clientPackageRepository.save(clientPackage);
	}

	@Override
	public List<ClientPackage> save(List<ClientPackage> clientPackages) {
		return clientPackageRepository.saveAll(clientPackages);
	}

	@Override
	public ClientPackage delete(Long id) {
		ClientPackage clientPackage = clientPackageRepository.findById(id).get();
		if(clientPackage == null) {
			throw new IllegalStateException("ClientPackage not found!");
		}
		clientPackageRepository.delete(clientPackage);
		return clientPackage;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id: ids) {
			this.delete(id);
		}
	}

	@Override
	public ClientPackage edit(Long id, ClientPackage clientPackage) {
		ClientPackage oldClientPackage = clientPackageRepository.findById(id).get();
		oldClientPackage.setClient(clientPackage.getClient());
		oldClientPackage.setPackageUnit(clientPackage.getPackageUnit());
		clientPackageRepository.save(oldClientPackage);
		return oldClientPackage;
	}

}
