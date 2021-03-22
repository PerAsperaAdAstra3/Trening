package training.dto;

public class PackageElementDTO {

	private Long packageElementID;
	
	private String packageElementName;
	
	private String description;
	
	private Long packageId;
	
	private boolean isProtected = false;
	
	public boolean isIsProtected() {
		return isProtected;
	}

	public void setIsProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public Long getPackageElementID() {
		return packageElementID;
	}

	public void setPackageElementID(Long id) {
		this.packageElementID = id;
	}

	public String getPackageElementName() {
		return packageElementName;
	}

	public void setPackageElementName(String packageElementName) {
		this.packageElementName = packageElementName;
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
	
	public PackageElementDTO(String packageElementName, String description, Long packageId) {
		super();
		this.packageElementName = packageElementName;
		this.description = description;
		this.packageId = packageId;
	}

}
