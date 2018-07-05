package training.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "Round")
public class Round {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn(name = "RoundSeqNumber")
	private int roundSequenceNumber;
	
	@ManyToOne
	@JoinColumn(name = "exerInRound")
	private ExerciseInRound roundExerciseInRound;
	
	@ManyToOne
	@JoinColumn(name = "trainingRound")
	private Training trainingRound;

	public Long getId() {
		return id;
	}
	
	public ExerciseInRound getExerciseInRound() {
		return roundExerciseInRound;
	}

	public void setExerciseInRound(ExerciseInRound roundExerciseInRound) {
		this.roundExerciseInRound = roundExerciseInRound;
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
