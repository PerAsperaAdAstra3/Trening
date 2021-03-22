package training.dto;

import java.util.Date;
import java.util.List;

import training.model.ClientPackageElement;

public class ClientPackageDTO {

	private Long id;
	
	private Long packageId;
	
	private Long clientId;
	
	private Long priceOfClientPackage;
	
	private String nameOfPackage;
	
	private boolean payed;
	
	private String clientPackageActive;
	
	private List<ClientPackageElement> clientPackageElements;

	private String purchaseDate;
	
	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Long getPriceOfClientPackage() {
		return priceOfClientPackage;
	}

	public void setPriceOfClientPackage(Long priceOfClientPackage) {
		this.priceOfClientPackage = priceOfClientPackage;
	}

	public boolean getPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public String getClientPackageActive() {
		return clientPackageActive;
	}

	public void setClientPackageActive(String clientPackageActive) {
		this.clientPackageActive = clientPackageActive;
	}

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
