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
	
	private int roundSequenceNumber;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="exerInRound")
	private ExerciseInRound roundExerciseInRound;
	

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

	public Round() {}
	
	public Round(int roundSequenceNumber) {
		super();
		this.roundSequenceNumber = roundSequenceNumber;
	}
	
}
