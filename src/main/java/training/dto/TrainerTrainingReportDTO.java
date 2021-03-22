package training.dto;

import java.util.Date;

public class TrainerTrainingReportDTO {

	private Long highlightedTrainingId;
	private Date startDate;
	private Date endDate;
	
	public Long getHighlightedTrainingId() {
		return highlightedTrainingId;
	}
	public void setHighlightedClientId(Long highlightedTrainingId) {
		this.highlightedTrainingId = highlightedTrainingId;
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
	
	public TrainerTrainingReportDTO() {}
	
	public TrainerTrainingReportDTO(Long highlightedClientId, Date startDate, Date endDate) {
		this.highlightedTrainingId = highlightedClientId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
