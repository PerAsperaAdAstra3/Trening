package training.dto;

public class ClientDTO {

	private Long id;
	
	private String name;
	
	private String familyName;
	
	private String email;
	
	private String phoneNumber;

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
	
	public ClientDTO(){}

	public ClientDTO(String name, String familyName) {
		super();
		this.name = name;
		this.familyName = familyName;
	}
		
}
