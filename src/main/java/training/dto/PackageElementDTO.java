package training.dto;

public class PackageElementDTO {

	private Long packageElementID;
	
	private String name;
	
	private String description;
	
	private Long packageId;

	public Long getPackageElementID() {
		return packageElementID;
	}

	public void setPackageElementID(Long id) {
		this.packageElementID = id;
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
