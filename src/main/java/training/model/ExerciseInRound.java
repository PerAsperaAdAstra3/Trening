package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "ExerciseInRound")
public class ExerciseInRound {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ExecInRound_Id;
	
	@Column(name="NumberOfRepetitions")
	private int numberOfRepetitions;
	
	@Column(name="Difficulty")
	private String difficulty;

	@Column(name="ExerciseID")
	private Long exerciseID;
	
	@Column(name="ExerciseName")
	private String exerciseName;
	
//	@OneToMany(mappedBy = "roundExerciseInRound")
//	private List<Round> rounds = new ArrayList<Round>();
	
	@ManyToOne
	@JoinColumn(name="roundExerciseInRound")
	private Round round;
	
	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Long getExecInRound_Id() {
		return ExecInRound_Id;
	}

	public Long getExerciseId() {
		return exerciseID;
	}

	public void setExerciseId(Long exercise) {
		this.exerciseID = exercise;
	}
	
	public void setExecInRound_Id(Long execInRound_Id) {
		ExecInRound_Id = execInRound_Id;
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
	
	public ExerciseInRound() {}
	
	public ExerciseInRound(int numberOfRepetitions, String difficulty) {
		super();
		this.numberOfRepetitions = numberOfRepetitions;
		this.difficulty = difficulty;
	}
	
}
