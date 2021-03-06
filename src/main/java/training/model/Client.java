package training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Client")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Name")
	private String name;

	@Column(name = "FamilyName")
	private String familyName;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@OneToMany(mappedBy = "client")
	private List<ClientPackage> clientPackages = new ArrayList<ClientPackage>();

	@OneToMany(mappedBy = "client")
	private List<Training> trainingList = new ArrayList<Training>();
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Training> getTrainingList() {
		return trainingList;
	}

	public void addTrainingList(Training training) {
		training.setClient(this);
		this.trainingList.add(training);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClientPackage> getClientPackages() {
		return clientPackages;
	}

	public void addClientPackages(ClientPackage clientPackage) {
		this.clientPackages.add(clientPackage);
	}
	
	public Client() {}
	
	public Client(String name, String familyName) {
		super();
		this.name = name;
		this.familyName = familyName;
	}
}
