package dto;

import java.util.List;

import model.Disk;

public class VirtualMachineDTO {
	
	private String name;
	private int categoryId;
	private List<Disk> disks;
	
	public VirtualMachineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VirtualMachineDTO(String name, int categoryId, List<Disk> disks) {
		super();
		this.name = name;
		this.categoryId = categoryId;
		this.disks = disks;
	}
	public String getName() {
		return name;
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
