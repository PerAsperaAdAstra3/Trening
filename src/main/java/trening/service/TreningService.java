package trening.service;

import java.util.List;

import trening.model.*;

public interface TreningService {

	Trening findOne(Long id);

	List<Trening> findAll();
	
	Trening save(Trening trening);
	
	List<Trening> save(List<Trening> treninzi);
	
	Trening delete(Long id);
	
	void delete(List<Long> ids);
}
