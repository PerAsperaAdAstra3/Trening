package training.service;

import java.util.List;

import training.model.PackageElement;

public interface PackageElementService {

	PackageElement findOne(Long id);
	
	List<PackageElement> findAll();
	
	PackageElement save(PackageElement packageElement);
	
	List<PackageElement> save(List<PackageElement> packageElements);
	
	PackageElement delete(Long id);
	
	void delete(List<Long> ids);
	
	PackageElement edit(Long id, PackageElement packageElement);
}
