package training.enumerations;

public enum ClientPackageStateEnum {
	ACTIVE("Aktivan"),
	NOTACTIVE("Neaktivan");
	
	private String nameText;

	public String getNameText() {
		return nameText;
	}
	
	ClientPackageStateEnum(){}
	
	ClientPackageStateEnum(final String nameText){
		this.nameText = nameText;
	}
}
