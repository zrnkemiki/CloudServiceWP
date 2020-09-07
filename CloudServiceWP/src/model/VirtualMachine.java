package model;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachine {

	private int id;
	private String name;
	private Organization organization;
	private CategoryVM category;
	private int numberOfCores;
	private int ram;
	private int numberOfGpuCores;
	private List<Disk> disks;
	private List<Activity> activities;

	public VirtualMachine() {
		super();
		disks = new ArrayList<Disk>();
		activities = new ArrayList<Activity>();
	}

	public VirtualMachine(int id, String name, Organization organization, CategoryVM category, int numberOfCores,
			int ram, int numberOfGpuCores, List<Disk> disks, List<Activity> activities) {
		super();
		this.id = id;
		this.name = name;
		this.organization = organization;
		this.category = category;
		this.numberOfCores = numberOfCores;
		this.ram = ram;
		this.numberOfGpuCores = numberOfGpuCores;
		this.disks = disks;
		this.activities = activities;
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

	public CategoryVM getCategory() {
		return category;
	}

	public void setCategory(CategoryVM category) {
		this.category = category;
	}

	public int getNumberOfCores() {
		return numberOfCores;
	}

	public void setNumberOfCores(int numberOfCores) {
		this.numberOfCores = numberOfCores;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getNumberOfGpuCores() {
		return numberOfGpuCores;
	}

	public void setNumberOfGpuCores(int numberOfGpuCores) {
		this.numberOfGpuCores = numberOfGpuCores;
	}

	public List<Disk> getDisks() {
		return disks;
	}

	public void setDisks(List<Disk> disks) {
		this.disks = disks;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

}
