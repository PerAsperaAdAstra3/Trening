package training.dto;

import training.enumerations.TrainingStatusEnum;
import training.model.Operator;

public class TrainingDTO {

	private Long id;
	
	private String date;
	
	private int numberOfTrainings;
	
	private String client;
	
	private String clientFamilyName;

	private String clientId;
	
	private TrainingStatusEnum status;

    private Operator trainingCreator;

    private Operator trainingExecutor;
    
    private Boolean circularYN;
    
	public Boolean isCircularYN() {
		return circularYN;
	}

	public void setCircularYN(Boolean circularYN) {
		this.circularYN = circularYN;
	}

	public Operator getTrainingCreator() {
		return trainingCreator;
	}

	public void setTrainingCreator(Operator trainingCreator) {
		this.trainingCreator = trainingCreator;
	}

	public Operator getTrainingExecutor() {
		return trainingExecutor;
	}

	public void setTrainingExecutor(Operator trainingExecutor) {
		this.trainingExecutor = trainingExecutor;
	}

	public String getClientFamilyName() {
		return clientFamilyName;
	}

	public void setClientFamilyName(String clientFamilyName) {
		this.clientFamilyName = clientFamilyName;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumberOfTrainings() {
		return numberOfTrainings;
	}

	public void setNumberOfTrainings(int numberOfTrainings) {
		this.numberOfTrainings = numberOfTrainings;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public TrainingStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TrainingStatusEnum status) {
		this.status = status;
	}

	public TrainingDTO() {}
	
	public TrainingDTO(Long id, String date, int numberOfTrainings) {
		super();
		this.date = date;
		this.numberOfTrainings = numberOfTrainings;
	}
}
