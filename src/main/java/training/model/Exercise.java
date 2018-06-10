package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Exercise")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long exercise_Id;
	
	@Column(name="Name", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name="Description", columnDefinition="VARCHAR(100)")
	private String description;

/*	@ManyToOne
	@JoinColumn(name="grExercise_id", referencedColumnName="exercise_Id", nullable=false)
	private ExerciseGroup grExercise;*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

/*	public ExerciseGroup getGrExercise() {
		return grExercise;
	}

	public void setGrExercise(ExerciseGroup grExercise) {
		this.grExercise = grExercise;
	}*/

	public Exercise(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
}
