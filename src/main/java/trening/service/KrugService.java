package trening.service;

import java.util.List;

import trening.model.Krug;

public interface KrugService {
	Krug findOne(Long id);

	List<Krug> findAll();
	
	Krug save(Krug krug);
	
	List<Krug> save(List<Krug> krugovi);
	
	Krug delete(Long id);
	
	void delete(List<Long> ids);
}
