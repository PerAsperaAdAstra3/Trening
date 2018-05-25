package trening.service;

import java.util.List;

import trening.model.Vezba;

public interface VezbaService {
	Vezba findOne(Long id);

	List<Vezba> findAll();
	
	Vezba save(Vezba vezba);
	
	List<Vezba> save(List<Vezba> vezbe);
	
	Vezba delete(Long id);
	
	void delete(List<Long> ids);
}
