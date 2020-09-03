package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import dto.VirtualMachineDTO;
import enums.UserType;
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
	public static VirtualMachines getAllVms(HttpServletRequest request, ServletContext ctx) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		VirtualMachines vms = null;
		if (logged.getUserType() == UserType.SUPERADMIN) {

		} else if (logged.getUserType() == UserType.ADMIN) {

		} else {

		}
		return vms;
	}

	/*
	 * SUPER ADMIN: menjanje svih polja osim organizacije jezgro, ram i gpu se
	 * menjaju ISKLJUCIVO kroz kategoriju! moze da pregleda i direktno menja listu
	 * aktivnosti ADMIN: sve isto samo za VM iz svoje organizacije i moze samo da
	 * pregleda aktivnosti, ne moze da ih menja KORISNIK: moze samo da vidi VM ne
	 * moze nista da menja
	 */
	public static VirtualMachine editVirtualMachine() {

		return null;
	}

	/*
	 * SUPER ADMIN: isto kao ADMIN samo mora da odabere organizaciju ADMIN: vidi u
	 * dokumentaciji
	 */
	public static VirtualMachine addVirtualMachine(VirtualMachineDTO dto) {

		return null;

	}

	// SUPER ADMIN ili ADMIN
	public static Response deleteVirtualMachine(int id, HttpServletRequest request, ServletContext ctx) {
		VirtualMachines vms = getAllVms(request, ctx);
		vms.getVms().remove(id);
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
		path += "/data/virtualMachines.txt";
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
	

	public static void saveOrganizations(ServletContext ctx, VirtualMachines allvms) {
		String path = ctx.getRealPath("") + "/data/virtualMachines.txt";

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

}
