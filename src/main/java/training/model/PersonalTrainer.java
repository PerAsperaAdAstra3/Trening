package training.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "PersonalTrainer")
public class PersonalTrainer {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@Column(name = "Name")
	private String name;
	@Column(name = "FamilyName")
	private String familyName;
	@Column(name = "PhoneNumber")
	private String phoneNumber;
	@Column(name = "Email")
	private String email;
	@OneToMany(mappedBy = "personalTrainer", cascade = CascadeType.ALL)
	private List<Appointment> appointmentList;
	
	public PersonalTrainer() {}

	public PersonalTrainer(Long id, String name, String familyName, String phoneNumber, String email,
			List<Appointment> appointmentList) {
		super();
		this.id = id;
		this.name = name;
		this.familyName = familyName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.appointmentList = appointmentList;
	}

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

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Appointment> getAppointments() {
		return appointmentList;
	}

	public void addAppointments(Appointment appointment) {
		this.appointmentList.add(appointment);
	}

}
