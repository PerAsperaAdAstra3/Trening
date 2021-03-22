package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.ClientPackageElement;
import training.model.PackageElement;
import training.repository.PackageElementRepository;
import training.service.PackageElementService;

@Service
@Transactional
public class JpaPackageElementService implements PackageElementService {

	@Autowired
	PackageElementRepository packageElementRepository;
	
	@Override
	public PackageElement findOne(Long id) {
		return packageElementRepository.findById(id).get();
	}

	@Override
	public List<PackageElement> findAll() {
		return packageElementRepository.findAll();
	}

	@Override
	public PackageElement save(PackageElement packageElement) {
		return packageElementRepository.save(packageElement);
	}

	@Override
	public List<PackageElement> save(List<PackageElement> packageElements) {
		return packageElementRepository.saveAll(packageElements);
	}

	@Override
	public PackageElement delete(Long id) {
		PackageElement packageElement = packageElementRepository.findById(id).get();
		if(packageElement == null) {
			throw new IllegalStateException("Package element does not exist!");
		}
		packageElementRepository.delete(packageElement);
		return packageElement;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids) {
			this.delete(id);
		}
	}

	@Override
	public PackageElement edit(Long id, PackageElement packageElement) {
		PackageElement oldPackageElement = packageElementRepository.findById(id).get();
		oldPackageElement.setDescription(packageElement.getDescription());
		oldPackageElement.setPackageElementName(packageElement.getPackageElementName());
		oldPackageElement.setIsProtected(packageElement.isIsProtected());
		packageElementRepository.save(oldPackageElement);
		return null;
	}

}
