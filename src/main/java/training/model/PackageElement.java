package training.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name="PackageElement")
public class PackageElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long packageElementID;
	
	@Column(name="name")
	private String packageElementName;
	
	@Column(name="description")
	private String description;

	@OneToMany(mappedBy = "packageElementEIP", cascade = CascadeType.ALL)
	private List<ElementsInPackages> elementsInPackagesPE;
	
	@Column(name="isprotected")
	private Boolean isProtected = false;
	
	public Boolean isIsProtected() {
		return isProtected;
	}

	public void setIsProtected(Boolean isProtected) {
		this.isProtected = isProtected;
	}

	public Long getPackageElementID() {
		return packageElementID;
	}

	public void setPackageElementID(Long id) {
		this.packageElementID = id;
	}

	public String getPackageElementName() {
		return packageElementName;
	}

	public void setPackageElementName(String packageElementName) {
		this.packageElementName = packageElementName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<ElementsInPackages> getElementsInPackagesPE() {
		return elementsInPackagesPE;
	}

	public void setElementsInPackagesPE(ElementsInPackages elementsInPackagesPE) {
		elementsInPackagesPE.setPackageElementEIP(this);
		this.elementsInPackagesPE.add(elementsInPackagesPE);
	}

	public PackageElement() {}

	public PackageElement(String name, String description) {
		super();
		this.packageElementName = name;
		this.description = description;
	}
		
}
