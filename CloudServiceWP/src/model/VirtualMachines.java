package model;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class VirtualMachines {

	private HashMap<Integer, VirtualMachine> vms = new HashMap<Integer, VirtualMachine>();
	
	public VirtualMachines() {
		
	}

	public VirtualMachines(HashMap<Integer, VirtualMachine> vms) {
		super();
		this.vms = vms;
	}

	public HashMap<Integer, VirtualMachine> getVms() {
		return vms;
	}

	public void setVms(HashMap<Integer, VirtualMachine> vms) {
		this.vms = vms;
	}

	
}
