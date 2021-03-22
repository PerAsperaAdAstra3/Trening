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

@Entity(name = "ElementsInPackages")
public class ElementsInPackages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long elemInPackagesId;

	@Column(name = "number")
	private Long number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "elementsInPackages")
	private Package elementsPackage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "elementsInPackagesPE")
	private PackageElement packageElementEIP;
	
	@OneToMany(mappedBy = "elementsInPackages", cascade = CascadeType.ALL)
	private List<ClientPackageElement> clientPackageElementList = new ArrayList<ClientPackageElement>();

	public List<ClientPackageElement> getClientPackageElementList() {
		return clientPackageElementList;
	}

	public void setClientPackageElementList(ClientPackageElement clientPackageElement) {
		clientPackageElement.setElementsInPackages(this);
		this.clientPackageElementList.add(clientPackageElement);
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Long getElemInPackagesId() {
		return elemInPackagesId;
	}

	public void setElemInPackagesId(Long elemInPackagesId) {
		this.elemInPackagesId = elemInPackagesId;
	}

	public Package getPackage() {
		return elementsPackage;
	}

	public void setPackage(Package elementsPackage) {
		this.elementsPackage = elementsPackage;
	}
	
	public PackageElement getPackageElementEIP() {
		return packageElementEIP;
	}

	public void setPackageElementEIP(PackageElement packageElementEIP) {
		this.packageElementEIP = packageElementEIP;
	}

	public ElementsInPackages() {}
	
	public ElementsInPackages(Long packageElementId, Package elementsPackage) {
		super();
		this.elementsPackage = elementsPackage;
	}
	
}
