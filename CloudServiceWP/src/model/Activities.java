package model;

import java.util.HashMap;

public class Activities {

	private HashMap<Integer, Activity> activities; 

	public Activities(HashMap<Integer, Activity> activities) {
		super();
		this.activities = activities;
	}

	public Activities() {
		activities = new HashMap<Integer, Activity>();
	}

	public HashMap<Integer, Activity> getActivities() {
		return activities;
	}

	public void setActivities(HashMap<Integer, Activity> activities) {
		this.activities = activities;
	}
	
	

}
