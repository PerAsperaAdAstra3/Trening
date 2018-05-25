package trening.service;

import java.util.List;

import trening.model.VezbaUKrugu;

public interface VezbaUKruguService {
	VezbaUKrugu findOne(Long id);

	List<VezbaUKrugu> findAll();
	
	VezbaUKrugu save(VezbaUKrugu vezbaUKrugu);
	
	List<VezbaUKrugu> save(List<VezbaUKrugu> vezbeUKrugu);
	
	VezbaUKrugu delete(Long id);
	
	void delete(List<Long> ids);
}
