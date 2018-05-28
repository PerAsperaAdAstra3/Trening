package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trening.model.Trening;
import trening.repository.TreningRepository;
import trening.service.TreningService;

@Transactional
@Service
public class JpaTreningService implements TreningService {

	@Autowired
	private TreningRepository treningRepository;

	@Override
	public Trening findOne(Long id) {
		return treningRepository.findOne(id);
	}

	@Override
	public List<Trening> findAll() {
		return treningRepository.findAll();
	}

	@Override
	public Trening save(Trening trening) {
		return treningRepository.save(trening);
	}

	@Override
	public List<Trening> save(List<Trening> treninzi) {
		return treningRepository.save(treninzi);
	}

	@Override
	public Trening delete(Long id) {
		Trening trening = treningRepository.findOne(id);
		if (trening == null) {
			throw new IllegalArgumentException("Trening ne postoji");
		}
		treningRepository.delete(trening);
		return trening;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
}
