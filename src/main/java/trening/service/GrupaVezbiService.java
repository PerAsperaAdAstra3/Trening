package trening.service;

import java.util.List;

import trening.model.GrupaVezbi;

public interface GrupaVezbiService {
	GrupaVezbi findOne(Long id);

	List<GrupaVezbi> findAll();
	
	GrupaVezbi save(GrupaVezbi grupaVezbi);
	
	List<GrupaVezbi> save(List<GrupaVezbi> grupeVezbi);
	
	GrupaVezbi delete(Long id);
	
	void delete(List<Long> ids);
}
