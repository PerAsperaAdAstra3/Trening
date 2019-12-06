package training.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity(name="PackageElement")
public class PackageElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(mappedBy = "packageElement")
	private List<ClientPackageElement> clientPackageElementsPE; 
	
	@ManyToOne
	@JoinColumn(name="packageUnitPE")
	private Package packageUnitPE;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public List<ClientPackageElement> getClientPackageElements() {
		return clientPackageElementsPE;
	}

	public void addClientPackageElements(ClientPackageElement clientPackageElement) {
		this.clientPackageElementsPE.add(clientPackageElement);
	}

	public PackageElement() {}

	public PackageElement(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
		
}
