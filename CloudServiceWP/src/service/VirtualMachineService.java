package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import dto.VirtualMachineDTO;
import enums.UserType;
import model.Disk;
import model.User;
import model.VirtualMachine;
import model.VirtualMachines;

public class VirtualMachineService {

	private static BufferedWriter bw;
	private static FileWriter fw;

	/*
	 * SUPER ADMIN: pregled svih VM iz svih organizacija ADMIN: pregled svih iz
	 * svoje organizacije KORISNIK: isto kao i admin, samo ne postoji opcija za
	 * dodavanje nove VM (ovo je za front)
	 * 
	 * ovo isto moze da vraca ili VirtualMachines ili List<VirtualMachine>
	 */
	public static List<VirtualMachine> getVMSbyUser(HttpServletRequest request, ServletContext ctx) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		VirtualMachines vms = null;
		List<VirtualMachine> returnVms = new ArrayList<VirtualMachine>();
		if (logged.getUserType() == UserType.ADMIN || logged.getUserType() == UserType.USER) {
			vms = getVirtualMachines(ctx);
			Collection<VirtualMachine> collectionvms = vms.getVms().values();
			for (VirtualMachine vmTemp : collectionvms) {
				if (vmTemp.getOrganization().getId() == logged.getOrganization().getId()) {
					returnVms.add(vmTemp);
				}
			}
		}
		return returnVms;
	}

	/*
	 * SUPER ADMIN: menjanje svih polja osim organizacije jezgro, ram i gpu se
	 * menjaju ISKLJUCIVO kroz kategoriju! moze da pregleda i direktno menja listu
	 * aktivnosti ADMIN: sve isto samo za VM iz svoje organizacije i moze samo da
	 * pregleda aktivnosti, ne moze da ih menja KORISNIK: moze samo da vidi VM ne
	 * moze nista da menja
	 */
	public static Response editVirtualMachine(VirtualMachineDTO dto, int id, HttpServletRequest request, ServletContext ctx) {
		VirtualMachine vm = getVirtualMachineByID(id, ctx);
		// ako neko polje nije popunjeno, vrati gresku
		if (dto.getName().equals("")) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (vm == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		vm.setName(dto.getName());
		vm.setCategory(CategoryService.getCategoryByID(dto.getCategoryId(), ctx));
		vm.setNumberOfCores(vm.getCategory().getNumberOfCores());
		vm.setNumberOfGpuCores(vm.getNumberOfGpuCores());
		vm.setRam(vm.getRam());
		
		VirtualMachines vms = getVirtualMachines(ctx);
		vms.getVms().replace(vm.getId(), vm);
		saveVirtualMachines(ctx, vms);
		

		return Response.status(Response.Status.OK).build();
	}

	/*
	 * SUPER ADMIN: isto kao ADMIN samo mora da odabere organizaciju ADMIN: vidi u
	 * dokumentaciji
	 */
	public static Response addVirtualMachine(VirtualMachineDTO dto, ServletContext ctx, HttpServletRequest request) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == UserType.ADMIN) {
			dto.setOrganizationId(logged.getOrganization().getId());
		}
		else if(logged.getUserType() == UserType.SUPERADMIN){
			System.out.println("Korisnik je super admin.");
		}
		else {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		
		if(dto.getName().equals("")) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		VirtualMachine vm = new VirtualMachine();
		VirtualMachines vms = getVirtualMachines(ctx);
		vm.setId(vms.getVms().size() +1);
		vm.setName(dto.getName());
		vm.setCategory(CategoryService.getCategoryByID(dto.getCategoryId(), ctx));
		vm.setOrganization(OrganizationService.getOrganizationByID(dto.getOrganizationId(), ctx));
		vm.setNumberOfCores(vm.getCategory().getNumberOfCores());
		vm.setNumberOfGpuCores(vm.getCategory().getNumberOfGpuCores());
		vm.setRam(vm.getCategory().getRam());
		
		
		if(!dto.getDisks().isEmpty()) {
			for (Integer i : dto.getDisks()) {
				vm.getDisks().add(DiskService.getDiskByID(i, ctx));
			}
		}

		vms.getVms().put(vm.getId(), vm);
		
		saveVirtualMachines(ctx, vms);
		return Response.status(Response.Status.CREATED).build();
		

	}

	// SUPER ADMIN ili ADMIN
	public static Response deleteVirtualMachine(int id, HttpServletRequest request, ServletContext ctx) {
		VirtualMachines vms = getVirtualMachines(ctx);
		vms.getVms().remove(String.valueOf(id));
		saveVirtualMachines(ctx, vms);
		System.out.println("Virtual machines updated.");
		DiskService.removeVMfromDisk(id, ctx);
		return Response.status(Response.Status.OK).build();
	}

	// nzm sta ce sve od params da prima za sad
	public static List<VirtualMachine> filterAndSearch() {

		return null;
	}

	public static VirtualMachines getVirtualMachines(ServletContext ctx) {
		VirtualMachines vms = (VirtualMachines) ctx.getAttribute("virtualmachines");
		if (vms == null) {
			vms = loadVirtualMachines(ctx.getRealPath(""));
			ctx.setAttribute("virtualmachines", vms);
		}

		return vms;
	}

	public static VirtualMachines loadVirtualMachines(String path) {
		path += "/data/vms.txt";
		BufferedReader in = null;
		VirtualMachines vms = null;
		try {
			in = new BufferedReader(new FileReader(path));

			String s = in.readLine();
			Jsonb jsonb = JsonbBuilder.create();
			vms = jsonb.fromJson(s, VirtualMachines.class);

			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vms;
	}

	public static void saveVirtualMachines(ServletContext ctx, VirtualMachines allvms) {
		String path = ctx.getRealPath("") + "/data/vms.txt";

		String data = "";
		Jsonb jsonb = JsonbBuilder.create();
		data = jsonb.toJson(allvms);
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.removeAttribute("virtualmachines");
	}

	public static VirtualMachine getVirtualMachineByID(int id, ServletContext ctx) {
		VirtualMachines vms = getVirtualMachines(ctx);
		VirtualMachine vm = null;
		Collection<VirtualMachine> collectionvms = vms.getVms().values();
		for (VirtualMachine vmTemp : collectionvms) {
			if (vmTemp.getId() == id) {
				vm = vmTemp;
			}
		}
		return vm;
	}

	public static void removeDiskFromMachine(int vmId, int diskId, ServletContext ctx) {
		VirtualMachines vms = getVirtualMachines(ctx);
		Iterator i = vms.getVms().get(String.valueOf(vmId)).getDisks().iterator();
		while (i.hasNext()) {
			Disk d = (Disk) i.next();
			if (d.getId() == diskId){
				i.remove();
			}
		saveVirtualMachines(ctx, vms);
		System.out.println("VMS Updated");
		}
		
	}

	public static boolean checkForCategoryConflict(int id, ServletContext ctx) {
		boolean flag = false;
		VirtualMachines vms = getVirtualMachines(ctx);
		Collection<VirtualMachine> collection = vms.getVms().values();
		for (VirtualMachine virtualMachine : collection) {
			if(virtualMachine.getCategory().getId() == id) {
				return flag = true;
			}
		}
		return flag;
		
	}

	public static void addDiskToMachine(Disk disk, int vmId, ServletContext ctx) {
		VirtualMachines vms = getVirtualMachines(ctx);
		VirtualMachine vm = getVirtualMachineByID(vmId, ctx);
		vm.getDisks().add(disk);
		vms.getVms().replace(vm.getId(), vm);
		saveVirtualMachines(ctx, vms);
		
	}
}
