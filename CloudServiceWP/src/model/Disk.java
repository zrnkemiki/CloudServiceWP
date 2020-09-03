package model;

import enums.DiskType;

public class Disk {

	private int id;
	private String name;
	private Organization organization;
	private DiskType diskType;
	private int capacity;

	public Disk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Disk(int id, String name, Organization organization, DiskType diskType, int capacity, VirtualMachine vm) {
		super();
		this.id = id;
		this.name = name;
		this.organization = organization;
		this.diskType = diskType;
		this.capacity = capacity;
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

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public DiskType getDiskType() {
		return diskType;
	}

	public void setDiskType(DiskType diskType) {
		this.diskType = diskType;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


}
