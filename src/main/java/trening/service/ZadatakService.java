package trening.service;

import java.util.List;

import trening.model.Zadatak;

public interface ZadatakService {
	Zadatak findOne(Long id);

	List<Zadatak> findAll();
	
	Zadatak save(Zadatak zadatak);
	
	List<Zadatak> save(List<Zadatak> zadaci);
	
	Zadatak delete(Long id);
	
	void delete(List<Long> ids);
}
