package training.dto;

import java.util.ArrayList;
import java.util.List;

public class ExerciseInRoundDTO {

	private int numberOfRepetitions;

	private String difficulty;
	
	private List<String> execNameList = new ArrayList<String>();

	private List<String> roundNameList = new ArrayList<String>();

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
	
	public List<String> getExecNameList() {
		return execNameList;
	}

	public void addExecName(String execName) {
		this.execNameList.add(execName);
	}

	public List<String> getRoundNameList() {
		return roundNameList;
	}

	public void addRoundName(String roundName) {
		this.roundNameList.add(roundName);
	}
	
	public ExerciseInRoundDTO() {}
	
	public ExerciseInRoundDTO(int numberOfRepetitions, String difficulty) {
		super();
		this.numberOfRepetitions = numberOfRepetitions;
		this.difficulty = difficulty;
	}

}
