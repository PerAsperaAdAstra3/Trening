package training.service;

import java.util.List;

import training.model.ExerciseInRound;

public interface ExerciseInRoundService {

	ExerciseInRound findOne(Long id);

	List<ExerciseInRound> findAll();

	ExerciseInRound save(ExerciseInRound exerciseInRound);

	List<ExerciseInRound> save(List<ExerciseInRound> exerciseInRound);

	ExerciseInRound delete(Long id);

	void delete(List<Long> ids);
}
