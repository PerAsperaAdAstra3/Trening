package training.dto;

public class PackageElementDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private Long packageId;

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

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public PackageElementDTO() {}
	
	public PackageElementDTO(String name, String description, Long packageId) {
		super();
		this.name = name;
		this.description = description;
		this.packageId = packageId;
	}

}
