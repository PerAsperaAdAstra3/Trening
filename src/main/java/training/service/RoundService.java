package training.service;

import java.util.List;

import training.model.Round;

public interface RoundService {

	Round findOne(Long id);

	List<Round> findAll();

	Round save(Round round);

	List<Round> save(List<Round> rounds);

	Round delete(Long id);

	void delete(List<Long> ids);
}
