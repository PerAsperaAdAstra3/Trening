package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Exercise")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Name", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name="Description", columnDefinition="VARCHAR(100)")
	private String description;

	@ManyToOne
	@JoinColumn(name="exerInRound")
	private ExerciseInRound exerciseInRound;

	@ManyToOne
	@JoinColumn(name="exerciseGroup")
	private ExerciseGroup exerciseGroup;
	
	public ExerciseGroup getExerciseGroup() {
		return exerciseGroup;
	}

	public void setExerciseGroup(ExerciseGroup exerciseGroup) {
		this.exerciseGroup = exerciseGroup;
	}

	public ExerciseInRound getExerciseInRound() {
		return exerciseInRound;
	}

	public void setExerciseInRound(ExerciseInRound exerciseInRound) {
		this.exerciseInRound = exerciseInRound;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

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

	public Exercise() {}
	
	public Exercise(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
}
