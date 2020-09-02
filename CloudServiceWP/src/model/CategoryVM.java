package model;

public class CategoryVM {

	private int id;
	private String name;
	private int numberOfCores;
	private int ram;
	private int numberOfGpuCores;

	public CategoryVM() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryVM(int id, String name, int numberOfCores, int ram, int numberOfGpuCores) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfCores = numberOfCores;
		this.ram = ram;
		this.numberOfGpuCores = numberOfGpuCores;
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

}
