package training;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import training.model.Exercise;
import training.service.ExerciseService;

public class TrainingTestData {

	@Autowired
	private ExerciseService exerciseService;

	@PostConstruct
	private void init() {
/*
		Exercise exercise1 = new Exercise("Stomach", "Exercise for stomack");
		exerciseService.save(exercise1);

		Exercise exercise2 = new Exercise("Biceps", "Exercise for Bicepse");
		exerciseService.save(exercise2);

		Exercise exercise3 = new Exercise("Legs", "Exercise for legs");
		exerciseService.save(exercise3);

		Exercise exercise4 = new Exercise("Triceps", "Exercise for triceps");
		exerciseService.save(exercise4);*/
	}
}
