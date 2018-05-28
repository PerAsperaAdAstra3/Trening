package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.VezbaUKrugu;
import trening.repository.VezbaUKruguRepository;
import trening.service.VezbaUKruguService;

public class JpaVezbaUKruguService implements VezbaUKruguService {

	@Autowired
	private VezbaUKruguRepository vezbaUKruguRepository;

	@Override
	public VezbaUKrugu findOne(Long id) {
		return vezbaUKruguRepository.findOne(id);
	}

	@Override
	public List<VezbaUKrugu> findAll() {
		return vezbaUKruguRepository.findAll();
	}

	@Override
	public VezbaUKrugu save(VezbaUKrugu vezbaUKrugu) {
		return vezbaUKruguRepository.save(vezbaUKrugu);
	}

	@Override
	public List<VezbaUKrugu> save(List<VezbaUKrugu> vezbeUKrugu) {
		return vezbaUKruguRepository.save(vezbeUKrugu);
	}

	@Override
	public VezbaUKrugu delete(Long id) {
		VezbaUKrugu vezbaUKrugu = vezbaUKruguRepository.findOne(id);
		if (vezbaUKrugu == null) {
			throw new IllegalStateException("Vezba u krugu ne postoji");
		}
		vezbaUKruguRepository.delete(vezbaUKrugu);
		return vezbaUKrugu;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids)
			this.delete(id);
	}

}
