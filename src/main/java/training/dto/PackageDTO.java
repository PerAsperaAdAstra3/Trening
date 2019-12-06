package training.dto;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {

	private Long id;
	
	private String nameOfPackage;
	
	private List<PackageElementDTO> packageElementList = new ArrayList<PackageElementDTO>();
	
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

	public List<PackageElementDTO> getPackageElementList() {
		return packageElementList;
	}

	public void setPackageElementList(PackageElementDTO packageElementList) {
		this.packageElementList.add(packageElementList);
	}

	public PackageDTO() {}

	public PackageDTO(String nameOfPackage) {
		super();
		this.nameOfPackage = nameOfPackage;
	}
	
}
