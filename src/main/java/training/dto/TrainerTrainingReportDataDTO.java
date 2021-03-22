package training.dto;

public class TrainerTrainingReportDataDTO {
	private String name;
	private String startDate;
	private String endDate;
	private String listOfTrainings;
	private int numberOfTrainings;
	
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
	
	TrainerTrainingReportDataDTO() {}
}
