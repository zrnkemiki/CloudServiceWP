package service;

import java.util.List;

import dto.VirtualMachineDTO;
import model.VirtualMachine;
import model.VirtualMachines;

public class VirtualMachineService {
	
	/*
	 * SUPER ADMIN:
	 * pregled svih VM iz svih organizacija
	 * ADMIN:
	 * pregled svih iz svoje organizacije
	 * KORISNIK:
	 * isto kao i admin, samo ne postoji opcija za dodavanje nove VM (ovo je za front)
	 * 
	 * ovo isto moze da vraca ili VirtualMachines ili List<VirtualMachine>
	 */
	public static VirtualMachines getAllVms() {
		
		return null;
	}
	
	/*
	 * SUPER ADMIN:
	 * menjanje svih polja osim organizacije
	 * jezgro, ram i gpu se menjaju ISKLJUCIVO kroz kategoriju!
	 * moze da pregleda i direktno menja listu aktivnosti 
	 * ADMIN:
	 * sve isto samo za VM iz svoje organizacije
	 * i moze samo da pregleda aktivnosti, ne moze da ih menja
	 * KORISNIK:
	 * moze samo da vidi VM ne moze nista da menja
	 */
	public static VirtualMachine editVirtualMachine() {
		
		return null;
	}
	
	
	/*
	 * SUPER ADMIN:
	 * isto kao ADMIN samo mora da odabere organizaciju
	 * ADMIN:
	 * vidi u dokumentaciji
	 */
	public static VirtualMachine addVirtualMachine(VirtualMachineDTO dto) {
		
		return null;
		
	}
	
	
	// SUPER ADMIN ili ADMIN
	public static VirtualMachine deleteVirtualMachine(int id) {
		
		return null;
	}
	
	
	// nzm sta ce sve od params da prima za sad
	public static List<VirtualMachine> filterAndSearch() {
		
		return null;
	}

}
