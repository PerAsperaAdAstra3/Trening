package training.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ExerciseGroup")
public class ExerciseGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name", columnDefinition = "VARCHAR(40)")
	private String name;

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
