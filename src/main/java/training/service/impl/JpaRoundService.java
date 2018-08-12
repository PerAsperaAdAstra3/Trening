package training.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import training.model.ExerciseInRound;
import training.model.Round;
import training.repository.RoundRepository;
import training.service.RoundService;

@Service
@Transactional
public class JpaRoundService implements RoundService {

	@Autowired
	private RoundRepository roundRepository;
	
	@Override
	public Round findOne(Long id) {
		return roundRepository.findOne(id);
	}

	@Override
	public List<Round> findAll() {
		return roundRepository.findAll();
	}

	@Override
	public Round save(Round round) {
		return roundRepository.save(round) ;
	}

	@Override
	public List<Round> save(List<Round> rounds) {
		return roundRepository.save(rounds) ;
	}

	@Override
	public Round delete(Long id) {
		Round round = roundRepository.findOne(id);
		if(round == null){
			throw new IllegalStateException("Round does not exist");
		}
		roundRepository.delete(round);
		return round;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id : ids)
			this.delete(id);
	}
	
	@Override
	public Round edit(Long id, Round round) {
		Round oldRound = roundRepository.findOne(id);
		oldRound.setRoundSequenceNumber(round.getRoundSequenceNumber());
		for(ExerciseInRound exerciseInRound : round.getExerciseInRound()) {
			oldRound.setExerciseInRound(exerciseInRound);
		}
		roundRepository.save(oldRound);
		return oldRound;
	}
}
