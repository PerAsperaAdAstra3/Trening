package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Package")
public class Package {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String packageName;

	@Column
	private Long price;
	
	@OneToMany(mappedBy = "elementsPackage", cascade = CascadeType.ALL)
	private List<ElementsInPackages> elementsInPackages = new ArrayList<ElementsInPackages>();
	
	@OneToMany(mappedBy = "packageUnitCP", cascade = CascadeType.ALL)
	private List<ClientPackage> clientPackages = new ArrayList<ClientPackage>();

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
	
	public List<ElementsInPackages> getElementsInPackages() {
		return elementsInPackages;
	}

	public void addElementsInPackages(ElementsInPackages elementsInPackages) {
		elementsInPackages.setPackage(this);
		this.elementsInPackages.add(elementsInPackages);
	}

	public List<ClientPackage> getClientPackages() {
		return clientPackages;
	}

	public void addClientPackages(ClientPackage clientPackage) {
		clientPackage.setPackageUnit(this);
		this.clientPackages.add(clientPackage);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Package() {}
	
	public Package(String packageName) {
		super();
		this.packageName = packageName;
	}
	
}
