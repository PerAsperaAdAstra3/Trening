package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "Task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ROUND")
	private boolean round;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trainingTask")
	private Training trainingTask;

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

	public Task() {}
	
	public Task(boolean round, Training training) {
		super();
		this.round = round;
		this.trainingTask = training;
	} 	
}
