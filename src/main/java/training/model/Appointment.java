package training.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "Appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "appointment")
	private List<Training> trainingListAppointment;
	
	@Column(name="appointmentDate")
	private Date timeOfAppointment;
	
	@ManyToOne
	@JoinColumn(name="appointmentList")
	private PersonalTrainer personalTrainer;
	
	public Appointment() {}

	public Appointment(List<Training> trainingList, Date timeOfAppointment, PersonalTrainer personalTrainer) {
		super();
		this.trainingListAppointment = trainingList;
		this.timeOfAppointment = timeOfAppointment;
		this.personalTrainer = personalTrainer;
	}

	public List<Training> getTrainings() {
		return trainingListAppointment;
	}

	public void addTrainings(Training trainings) {
		this.trainingListAppointment.add(trainings);
	}

	public Date getTimeOfAppointment() {
		return timeOfAppointment;
	}

	public void setTimeOfAppointment(Date timeOfAppointment) {
		this.timeOfAppointment = timeOfAppointment;
	}
	
	public PersonalTrainer getPersonalTrainer() {
		return personalTrainer;
	}

	public void setPersonalTrainer(PersonalTrainer personalTrainer) {
		this.personalTrainer = personalTrainer;
	}
	
}
