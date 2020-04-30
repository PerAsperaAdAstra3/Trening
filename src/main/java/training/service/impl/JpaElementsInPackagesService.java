package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.ElementsInPackages;
import training.model.PackageElement;
import training.model.Package;
import training.repository.ElementsInPackagesRepository;
import training.service.ElementsInPackagesService;
import training.service.PackageService;

@Service
@Transactional
public class JpaElementsInPackagesService implements ElementsInPackagesService {

	@Autowired
	ElementsInPackagesRepository elementsInPackagesRepository;
	
	@Override
	public ElementsInPackages findOne(Long id) {
		return elementsInPackagesRepository.findOne(id);
	}

	@Override
	public List<ElementsInPackages> findAll() {
		return elementsInPackagesRepository.findAll();
	}

	@Override
	public ElementsInPackages save(ElementsInPackages elementsInPackages) {
		return elementsInPackagesRepository.save(elementsInPackages);
	}

	@Override
	public List<ElementsInPackages> save(List<ElementsInPackages> elementsInPackages) {
		return elementsInPackagesRepository.save(elementsInPackages);
	}

	@Override
	public ElementsInPackages delete(Long id) {
		ElementsInPackages elementsInPackages = elementsInPackagesRepository.findOne(id);
		if(elementsInPackages == null) {
			throw new IllegalStateException("Client not found!");
		}
		elementsInPackagesRepository.delete(elementsInPackages);
		return elementsInPackages;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids) {
			this.delete(id);
		}
		
	}

	@Override
	public ElementsInPackages edit(Long id, ElementsInPackages elementsInPackages) {
		ElementsInPackages oldElementsInPackages = elementsInPackagesRepository.findOne(id);
		oldElementsInPackages.setPackageElementEIP(elementsInPackages.getPackageElementEIP());
		oldElementsInPackages.setPackage(elementsInPackages.getPackage());
		oldElementsInPackages.setNumber(elementsInPackages.getNumber());
		elementsInPackagesRepository.save(oldElementsInPackages);
		return oldElementsInPackages;
	}

	@Override
	public ElementsInPackages filter(Package elementsPackage, PackageElement packageElement) {
		return elementsInPackagesRepository.findByElementsPackageAndPackageElementEIP(elementsPackage, packageElement);
	}

}
