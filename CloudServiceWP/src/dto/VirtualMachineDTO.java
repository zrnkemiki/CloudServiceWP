package dto;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachineDTO {

	private String name;
	private int categoryId;
	private int organizationId;
	private List<Integer> disks;

	public VirtualMachineDTO() {
		super();
	}

	public String getName() {
		return name;
	}

	

	public VirtualMachineDTO(String name, int categoryId, int organizationId, List<Integer> disks) {
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

	public List<Integer> getDisks() {
		return disks;
	}

	public void setDisks(List<Integer> disks) {
		this.disks = disks;
	}

	

}
