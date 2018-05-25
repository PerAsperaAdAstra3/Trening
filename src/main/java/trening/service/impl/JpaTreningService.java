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
	public Trening save(Trening trening) {
		return treningRepository.save(trening);
	}

	@Override
	public List<Trening> save(List<Trening> treninzi) {
		return treningRepository.save(treninzi);
	}

	@Override
	public Trening delete(Long id) {
		Trening country = treningRepository.findOne(id);
		if(country == null){
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant country");
		}
		treningRepository.delete(country);
		return country;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids){
			this.delete(id);
		}
	}

	@Override
	public List<Trening> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
