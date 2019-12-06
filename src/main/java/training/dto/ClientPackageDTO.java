package training.dto;

import java.util.List;

import training.model.ClientPackageElement;

public class ClientPackageDTO {

	private Long id;
	
	private Long packageId;
	
	private Long clientId;
	
	private List<ClientPackageElement> clientPackageElements;

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
