package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "ExerciseInGroup")
public class ExerciseInRound {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ExecInRound_Id;
	
	@Column(name="NumberOfRepetitions")
	private int numberOfRepetitions;
	
	@Column(name="Difficulty")
	private String difficulty;

	@OneToMany(mappedBy = "exerciseInRound")	
	private List<Exercise> exercises = new ArrayList<Exercise>();
	
	@OneToMany(mappedBy = "roundExerciseInRound")
	private List<Round> rounds = new ArrayList<Round>();
	
	public Long getExecInRound_Id() {
		return ExecInRound_Id;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void addExercise(Exercise exercise) {
		exercise.setExerciseInRound(this);
		exercises.add(exercise);
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void addRounds(Round round) {
		round.setExerciseInRound(this);
		rounds.add(round);
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
