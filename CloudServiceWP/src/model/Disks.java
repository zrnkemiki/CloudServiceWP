package model;

import java.util.HashMap;

public class Disks {

	
	private HashMap<Integer, Disk> disks = new HashMap<Integer, Disk>();

	public Disks(HashMap<Integer, Disk> disks) {
		super();
		this.disks = disks;
	}

	public Disks() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, Disk> getDisks() {
		return disks;
	}

	public void setDisks(HashMap<Integer, Disk> disks) {
		this.disks = disks;
	}
	
	
}
