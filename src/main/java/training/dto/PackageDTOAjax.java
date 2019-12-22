package training.dto;

public class PackageDTOAjax {

	private Long id;
	
	private String nameOfPackage;
	
	private Long packageElementId;
	
	private Long elementsInPackages;
	
	private Long numnerOfElements;
	
	public Long getNumnerOfElements() {
		return numnerOfElements;
	}

	public void setNumnerOfElements(Long numnerOfElements) {
		this.numnerOfElements = numnerOfElements;
	}

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

	public Long getElementsInPackages() {
		return elementsInPackages;
	}

	public void setElementsInPackages(Long elementsInPackages) {
		this.elementsInPackages = elementsInPackages;
	}

	public PackageDTOAjax() {}

	public PackageDTOAjax(Long id, String nameOfPackage, Long packageElementId) {
		super();
		this.id = id;
		this.nameOfPackage = nameOfPackage;
		this.packageElementId = packageElementId;
	}

}
