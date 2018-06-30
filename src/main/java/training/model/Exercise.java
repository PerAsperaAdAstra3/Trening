package training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="Exercise")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long exercise_Id;
	
	@Column(name="Name", columnDefinition="VARCHAR(40)")
	private String name;
	
	@Column(name="Description", columnDefinition="VARCHAR(100)")
	private String description;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="exerInRound")
	private ExerciseInRound exerciseInRound;

	@ManyToMany(cascade = {CascadeType.ALL})
	private List<ExerciseGroup> exerciseGroups;
	
	public ExerciseInRound getExerciseInRound() {
		return exerciseInRound;
	}

	public void setExerciseInRound(ExerciseInRound exerciseInRound) {
		this.exerciseInRound = exerciseInRound;
	}

	public Long getExercise_Id() {
		return exercise_Id;
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
