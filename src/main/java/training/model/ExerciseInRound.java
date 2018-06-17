package training.model;

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

	public Long getExecInRound_Id() {
		return ExecInRound_Id;
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
