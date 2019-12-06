package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name="ClientPackageElement")
public class ClientPackageElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="counter")
	private Integer counter;
	
	@ManyToOne
	@JoinColumn(name="clientPackage")
	private ClientPackage clientPackage;

	@ManyToOne
	@JoinColumn(name="packageElement")
	private PackageElement packageElement;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PackageElement getPackageElement() {
		return packageElement;
	}

	public void setPackageElement(PackageElement packageElement) {
		this.packageElement = packageElement;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public ClientPackage getClientPackage() {
		return clientPackage;
	}

	public void setClientPackage(ClientPackage clientPackage) {
		this.clientPackage = clientPackage;
	}

	public ClientPackageElement() {}
	
	public ClientPackageElement(PackageElement packageElement, Integer counter) {
		super();
		this.packageElement = packageElement;
		this.counter = counter;
	}	
}
