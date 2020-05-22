package training.dto;

public class ClientPackageElementDTO {

	private Long id;

	private Integer count;
	
	private String name;
	
	private Integer activeLeft;
	
	private String description;
	
	private String clientPackageElementStatus;

	private Long clientPackageId;

	private Long elementsInPackagesId;
	
	public Integer getActiveLeft() {
		return activeLeft;
	}

	public void setActiveLeft(Integer activeLeft) {
		this.activeLeft = activeLeft;
	}

	public Long getElementsInPackagesId() {
		return elementsInPackagesId;
	}

	public void setElementsInPackagesId(Long elementsInPackagesId) {
		this.elementsInPackagesId = elementsInPackagesId;
	}

	public Long getClientPackageId() {
		return clientPackageId;
	}

	public void setClientPackageId(Long clientPackageId) {
		this.clientPackageId = clientPackageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getClientPackageElementStatus() {
		return clientPackageElementStatus;
	}

	public void setClientPackageElementStatus(String clientPackageElementStatus) {
		this.clientPackageElementStatus = clientPackageElementStatus;
	}

	public ClientPackageElementDTO() {}
}
