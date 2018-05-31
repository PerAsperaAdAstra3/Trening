package training.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Training {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long training_Id;
	
	@Column(name="Date")
	private Date date;
	
	@Column(name="NumberOfTrainings")
	private Integer numberOfTrainings;
	
	private Task task;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getNumberOfTrainings() {
		return numberOfTrainings;
	}
	public void setNumberOfTrainings(Integer numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	
	
}
