package training.dto;

import java.util.ArrayList;
import java.util.List;

public class PackageDTO {

	private Long id;
	
	private String nameOfPackage;
	
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

	public PackageDTO() {}

	public PackageDTO(String nameOfPackage) {
		super();
		this.nameOfPackage = nameOfPackage;
	}
	
}
