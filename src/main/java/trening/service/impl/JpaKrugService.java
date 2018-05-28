package trening.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import trening.model.Krug;
import trening.repository.KrugRepository;
import trening.service.KrugService;

public class JpaKrugService implements KrugService {

	@Autowired
	private KrugRepository krugRepository;
	
	@Override
	public Krug findOne(Long id) {
		return krugRepository.findOne(id);
	}

	@Override
	public List<Krug> findAll() {
		return krugRepository.findAll();
	}

	@Override
	public Krug save(Krug krug) {
		return krugRepository.save(krug) ;
	}

	@Override
	public List<Krug> save(List<Krug> krugovi) {
		return krugRepository.save(krugovi) ;
	}

	@Override
	public Krug delete(Long id) {
		Krug krug = krugRepository.findOne(id);
		if(krug == null) {
			throw new IllegalStateException("Krug ne postoji");
		}
		krugRepository.delete(krug);
		return krug;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids) {
			this.delete(id);
		}
	}

}
