package training.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "ExerciseGroup")
public class ExerciseGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", columnDefinition = "VARCHAR(40)")
	private String name;

	@ManyToMany
	@JoinTable(name = "ExerciseGroup_Exercise",
				joinColumns = { @JoinColumn(name = "fk_exerciseGroup") },
				inverseJoinColumns = { @JoinColumn(name = "fk_exercise") })
	private List<Exercise> exerciseList = new ArrayList<Exercise>();
		
	@ManyToOne
	@JoinTable(name = "task")
	private Task task;
	
	public Long getId() {
		return id;
	}

	public List<Exercise> getExerciseList() {
		return exerciseList;
	}

	public void addExercise(Exercise exercise) {
		this.exerciseList.add(exercise);
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
