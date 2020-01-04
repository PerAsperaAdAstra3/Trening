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
	private String name;
	
	@Column(name="description")
	private String description;

	@OneToMany(mappedBy = "packageElementEIP", cascade = CascadeType.ALL)
	private List<ElementsInPackages> elementsInPackagesPE;
	
	public Long getPackageElementID() {
		return packageElementID;
	}

	public void setPackageElementID(Long id) {
		this.packageElementID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		this.name = name;
		this.description = description;
	}
		
}
