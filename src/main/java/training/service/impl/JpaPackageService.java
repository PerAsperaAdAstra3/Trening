package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.repository.PackageRepository;
import training.service.PackageService;
import training.model.ClientPackage;
import training.model.Package;
import training.model.PackageElement;

@Service
@Transactional
public class JpaPackageService implements PackageService {

	@Autowired
	PackageRepository packageRepository;
	
	@Override
	public Package findOne(Long id) {
		return packageRepository.findOne(id);
	}

	@Override
	public List<Package> findAll() {
		return packageRepository.findAll();
	}

	@Override
	public Package save(Package packageUnit) {
		return packageRepository.save(packageUnit);
	}

	@Override
	public List<Package> save(List<Package> packages) {
		return packageRepository.save(packages);
	}

	@Override
	public Package delete(Long id) {
		Package packageUnit = packageRepository.findOne(id);
		if(packageUnit == null) {
			throw new IllegalStateException("Package not found!");
		}
		packageRepository.delete(packageUnit);
		return packageUnit;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id: ids) {
			this.delete(id);
		}
	}

	@Override
	public Package edit(Long id, Package packageUnit) {
		Package oldPackage = packageRepository.findOne(id);
		
		oldPackage.setNameOfPackage(packageUnit.getNameOfPackage());
		
		for(ClientPackage clientPackage : packageUnit.getClientPackages()) {
			oldPackage.addClientPackages(clientPackage);
		}
		for(PackageElement packageElement : packageUnit.getPackageElements()) {
			oldPackage.addPackageElements(packageElement);
		}
		packageRepository.save(oldPackage);
		return oldPackage;
	}

}
