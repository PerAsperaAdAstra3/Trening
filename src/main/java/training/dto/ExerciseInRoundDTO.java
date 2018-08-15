package training.dto;

public class ExerciseInRoundDTO {

	private int numberOfRepetitions;

	private String difficulty;
	
	private String exerciseInRoundExerciseName;
	
	private Long exerciseInRoundExerciseId;

	private String roundName;

	private Long roundId;
	
	public Long getexerciseInRoundExerciseId() {
		return exerciseInRoundExerciseId;
	}

	public void setExerciseInRoundExerciseId(Long exerciseId) {
		this.exerciseInRoundExerciseId = exerciseId;
	}

	public Long getRoundId() {
		return roundId;
	}

	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}

	public int getNumberOfRepetitions() {
		return numberOfRepetitions;
	}

	public void setNumberOfRepetitions(int numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}

	public String getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}	
	
	public String getExerciseInRoundExerciseName() {
		return exerciseInRoundExerciseName;
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
	
	public ExerciseInRoundDTO(int numberOfRepetitions, String difficulty) {
		super();
		this.numberOfRepetitions = numberOfRepetitions;
		this.difficulty = difficulty;
	}

}
