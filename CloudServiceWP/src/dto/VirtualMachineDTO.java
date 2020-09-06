package dto;

import java.util.List;

import model.Disk;

public class VirtualMachineDTO {

	private String name;
	private int categoryId;
	private int organizationId;
	private List<Disk> disks;

	public VirtualMachineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public VirtualMachineDTO(String name, int categoryId, int organizationId, List<Disk> disks) {
		super();
		this.name = name;
		this.categoryId = categoryId;
		this.organizationId = organizationId;
		this.disks = disks;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public List<Disk> getDisks() {
		return disks;
	}

	public void setDisks(List<Disk> disks) {
		this.disks = disks;
	}

}
