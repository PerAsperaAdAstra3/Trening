package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "ElementsInPackages")
public class ElementsInPackages {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long elemInPackagesId;
	
//	@Column(name = "PackageElementId")
//	private Long packageElementId;

	@Column(name = "number")
	private Long number;
	
	@ManyToOne
	@JoinColumn(name = "elementsInPackages")
	private Package package1;
	
	@ManyToOne
	@JoinColumn(name = "elementsInPackagesPE")
	private PackageElement packageElementEIP;
	
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

/*	public Long getPackageElementId() {
		return packageElementId;
	}

	public void setPackageElementId(Long packageElementId) {
		this.packageElementId = packageElementId;
	}*/

	public Package getPackage() {
		return package1;
	}

	public void setPackage(Package package2) {
		this.package1 = package2;
	}
	
	public PackageElement getPackageElementEIP() {
		return packageElementEIP;
	}

	public void setPackageElementEIP(PackageElement packageElementEIP) {
		this.packageElementEIP = packageElementEIP;
	}

	public ElementsInPackages() {}
	
	public ElementsInPackages(Long packageElementId, Package package2) {
		super();
//		this.packageElementId = packageElementId;
		this.package1 = package2;
	}
	
}
