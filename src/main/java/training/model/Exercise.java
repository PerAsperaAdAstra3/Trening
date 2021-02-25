package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="Exercise")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="exerciseGroup")
	private ExerciseGroup exerciseGroup;
	
	@OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
	private List<ExerciseInRound> ExerciseInRoundList = new ArrayList<ExerciseInRound>();
	
	public List<ExerciseInRound> getExerciseInRoundList() {
		return ExerciseInRoundList;
	}

	public void setExerciseInRoundList(List<ExerciseInRound> exerciseInRoundList) {
		ExerciseInRoundList = exerciseInRoundList;
	}

	public ExerciseGroup getExerciseGroup() {
		return exerciseGroup;
	}
	
	public void setExerciseGroup(ExerciseGroup exerciseGroup) {
		this.exerciseGroup = exerciseGroup;
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
