package training.dto;

import java.util.Date;

public class ClientTrainingReportDTO {

	private Long highlightedClientId;
	private Date startDate;
	private Date endDate;
	private Long trainingPrice;
	
	public Long getTrainingPrice() {
		return trainingPrice;
	}
	public void setTrainingPrice(Long trainingPrice) {
		this.trainingPrice = trainingPrice;
	}
	public Long getHighlightedClientId() {
		return highlightedClientId;
	}
	public void setHighlightedClientId(Long highlightedClientId) {
		this.highlightedClientId = highlightedClientId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public ClientTrainingReportDTO() {}
	
	public ClientTrainingReportDTO(Long highlightedClientId, Date startDate, Date endDate, Long trainingPrice) {
		this.highlightedClientId = highlightedClientId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainingPrice = trainingPrice;
	}
}
