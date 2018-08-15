package training.model;

import java.util.ArrayList;
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

@Entity(name = "Training")
public class Training {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Date")
	private Date date;
	
	@Column(name="NumberOfTrainings")
	private int numberOfTrainings;
	
	@ManyToOne
	@JoinColumn(name = "trainingList")
	private Client client;
	
	@OneToMany(mappedBy = "trainingTask")
	private List<Task> tasks = new ArrayList<Task>();
	
    @OneToMany(mappedBy = "trainingRound")
	private List<Round> rounds = new ArrayList<Round>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getNumberOfTrainings() {
		return numberOfTrainings;
	}
	
	public void setNumberOfTrainings(int numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void addTasks(Task task) {
		task.setTraining(this);
		tasks.add(task);
	}
	
	public List<Round> getRounds() {
		return rounds;
	}
	
	public void addRound(Round round) {
		round.setTraining(this);
		rounds.add(round);
	}
	
	public Training() {}
	
	public Training(Date date, int numberOfTrainings) {
		super();
		this.date = date;
		this.numberOfTrainings = numberOfTrainings;
	}
	
}
