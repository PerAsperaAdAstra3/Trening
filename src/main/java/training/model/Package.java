package training.model;

import java.util.ArrayList;
import java.util.List;

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
	private String nameOfPackage;
	
	@OneToMany(mappedBy = "packageUnitPE")
	private List<PackageElement> packageElements = new ArrayList<PackageElement>();
	
	@OneToMany(mappedBy = "packageUnitCP")
	private List<ClientPackage> clientPackages = new ArrayList<ClientPackage>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<PackageElement> getPackageElements() {
		return packageElements;
	}

	public void addPackageElements(PackageElement packageElement) {
		this.packageElements.add(packageElement);
	}

	public List<ClientPackage> getClientPackages() {
		return clientPackages;
	}

	public void addClientPackages(ClientPackage clientPackage) {
		this.clientPackages.add(clientPackage);
	}

	public String getNameOfPackage() {
		return nameOfPackage;
	}

	public void setNameOfPackage(String nameOfPackage) {
		this.nameOfPackage = nameOfPackage;
	}

	public Package() {}
	
	public Package(String nameOfPackage, List<PackageElement> packageElements) {
		super();
		this.nameOfPackage = nameOfPackage;
		this.packageElements = packageElements;
	}
	
}
