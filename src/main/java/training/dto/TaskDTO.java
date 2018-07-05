package training.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskDTO {

	private Long id;
	
	private boolean round;
	
	private String trainingName;
	
	private List<String> exercisesNameInRound = new ArrayList<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isRound() {
		return round;
	}

	public void setRound(boolean round) {
		this.round = round;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public List<String> getExercisesNameInRound() {
		return exercisesNameInRound;
	}

	public void setExercisesNameInRound(List<String> exercisesNameInRound) {
		this.exercisesNameInRound = exercisesNameInRound;
	}

	public TaskDTO() {}
	
	public TaskDTO(boolean round, String trainingName) {
		super();
		this.round = round;
		this.trainingName = trainingName;
	}	
}
