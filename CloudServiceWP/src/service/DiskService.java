package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import dto.DiskDTO;
import dto.VirtualMachineDTO;
import enums.DiskType;
import enums.UserType;
import model.Disk;
import model.Disks;
import model.User;
import model.VirtualMachine;
import model.VirtualMachines;

public class DiskService {

	private static BufferedWriter bw;
	private static FileWriter fw;

	public static Disks getDisks(ServletContext ctx) {
		Disks disks = (Disks) ctx.getAttribute("disks");
		if (disks == null) {
			disks = loadDisks(ctx.getRealPath(""));
			ctx.setAttribute("disks", disks);
		}

		return disks;
	}

	public static Disks loadDisks(String path) {
		path += "/data/disks.txt";
		BufferedReader in = null;
		Disks disks = null;
		try {
			in = new BufferedReader(new FileReader(path));

			String s = in.readLine();
			Jsonb jsonb = JsonbBuilder.create();
			disks = jsonb.fromJson(s, Disks.class);

			in.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return disks;
	}

	public static void saveDisks(ServletContext ctx, Disks allDisks) {
		String path = ctx.getRealPath("") + "/data/disks.txt";

		String data = "";
		Jsonb jsonb = JsonbBuilder.create();
		data = jsonb.toJson(allDisks);
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.removeAttribute("disks");
	}

	public static Disk getDiskByID(int id, ServletContext ctx) {
		Disks disks = getDisks(ctx);
		Disk disk = null;
		Collection<Disk> collectionDisks = disks.getDisks().values();
		for (Disk diskTemp : collectionDisks) {
			if (diskTemp.getId() == id) {
				disk = diskTemp;
			}
		}
		return disk;
	}

	public static Object getDiskByUser(HttpServletRequest request, ServletContext ctx) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		Disks disks = null;
		List<Disk> returnDisks = new ArrayList<Disk>();
		if (logged.getUserType() == UserType.ADMIN || logged.getUserType() == UserType.USER) {
			disks = getDisks(ctx);
			Collection<Disk> collectionDisks = disks.getDisks().values();
			for (Disk diskTemp : collectionDisks) {
				if (diskTemp.getOrganization().getId() == logged.getOrganization().getId()) {
					returnDisks.add(diskTemp);
				}
			}
		}
		return returnDisks;
	}

	public static Response addDisk(DiskDTO dto, ServletContext ctx, HttpServletRequest request) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == UserType.ADMIN) {
			dto.setOrganizationId(logged.getOrganization().getId());
		} else if (logged.getUserType() == UserType.SUPERADMIN) {
			System.out.println("Korisnik je superadmin");
		} else {
			return Response.status(Response.Status.FORBIDDEN).build();
		}

		if (dto.getName().equals("")) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Disk disk = new Disk();
		Disks disks = getDisks(ctx);

		disk.setId(disks.getDisks().size() + 1);
		disk.setName(dto.getName());
		System.out.println(dto.getVmId());

		disk.setCapacity(dto.getCapacity());
		disk.setOrganization(OrganizationService.getOrganizationByID(dto.getOrganizationId(), ctx));
		if (dto.getDiskType() == 1) {
			disk.setDiskType(DiskType.HDD);
		} else {
			disk.setDiskType(DiskType.SSD);
		}
		if (dto.getVmId() != -1) {
			disk.setVmId(dto.getVmId());
			VirtualMachineService.addDiskToMachine(disk, dto.getVmId(), ctx);
		} else {
			disk.setVmId(-1);
		}
		disks.getDisks().put(disk.getId(), disk);
		saveDisks(ctx, disks);

		return Response.status(Response.Status.CREATED).build();

	}

	public static void removeVMfromDisk(int id, ServletContext ctx) {
		Disks disks = getDisks(ctx);
		for (Object key : disks.getDisks().keySet()) {
			if (disks.getDisks().get(key).getVmId() == id) {
				disks.getDisks().get(key).setVmId(-1);
			}
		}

		saveDisks(ctx, disks);
		System.out.println("Disks updated.");

	}

	public static Response deleteDisk(int diskId, ServletContext ctx, HttpServletRequest request) {
		Disks disks = getDisks(ctx);
		Disk disk = getDiskByID(diskId, ctx);
		int vmId = disk.getVmId();
		disks.getDisks().remove(String.valueOf(diskId));
		if (vmId != -1) {
			VirtualMachineService.removeDiskFromMachine(vmId, diskId, ctx);
		}
		return Response.status(Response.Status.OK).build();
	}

	public static Collection<Disk> getFreeDisks(ServletContext ctx, HttpServletRequest request) {
		Disks disks = getDisks(ctx);
		User logged = (User) request.getSession().getAttribute("loggedUser");
		List<Disk> freeDisks = new ArrayList<Disk>();
		if (logged.getUserType() == UserType.SUPERADMIN) {
			for (Object key : disks.getDisks().keySet()) {
				if (disks.getDisks().get(key).getVmId() == -1) {
					freeDisks.add(disks.getDisks().get(key));
				}
			}
		} else if (logged.getUserType() == UserType.ADMIN) {
			for (Object key : disks.getDisks().keySet()) {
				if (disks.getDisks().get(key).getVmId() == -1
						&& disks.getDisks().get(key).getOrganization().getId() == logged.getOrganization().getId()) {
					freeDisks.add(disks.getDisks().get(key));
				}
			}
		}

		return freeDisks;
	}

	public static Response editDisk(DiskDTO dto, int id, HttpServletRequest request, ServletContext ctx) {
		Disk disk = getDiskByID(id, ctx);
		// ako neko polje nije popunjeno, vrati gresku
		if (dto.getName().equals("")) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		if (disk == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		disk.setName(dto.getName());
		disk.setCapacity(dto.getCapacity());
		if (dto.getDiskType() == 1) {
			disk.setDiskType(DiskType.HDD);
		} else if (dto.getDiskType() == 2) {
			disk.setDiskType(DiskType.SSD);
		}
		if (disk.getVmId() == dto.getVmId()) {
			VirtualMachineService.updateVmDisk(disk, dto.getVmId(), ctx);
		} else {
			VirtualMachineService.removeDiskFromMachine(disk.getVmId(), disk.getId(), ctx);
			VirtualMachineService.addDiskToMachine(disk, dto.getVmId(), ctx);
		}
		disk.setVmId(dto.getVmId());
		Disks disks = getDisks(ctx);
		disks.getDisks().replace(disk.getId(), disk);
		saveDisks(ctx, disks);

		return Response.status(Response.Status.OK).build();
	}

}
