package training.dto;

public class CleintDTO {

	private String name;
	
	private String familyName;

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

	public CleintDTO(String name, String familyName) {
		super();
		this.name = name;
		this.familyName = familyName;
	}
		
}
