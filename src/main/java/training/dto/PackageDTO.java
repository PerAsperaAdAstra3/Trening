package training.dto;

public class PackageDTO {

	private Long id;
	
	private String nameOfPackage;

	private Long price;
	
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
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

	public PackageDTO() {}

	public PackageDTO(String nameOfPackage) {
		super();
		this.nameOfPackage = nameOfPackage;
	}
	
}
