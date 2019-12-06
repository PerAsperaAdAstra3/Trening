package training.dto;

public class PackageDTOAjax {

	private Long id;
	
	private String nameOfPackage;
	
	private Long packageElementId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfPackage() {
		return nameOfPackage;
	}

	public void setNameOfPackage(String nameOfPackage) {
		this.nameOfPackage = nameOfPackage;
	}

	public Long getPackageElementId() {
		return packageElementId;
	}

	public void setPackageElementId(Long packageElementId) {
		this.packageElementId = packageElementId;
	}

	public PackageDTOAjax() {}

	public PackageDTOAjax(Long id, String nameOfPackage, Long packageElementId) {
		super();
		this.id = id;
		this.nameOfPackage = nameOfPackage;
		this.packageElementId = packageElementId;
	}

}
