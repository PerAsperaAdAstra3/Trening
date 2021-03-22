package training.enumerations;

public enum ClientPackageStateEnum {
	ACTIVE("Aktivan", "A"),
	NOTACTIVE("Neaktivan", "N");
	
	private String nameText;
	
	private String shortName;

	public String getShortName() {
		return shortName;
	}
	
	public String getNameText() {
		return nameText;
	}
	
	public static ClientPackageStateEnum fromShortName(String shortName) {
		switch(shortName) {
		case "A" :
			return ClientPackageStateEnum.ACTIVE;
		case "B" :
			return ClientPackageStateEnum.NOTACTIVE;
		default : 
			throw new IllegalArgumentException("ShortName [" + shortName
                    + "] not supported.");
		}
	}
	
	ClientPackageStateEnum(){}
	
	ClientPackageStateEnum(final String nameText, final String shortName){
		this.nameText = nameText;
		this.shortName = shortName;
	}
}
