package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ROUND")
	private boolean round;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trainingTask")
	private Training trainingTask;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	private List<ExerciseGroup> exercisesGroup = new ArrayList<ExerciseGroup>();

	public Long getId() {
		return id;
	}

	public boolean isRound() {
		return round;
	}

	public void setRound(boolean round) {
		this.round = round;
	}

	public Training getTraining() {
		return trainingTask;
	}

	public void setTraining(Training training) {
		this.trainingTask = training;
	}

	public List<ExerciseGroup> getExercisesInRound() {
		return exercisesGroup;
	}

	public void addExercisesInRound(ExerciseGroup exerciseInRound) {
	//	exercisesInRound.set
		exercisesGroup.add(exerciseInRound);
	}

	public Task() {}
	
	public Task(boolean round, Training training) {
		super();
		this.round = round;
		this.trainingTask = training;
	} 	
}
