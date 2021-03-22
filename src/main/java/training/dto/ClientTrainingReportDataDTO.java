package training.dto;

public class ClientTrainingReportDataDTO {
	private String name;
	private String startDate;
	private String endDate;
	private Long trainingPrice;
	private String listOfTrainings;
	private int numberOfTrainings;
	private Long oneTrainingPrice;
	private Long numberOfBonusTrainings;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getTrainingPrice() {
		return trainingPrice;
	}
	public void setTrainingPrice(Long trainingPrice) {
		this.trainingPrice = trainingPrice;
	}
	public String getListOfTrainings() {
		return listOfTrainings;
	}

	public void setListOfTrainings(String listOfTrainings) {
		this.listOfTrainings = listOfTrainings;
	}
	public int getNumberOfTrainings() {
		return numberOfTrainings;
	}
	public void setNumberOfTrainings(int numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}
	public Long getOneTrainingPrice() {
		return oneTrainingPrice;
	}
	public void setOneTrainingPrice(Long oneTrainingPrice) {
		this.oneTrainingPrice = oneTrainingPrice;
	}
	public Long getNumberOfBonusTrainings() {
		return numberOfBonusTrainings;
	}
	public void setNumberOfBonusTrainings(Long numberOfBonusTrainings) {
		this.numberOfBonusTrainings = numberOfBonusTrainings;
	}
	
	ClientTrainingReportDataDTO() {}
}
