package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.GrupaVezbi;
import trening.repository.GrupaVezbiRepository;
import trening.service.GrupaVezbiService;

public class JpaGrupaVezbiService implements GrupaVezbiService {

	@Autowired
	private GrupaVezbiRepository grupaVezbiRepository;

	@Override
	public GrupaVezbi findOne(Long id) {
		return grupaVezbiRepository.findOne(id);
	}

	@Override
	public List<GrupaVezbi> findAll() {
		return grupaVezbiRepository.findAll();
	}

	@Override
	public GrupaVezbi save(GrupaVezbi grupaVezbi) {
		return grupaVezbiRepository.save(grupaVezbi);
	}

	@Override
	public List<GrupaVezbi> save(List<GrupaVezbi> grupeVezbi) {
		return grupaVezbiRepository.save(grupeVezbi);
	}

	@Override
	public GrupaVezbi delete(Long id) {
		GrupaVezbi grupaVezbi = grupaVezbiRepository.findOne(id);
		if (grupaVezbi == null) {
			throw new IllegalStateException("");
		}
		grupaVezbiRepository.delete(grupaVezbi);
		return grupaVezbi;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			this.delete(id);
		}
	}
}
