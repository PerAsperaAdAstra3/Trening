package training.dto;

public class RoundDTO {

	private int roundSequenceNumber;

	public int getRoundSequenceNumber() {
		return roundSequenceNumber;
	}

	public void setRoundSequenceNumber(int roundSequenceNumber) {
		this.roundSequenceNumber = roundSequenceNumber;
	}

	public RoundDTO(int roundSequenceNumber) {
		super();
		this.roundSequenceNumber = roundSequenceNumber;
	}
	
}
