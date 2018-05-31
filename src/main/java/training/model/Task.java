package training.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy="grVezbi")
	private Set<ExerciseGroup> exerciseGroup = new HashSet<ExerciseGroup>();
	
	@Column(name="IsRoundExercise")
	private boolean isRoundExercise;
	
	
}
