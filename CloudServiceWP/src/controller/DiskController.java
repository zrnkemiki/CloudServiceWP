package controller;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.DiskDTO;
import dto.VirtualMachineDTO;
import enums.UserType;
import model.Disk;
import model.User;
import model.VirtualMachine;
import service.DiskService;
import service.VirtualMachineService;
@Path("/")
public class DiskController {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;

	@GET
	@Path("/disk/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDisks() {
		Collection<Disk> alldisks = null;
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == UserType.SUPERADMIN) {
			alldisks = DiskService.getDisks(ctx).getDisks().values();
			return Response.ok(alldisks).build();
		} else if (logged.getUserType() == UserType.ADMIN || logged.getUserType() == UserType.USER) {
			return Response.ok(DiskService.getDiskByUser(request, ctx)).build();
		}
		return Response.status(Response.Status.FORBIDDEN).build();
	}
	
	@GET
	@Path("/disk/allFree")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFreeDisks() {
		System.out.println("Usao sam u get free disks");
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if(logged == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		return Response.ok(DiskService.getFreeDisks(ctx, request)).build();
	}

	@GET
	@Path("/disk/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDiskById(@PathParam("id") int id) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		Disk disk = DiskService.getDiskByID(id, ctx);
		System.out.println("Usao u get disk by id controller");
		return Response.status(Response.Status.OK).entity(disk).build();

	}
	
	@POST
	@Path("/disk/{id}/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response diskEdit(VirtualMachineDTO dto, @PathParam("id") int id) {
		System.out.println("Usao u Disk edit controller");
		return VirtualMachineService.editVirtualMachine(dto, id, request, ctx);
	}
	
	@DELETE
	@Path("/disk/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteVM(@PathParam("id") int id) {
		return DiskService.deleteDisk(id, ctx, request);
	}
	
	@POST
	@Path("/disk/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDisk(DiskDTO dto) {
		return DiskService.addDisk(dto, ctx, request);
	}


}
