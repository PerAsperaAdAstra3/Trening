package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.Klijent;
import trening.repository.KlijentRepository;
import trening.service.KlijentService;

public class JpaKlijentService implements KlijentService {

	@Autowired
	private KlijentRepository klijentRepository;

	@Override
	public Klijent findOne(Long id) {
		return klijentRepository.findOne(id);
	}

	@Override
	public List<Klijent> findAll() {
		return klijentRepository.findAll();
	}

	@Override
	public Klijent save(Klijent klijent) {
		return klijentRepository.save(klijent);
	}

	@Override
	public List<Klijent> save(List<Klijent> klijenti) {
		return klijentRepository.save(klijenti);
	}

	@Override
	public Klijent delete(Long id) {
		Klijent klijent = klijentRepository.findOne(id);
		if (klijent == null) {
			throw new IllegalStateException("Klijent nije nadjen.");
		}
		klijentRepository.delete(klijent);
		return klijent;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
}
