package training.dto;

public class RoundDTO {

	private Long id;
	
	private int roundSequenceNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRoundSequenceNumber() {
		return roundSequenceNumber;
	}

	public void setRoundSequenceNumber(int roundSequenceNumber) {
		this.roundSequenceNumber = roundSequenceNumber;
	}
	
	public RoundDTO() {}

	public RoundDTO(int roundSequenceNumber) {
		super();
		this.roundSequenceNumber = roundSequenceNumber;
	}
	
}
