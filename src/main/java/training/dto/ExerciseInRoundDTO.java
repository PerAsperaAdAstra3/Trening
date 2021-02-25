package training.dto;

import training.model.Exercise;

public class ExerciseInRoundDTO {

	private Long id;
	
	private String numberOfRepetitions;

	private String difficulty;
	
	private String exerciseInRoundExerciseName;
	
	private Long exerciseInRoundExerciseId;

	private String roundName;

	private Long roundId;
	
	private ExerciseDTO exerciseDTO;
	
	private String note;
	
	public ExerciseDTO getExercise() {
		return exerciseDTO;
	}

	public void setExercise(ExerciseDTO exercise) {
		this.exerciseDTO = exercise;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getExerciseInRoundExerciseId() {
		return exerciseInRoundExerciseId;
	}

	public void setExerciseInRoundExerciseId(Long exerciseInRoundExerciseId) {
		this.exerciseInRoundExerciseId = exerciseInRoundExerciseId;
	}

	public String getRoundName() {
		return roundName;
	}

	public void setRoundName(String roundName) {
		this.roundName = roundName;
	}

	public Long getRoundId() {
		return roundId;
	}

	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}

	public String getNumberOfRepetitions() {
		return numberOfRepetitions;
	}

	public void setNumberOfRepetitions(String numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}

	public String getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}	
	
	public String getExerciseInRoundExerciseName() {
		if(exerciseDTO != null) {
			return exerciseDTO.getName();
		}
		else {
			return exerciseInRoundExerciseName;
		}
	}

	public void setExerciseInRoundExerciseName(String exerciseName) {
		this.exerciseInRoundExerciseName = exerciseName;
	}

	public String getRoundNameList() {
		return roundName;
	}

	public void addRoundName(String roundName) {
		this.roundName = roundName;
	}
	
	public ExerciseInRoundDTO() {}
	
	public ExerciseInRoundDTO(String numberOfRepetitions, String difficulty) {
		super();
		this.numberOfRepetitions = numberOfRepetitions;
		this.difficulty = difficulty;
	}

}
