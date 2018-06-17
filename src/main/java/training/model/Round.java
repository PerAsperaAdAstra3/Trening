package training.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Round")
public class Round {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private int roundSequenceNumber;

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
