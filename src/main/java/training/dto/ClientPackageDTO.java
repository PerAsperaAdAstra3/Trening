package training.dto;

import java.util.List;

import training.model.ClientPackageElement;

public class ClientPackageDTO {

	private Long id;
	
	private Long packageId;
	
	private Long clientId;
	
	private String nameOfPackage;
	
	private String payed;
	
	private String clientPackageStatus;
	
	public String getPayed() {
		return payed;
	}

	public void setPayed(String payed) {
		this.payed = payed;
	}

	public String getClientPackageStatus() {
		return clientPackageStatus;
	}

	public void setClientPackageStatus(String clientPackageStatus) {
		this.clientPackageStatus = clientPackageStatus;
	}

	private List<ClientPackageElement> clientPackageElements;

	public String getNameOfPackage() {
		return nameOfPackage;
	}

	public void setNameOfPackage(String nameOfPackage) {
		this.nameOfPackage = nameOfPackage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public List<ClientPackageElement> getClientPackageElements() {
		return clientPackageElements;
	}

	public void setClientPackageElements(ClientPackageElement clientPackageElement) {
		this.clientPackageElements.add(clientPackageElement);
	}

	public ClientPackageDTO() {
		
	}
	
	public ClientPackageDTO(Long packageId, Long clientId) {
		super();
		this.packageId = packageId;
		this.clientId = clientId;
	}
	
}
