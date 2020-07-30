package training.enumerations;

public enum TrainingStatusEnum {
	READY("Ready","Spreman"),
	CANCELED("Canceled","Odkazan"),
	DONE("Done","Urađen");
	
	private String name;
	private String localisedName;

	public String getName() {
		return name;
	}
	
	public String getLocalisedName() {
		return localisedName;
	}
	
	TrainingStatusEnum(){}
	
	TrainingStatusEnum(final String name, final String localisedName){
		this.name = name;
	}
}
