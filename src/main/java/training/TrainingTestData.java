package training;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import training.model.Client;
import training.model.Exercise;
import training.service.ClientService;
import training.service.ExerciseService;

@Component
public class TrainingTestData {

	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private ClientService clientService;

	@PostConstruct
	private void init() {

		Exercise exercise1 = new Exercise("Stomach", "Exercise for stomack");
		exerciseService.save(exercise1);

		Exercise exercise2 = new Exercise("Biceps", "Exercise for Bicepse");
		exerciseService.save(exercise2);

		Exercise exercise3 = new Exercise("Legs", "Exercise for legs");
		exerciseService.save(exercise3);

		Exercise exercise4 = new Exercise("Triceps", "Exercise for triceps");
		exerciseService.save(exercise4);
		
		Client client1 = new Client("Firstname","Lastname");
		clientService.save(client1);
		
		Client client2 = new Client("Firstname1","Lastname1");
		clientService.save(client2);
		
	}
}
