package model;

import java.util.HashMap;

import javax.servlet.ServletContext;

public class Users {
	
	private HashMap<String, User> users = new HashMap<String, User>();
	
	

	public Users() {
	}

	

	public HashMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}
	
	
	

}
