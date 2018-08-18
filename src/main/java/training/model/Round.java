package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Round")
public class Round {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "RoundSeqNumber")
	private int roundSequenceNumber;
	
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
	private List<ExerciseInRound> roundExerciseInRound = new ArrayList<ExerciseInRound>();
	
	@ManyToOne
	@JoinColumn(name = "trainingRound")
	private Training trainingRound;

	public Long getId() {
		return id;
	}
	
	public List<ExerciseInRound> getExerciseInRound() {
		return roundExerciseInRound;
	}

	public void setExerciseInRound(ExerciseInRound exerciseInRound) {
		exerciseInRound.setRound(this);
		roundExerciseInRound.add(exerciseInRound);
	}

	public int getRoundSequenceNumber() {
		return roundSequenceNumber;
	}

	public void setRoundSequenceNumber(int roundSequenceNumber) {
		this.roundSequenceNumber = roundSequenceNumber;
	}

	public Training getTraining() {
		return trainingRound;
	}

	public void setTraining(Training training) {
		this.trainingRound = training;
	}

	public Round() {}
	
	public Round(int roundSequenceNumber) {
		super();
		this.roundSequenceNumber = roundSequenceNumber;
	}
}
