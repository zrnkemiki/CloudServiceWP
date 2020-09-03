package model;

import java.util.List;

public class Organization {

	private int id;
	private String name;
	private String about;
	private String logo;
	private List<Integer> users;
	private List<Integer> vms;

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Organization(int id, String name, String about, String logo, List<Integer> users, List<Integer> vms) {
		super();
		this.id = id;
		this.name = name;
		this.about = about;
		this.logo = logo;
		this.users = users;
		this.vms = vms;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<Integer> getUsers() {
		return users;
	}

	public void setUsers(List<Integer> users) {
		this.users = users;
	}

	public List<Integer> getVms() {
		return vms;
	}

	public void setVms(List<Integer> vms) {
		this.vms = vms;
	}

	
	
	

}
