package dto;

public class DiskDTO {

	private String name;
	private int vmId;
	private int capacity;
	private int organizationId;
	private int diskType;

	public DiskDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DiskDTO(String name, int vmId, int capacity, int organizationId, int diskType) {
		super();
		this.name = name;
		this.vmId = vmId;
		this.capacity = capacity;
		this.organizationId = organizationId;
		this.diskType = diskType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVmId() {
		return vmId;
	}

	public void setVmId(int vmId) {
		this.vmId = vmId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public int getDiskType() {
		return diskType;
	}

	public void setDiskType(int diskType) {
		this.diskType = diskType;
	}

}
