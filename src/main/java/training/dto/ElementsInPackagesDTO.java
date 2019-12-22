package training.dto;

public class ElementsInPackagesDTO {

	private Long elemInPackagesId;
	
	private Long packageElementId;

	private String packageElementName;
	
	private String packageElementDescription;

	private Long number;
	
	private Long packageId;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getElemInPackagesId() {
		return elemInPackagesId;
	}

	public void setElemInPackagesId(Long elemInPackagesId) {
		this.elemInPackagesId = elemInPackagesId;
	}

	public Long getPackageElementId() {
		return packageElementId;
	}

	public void setPackageElementId(Long packageElementId) {
		this.packageElementId = packageElementId;
	}

	public String getPackageElementName() {
		return packageElementName;
	}

	public void setPackageElementName(String packageElementName) {
		this.packageElementName = packageElementName;
	}

	public String getPackageElementDescription() {
		return packageElementDescription;
	}

	public void setPackageElementDescription(String packageElementDescription) {
		this.packageElementDescription = packageElementDescription;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public ElementsInPackagesDTO() {}
	
	public ElementsInPackagesDTO(Long elemInPackagesId, Long packageElementId, String packageElementName,
			String packageElementDescription, Long packageId) {
		super();
		this.elemInPackagesId = elemInPackagesId;
		this.packageElementId = packageElementId;
		this.packageElementName = packageElementName;
		this.packageElementDescription = packageElementDescription;
		this.packageId = packageId;
	}
	
}
