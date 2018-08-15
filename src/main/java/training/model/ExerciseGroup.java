package training.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "ExerciseGroup")
public class ExerciseGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name")
	private String name;

	@OneToMany(mappedBy = "exerciseGroup")
	private List<Exercise> exercises = new ArrayList<Exercise>();
		
	@ManyToOne
	@JoinTable(name = "task")
	private Task task;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void addExercise(Exercise exercise) {
		exercise.setExerciseGroup(this);
		this.exercises.add(exercise);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public ExerciseGroup() {}
	
	public ExerciseGroup(String name, Set<Exercise> exerciseGroup) {
		super();
		this.name = name;
	}
}
