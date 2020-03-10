package training.enumerations;

public enum Roles {
	TRAINER("TRENER"),
	ADMIN("ADMIN"),
	FRONTDESK("RECEPCIJA");
	
	private String nameText;

	public String getNameText() {
		return nameText;
	}

	Roles(){}
	
	Roles(final String nameText) {
		  this.nameText = nameText;
		  }
}
