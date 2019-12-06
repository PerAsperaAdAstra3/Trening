package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity(name="ClientPackage")
public class ClientPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "packageUnitCP")
	private Package packageUnitCP;
	
	@ManyToOne
	@JoinColumn(name = "client")
	private Client client;
	
	@OneToMany(mappedBy = "clientPackage")
	private List<ClientPackageElement> clientPackageElementsCP = new ArrayList<ClientPackageElement>();

	public Long getId() {
		return id;
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
