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

import dto.UserDTO;
import dto.VirtualMachineDTO;
import enums.UserType;
import model.User;
import model.VirtualMachine;
import service.UserService;
import service.VirtualMachineService;

@Path("/")
public class VirtualMachinesController {

	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;

	@GET
	@Path("/vms/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllVirtualMachines() {
		Collection<VirtualMachine> allVMS = null;
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == UserType.SUPERADMIN) {
			allVMS = VirtualMachineService.getVirtualMachines(ctx).getVms().values();
			return Response.ok(allVMS).build();
		} else if (logged.getUserType() == UserType.ADMIN || logged.getUserType() == UserType.USER) {
			return Response.ok(VirtualMachineService.getVMSbyUser(request, ctx)).build();
		}
		return Response.status(Response.Status.FORBIDDEN).build();
	}

	@GET
	@Path("/vm/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVMByID(@PathParam("id") int id) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		VirtualMachine vm = VirtualMachineService.getVirtualMachineByID(id, ctx);
		return Response.status(Response.Status.OK).entity(vm).build();

	}
	
	@POST
	@Path("/vm/{id}/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editVM(VirtualMachineDTO dto, @PathParam("id") int id) {
		System.out.println("Usao u VM controller");
		return VirtualMachineService.editVirtualMachine(dto, id, request, ctx);
	}
	
	@DELETE
	@Path("/vm/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteVM(@PathParam("id") int id) {
		
		return Response.status(Response.Status.OK).build();
	}

}
