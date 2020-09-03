package dto;

public class OrganizationDTO {
	private String name;
	private String about;
	private String logo;
	
	
	
	
	public OrganizationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrganizationDTO(String name, String about, String logo) {
		super();
		this.name = name;
		this.about = about;
		this.logo = logo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
}
