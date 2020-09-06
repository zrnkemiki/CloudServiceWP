package model;

import java.util.HashMap;

public class Activities {

	private HashMap<Integer, Activity> activities = new HashMap<Integer, Activity>();

	public Activities(HashMap<Integer, Activity> activities) {
		super();
		this.activities = activities;
	}

	public Activities() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, Activity> getActivities() {
		return activities;
	}

	public void setActivities(HashMap<Integer, Activity> activities) {
		this.activities = activities;
	}
	
	

}
