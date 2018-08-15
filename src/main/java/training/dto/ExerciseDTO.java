package training.dto;

public class ExerciseDTO {

	private Long id;
	private String name;
	private String description;
	private String exerciseGroup;
	private Long exerciseGroupId;
	private Integer colorCode;

	public Integer getColorCode() {
		return colorCode;
	}

	public void setColorCode(Integer colorCode) {
		this.colorCode = colorCode;
	}

	public ExerciseDTO() {}
	
	public ExerciseDTO(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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
	public String getExerciseGroup() {
		return exerciseGroup;
	}
	public void setExerciseGroup(String exerciseGroup) {
		this.exerciseGroup = exerciseGroup;
	}
	public Long getExerciseGroupId() {
		return exerciseGroupId;
	}
	public void setExerciseGroupId(Long exerciseGroupId) {
		this.exerciseGroupId = exerciseGroupId;
	}
}
