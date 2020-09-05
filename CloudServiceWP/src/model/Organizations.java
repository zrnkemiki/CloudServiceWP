package model;

import java.util.HashMap;

public class Organizations {

	private HashMap<Integer, Organization> organizations = new HashMap<Integer, Organization>();
	
	public Organizations() {
		
	}

	public Organizations(HashMap<Integer, Organization> organizations) {
		super();
		this.organizations = organizations;
	}

	public HashMap<Integer, Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(HashMap<Integer, Organization> organizations) {
		this.organizations = organizations;
	}

	
	
}
