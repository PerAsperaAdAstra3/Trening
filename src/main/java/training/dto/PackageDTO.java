package training.dto;

public class PackageDTO {

	private Long id;
	
	private String packageName;

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public PackageDTO() {}

	public PackageDTO(String packageName) {
		super();
		this.packageName = packageName;
	}
	
}
