package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.Vezba;
import trening.repository.VezbaRepository;
import trening.service.VezbaService;

public class JpaVezbaService implements VezbaService {

	@Autowired
	private VezbaRepository vezbaRepository;

	@Override
	public Vezba findOne(Long id) {
		return vezbaRepository.findOne(id);
	}

	@Override
	public List<Vezba> findAll() {
		return vezbaRepository.findAll();
	}

	@Override
	public Vezba save(Vezba vezba) {
		return vezbaRepository.save(vezba);
	}

	@Override
	public List<Vezba> save(List<Vezba> vezbe) {
		return vezbaRepository.save(vezbe);
	}

	@Override
	public Vezba delete(Long id) {
		Vezba vezba = vezbaRepository.findOne(id);
		if (vezba == null) {
			throw new IllegalStateException("Ne postoji ta vezba");
		}
		vezbaRepository.delete(vezba);
		return vezba;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
}
