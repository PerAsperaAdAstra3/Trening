package training.service;

import java.util.List;

import training.model.ElementsInPackages;
import training.model.PackageElement;
import training.model.Package;

public interface ElementsInPackagesService {

	ElementsInPackages findOne(Long id);
	
	List<ElementsInPackages> findAll();
	
	ElementsInPackages filter(Package packag1, PackageElement packageElement);
	
	ElementsInPackages save(ElementsInPackages elementsInPackages);
	
	List<ElementsInPackages> save(List<ElementsInPackages> elementsInPackages);
	
	ElementsInPackages delete(Long id);
	
	void delete(List<Long> ids);
	
	ElementsInPackages edit(Long id, ElementsInPackages elementsInPackages);
}
