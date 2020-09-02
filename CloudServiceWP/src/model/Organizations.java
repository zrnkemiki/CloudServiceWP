package model;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Organizations {

	private HashMap<Integer, Organization> organizations = new HashMap<Integer, Organization>();
	
	public Organizations() {
		
	}

	public Organizations(HashMap<Integer, Organization> organizations) {
		super();
		this.organizations = organizations;
	}

	public HashMap<Integer, Organization> getOrganizationsAsMap() {
		return organizations;
	}

	public void setOrganizations(HashMap<Integer, Organization> organizations) {
		this.organizations = organizations;
	}
	
	/*
	 *  vraca sve organizacije iz mape u formatu ArrayList
	 */
	public List<Organization> getOrganizationsAsList() {
		return this.organizations.values().stream().collect(Collectors.toList());
	}
	
	
}
