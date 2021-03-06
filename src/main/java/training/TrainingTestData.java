package training;

import org.springframework.stereotype.Component;

@Component
public class TrainingTestData {
/*
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ExerciseInRoundService exerciseInRoundService;
	
	@Autowired
	private ExerciseGroupService exerciseGroupService;

	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private RoundService roundService;
	
	@PostConstruct
	private void init() {

		//Trainings
		
		Training training1 = new Training();
		Training training2 = new Training();
		Training training3 = new Training();
		
		training1.setDate(new Date());
		training1.setNumberOfTrainings(1);
		training2.setDate(new Date());
		training2.setNumberOfTrainings(2);
		training3.setDate(new Date());
		training3.setNumberOfTrainings(3);
		
		
		//Rounds
		Round round1 = new Round(1);
		Round round2 = new Round(2);
		Round round3 = new Round(3);
		
		roundService.save(round1);
		roundService.save(round2);
		roundService.save(round3);
		
		
		//Exercises
		Exercise exercise1 = new Exercise("Stomach", "Exercise for stomack");
		Exercise exercise2 = new Exercise("Biceps", "Exercise for Bicepse");
		Exercise exercise3 = new Exercise("Legs", "Exercise for legs");
		Exercise exercise4 = new Exercise("Triceps", "Exercise for triceps");
		
		exerciseService.save(exercise1);
		exerciseService.save(exercise2);
		exerciseService.save(exercise3);
		exerciseService.save(exercise4);
		
		Client client1 = new Client("Firstname","Lastname");
		
		
		client1.addTrainingList(training1);
		client1.addTrainingList(training2);
		client1.addTrainingList(training3);
		clientService.save(client1);
		trainingService.save(training1);
		trainingService.save(training2);
		trainingService.save(training3);
		
		
		Client client2 = new Client("Firstname1","Lastname1");
		clientService.save(client2);
		
		ExerciseGroup exerciseGroup1 = new ExerciseGroup();
		ExerciseGroup exerciseGroup2 = new ExerciseGroup();

        ExerciseInRound exerciseInRound1 = new ExerciseInRound(2,"First round");		
        ExerciseInRound exerciseInRound2 = new ExerciseInRound(3,"Second round");		
        ExerciseInRound exerciseInRound3 = new ExerciseInRound(4,"Third round");		

        exerciseInRound1.setExerciseId(exercise1.getId());  
		exerciseInRoundService.save(exerciseInRound1);
		round1.setExerciseInRound(exerciseInRound1);
		training1.addRound(round1);

        exerciseInRound2.setExerciseId(exercise2.getId()); 
		exerciseInRoundService.save(exerciseInRound2);
		round2.setExerciseInRound(exerciseInRound2);
		training2.addRound(round2);

        exerciseInRound3.setExerciseId(exercise3.getId());  
		exerciseInRoundService.save(exerciseInRound3);
		round3.setExerciseInRound(exerciseInRound3);
		training3.addRound(round3);
		
		exerciseGroup1.addExercise(exercise1);
		exerciseGroup1.addExercise(exercise2);

		exerciseGroup2.addExercise(exercise3);
		exerciseGroup2.addExercise(exercise4);
		
		exerciseGroup1.setName("Goup1");
		exerciseGroup2.setName("Goup2");
		

		
		trainingService.save(training1);
		
		roundService.save(round1);
		roundService.save(round2);
		roundService.save(round3);
		
		exerciseGroupService.save(exerciseGroup1);
		exerciseGroupService.save(exerciseGroup2);
		
		exerciseService.save(exercise1);
		exerciseService.save(exercise2);
		
		exerciseService.save(exercise3);
		exerciseService.save(exercise4);
		
	}*/
}
