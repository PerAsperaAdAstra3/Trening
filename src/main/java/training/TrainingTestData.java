package training;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import training.model.Client;
import training.model.Exercise;
import training.model.ExerciseGroup;
import training.model.ExerciseInRound;
import training.service.ClientService;
import training.service.ExerciseGroupService;
import training.service.ExerciseInRoundService;
import training.service.ExerciseService;
import training.service.RoundService;

@Component
public class TrainingTestData {

	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ExerciseInRoundService exerciseInRoundService;
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;

	@PostConstruct
	private void init() {

		Exercise exercise1 = new Exercise("Stomach", "Exercise for stomack");

		Exercise exercise2 = new Exercise("Biceps", "Exercise for Bicepse");

		Exercise exercise3 = new Exercise("Legs", "Exercise for legs");

		Exercise exercise4 = new Exercise("Triceps", "Exercise for triceps");
		
		Client client1 = new Client("Firstname","Lastname");
		clientService.save(client1);
		
		Client client2 = new Client("Firstname1","Lastname1");
		clientService.save(client2);
		
//		ExerciseGroup exerciseGroup1 = new ExerciseGroup("Group1");
		
		ExerciseGroup exerciseGroup1 = new ExerciseGroup();
		
		ExerciseGroup exerciseGroup2 = new ExerciseGroup();
		
		exerciseGroup1.addExercise(exercise1);
		exerciseGroup1.addExercise(exercise2);

		exerciseGroup2.addExercise(exercise3);
		exerciseGroup2.addExercise(exercise4);
		
		exerciseGroup1.setName("Goup1");
		
		exerciseGroup2.setName("Goup2");
		
//		ExerciseGroup exerciseGroup2 = new ExerciseGroup("Group2");
		
        ExerciseInRound exerciseInRound1 = new ExerciseInRound(2,"First round");
		exerciseInRoundService.save(exerciseInRound1);
		
		exerciseInRound1.addExercise(exercise1);
		exerciseInRound1.addExercise(exercise2);
		
		exerciseGroupService.save(exerciseGroup1);
		exerciseGroupService.save(exerciseGroup2);
		
		exerciseService.save(exercise1);
		exerciseService.save(exercise2);
		
		exerciseService.save(exercise3);
		exerciseService.save(exercise4);
		
	}
}
