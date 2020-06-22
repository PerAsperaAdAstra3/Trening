package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import training.enumerations.ClientPackageStateEnum;


@Entity(name="ClientPackage")
public class ClientPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packageUnitCP")
	private Package packageUnitCP;
	
	@Column(name = "clientPackageStatus")
	private ClientPackageStateEnum clientPackageActive;
	
	@Column(name = "clientPackagePayed")
	private boolean payed = false;
	
	@Column(name = "clientPackagePrice")
	private Long clientPackagePrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client")
	private Client client;
	
	@OneToMany(mappedBy = "clientPackage" , cascade = CascadeType.ALL)
	private List<ClientPackageElement> clientPackageElementsCP = new ArrayList<ClientPackageElement>();

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public Long getClientPackagePrice() {
		return clientPackagePrice;
	}

	public void setClientPackagePrice(Long clientPackagePrice) {
		this.clientPackagePrice = clientPackagePrice;
	}

	public Long getId() {
		return id;
	}

	public ClientPackageStateEnum getClientPackageActive() {
		return clientPackageActive;
	}

	public void setClientPackageActive(ClientPackageStateEnum clientPackageActive) {
		this.clientPackageActive = clientPackageActive;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Package getPackageUnit() {
		return packageUnitCP;
	}

	public void setPackageUnit(Package packageUnit) {
		this.packageUnitCP = packageUnit;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<ClientPackageElement> getClientPackageElements() {
		return clientPackageElementsCP;
	}

	public void addClientPackageElements(ClientPackageElement clientPackageElement) {
		clientPackageElement.setClientPackage(this);
		this.clientPackageElementsCP.add(clientPackageElement);
	}

	public ClientPackage() {}
	
	public ClientPackage(Package packageUnit, Client client, List<ClientPackageElement> clientPackageElements) {
		super();
		this.packageUnitCP = packageUnit;
		this.client = client;
		this.clientPackageElementsCP = clientPackageElements;
	}
	
}
