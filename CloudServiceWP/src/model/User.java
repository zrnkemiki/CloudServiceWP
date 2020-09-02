package model;

import enums.UserType;

public class User {

	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String picture;
	private int organizationId;
	private UserType userType;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String email, String password, String firstName, String lastName, String phone, String picture,
			int organizationId, UserType userType) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.picture = picture;
		this.organizationId = organizationId;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getOrganization() {
		return organizationId;
	}

	public void setOrganization(int organizationId) {
		this.organizationId = organizationId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
