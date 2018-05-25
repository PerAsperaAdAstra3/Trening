package trening.service;

import java.util.List;

import trening.model.Klijent;

public interface KlijentService {
	Klijent findOne(Long id);

	List<Klijent> findAll();
	
	Klijent save(Klijent klijent);
	
	List<Klijent> save(List<Klijent> klijenti);
	
	Klijent delete(Long id);
	
	void delete(List<Long> ids);
}
