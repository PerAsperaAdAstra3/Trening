package training.dto;

import java.util.List;

public class MultipleExercisetoRoundDTO {

	private List<Long> exerciseIDList;
	
	private Long trainingId;
	
	private Long highlightedRoundId;
	
	private Boolean circularRoundYN;
	
	public Boolean getCircularRoundYN() {
		return circularRoundYN;
	}

	public void setCircularRoundYN(Boolean circularRoundYN) {
		this.circularRoundYN = circularRoundYN;
	}

	public void setExerciseIDList(List<Long> exerciseIDList) {
		this.exerciseIDList = exerciseIDList;
	}

	public Long getHighlightedRoundId() {
		return highlightedRoundId;
	}

	public void setHighlightedRoundId(Long highlightedRoundId) {
		this.highlightedRoundId = highlightedRoundId;
	}

	public List<Long> getExerciseIDList() {
		return exerciseIDList;
	}

	public void addExerciseIDList(Long exerciseID) {
		this.exerciseIDList.add(exerciseID);
	}

	public Long getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Long trainingId) {
		this.trainingId = trainingId;
	}

	public MultipleExercisetoRoundDTO() {}
	
	public MultipleExercisetoRoundDTO(List<Long> exerciseIDList, Long trainingId) {
		super();
		this.exerciseIDList = exerciseIDList;
		this.trainingId = trainingId;
	}
	
	
	
}
