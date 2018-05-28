package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.Zadatak;
import trening.repository.ZadatakRepository;
import trening.service.ZadatakService;

public class JpaZadatakService implements ZadatakService {

	@Autowired
	private ZadatakRepository zadatakRepository;

	@Override
	public Zadatak findOne(Long id) {
		return zadatakRepository.findOne(id);
	}

	@Override
	public List<Zadatak> findAll() {
		return zadatakRepository.findAll();
	}

	@Override
	public Zadatak save(Zadatak zadatak) {
		return zadatakRepository.save(zadatak);
	}

	@Override
	public List<Zadatak> save(List<Zadatak> zadaci) {
		return zadatakRepository.save(zadaci);
	}

	@Override
	public Zadatak delete(Long id) {
		Zadatak zadatak = zadatakRepository.findOne(id);
		if (zadatak == null) {
			throw new IllegalArgumentException("Missing zadatak");
		}
		zadatakRepository.delete(zadatak);
		return zadatak;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
}
