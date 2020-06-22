package training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Integer count;

	@Column(name="activeLeft")
	private Integer activeLeft;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="clientPackage")
	private ClientPackage clientPackage;
	
	@Column(name="clientPackageElementStatus")
	private boolean clientPackageElementStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="elementsInPackages")
	private ElementsInPackages elementsInPackages;
	
	public Integer getActiveLeft() {
		return activeLeft;
	}

	public void setActiveLeft(Integer activeLeft) {
		this.activeLeft = activeLeft;
	}

	public boolean isClientPackageElementStatus() {
		return clientPackageElementStatus;
	}

	public void setClientPackageElementStatus(boolean clientPackageElementStatus) {
		this.clientPackageElementStatus = clientPackageElementStatus;
	}

	public ElementsInPackages getElementsInPackages() {
		return elementsInPackages;
	}

	public void setElementsInPackages(ElementsInPackages elementsInPackages) {
		this.elementsInPackages = elementsInPackages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCounter() {
		return count;
	}

	public void setCounter(Integer counter) {
		this.count = counter;
	}

	public ClientPackage getClientPackage() {
		return clientPackage;
	}

	public void setClientPackage(ClientPackage clientPackage) {
		this.clientPackage = clientPackage;
	}

	public ClientPackageElement() {}
	
	public ClientPackageElement(Integer counter) {
		super();
		this.count = counter;
	}	
}
