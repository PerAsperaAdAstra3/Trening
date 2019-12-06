package training.service;

import java.util.List;
import training.model.Package;

public interface PackageService {

	Package findOne(Long id);
	
	List<Package> findAll();
	
	Package save(Package packageUnit);
	
	List<Package> save(List<Package> packages);
	
	Package delete(Long id);
	
	void delete(List<Long> ids);
	
	Package edit(Long id, Package packageUnit);
}
