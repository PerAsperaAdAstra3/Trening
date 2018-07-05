package training.dto;

import java.util.ArrayList;
import java.util.List;

public class ExerciseGroupDTO {

	private Long id;
	
	private String name;

	private List<String> exerciseNameList = new ArrayList<String>();
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getExerciseNameList() {
		return exerciseNameList;
	}

	public void addExerciseNameList(String exerciseNameList) {
		this.exerciseNameList.add(exerciseNameList);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ExerciseGroupDTO() {}
	
	public ExerciseGroupDTO(String name) {
		super();
		this.name = name;
	}

}
