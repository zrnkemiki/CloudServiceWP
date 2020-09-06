package controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.OrganizationDTO;
import enums.UserType;
import model.Organization;
import model.User;
import service.OrganizationService;

@Path("/")
public class OrganizationController {

	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;

	@GET
	@Path("/organization/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllOrganizations() {
		Collection<Organization> allOrganizations = new ArrayList<Organization>();
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		} else if (logged.getUserType() == UserType.SUPERADMIN) {
			allOrganizations = OrganizationService.getOrganizations(ctx).getOrganizations().values();
			return Response.ok(allOrganizations).build();
		} else if (logged.getUserType() == UserType.ADMIN) {
			allOrganizations.add(OrganizationService.getOrganizationByID(logged.getOrganization().getId(), ctx));
			return Response.ok(allOrganizations).build();
		}
		return Response.status(Response.Status.FORBIDDEN).build();
	}

	@GET
	@Path("/organization/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizationByID(@PathParam("id") int id) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		Organization org = OrganizationService.getOrganizationByID(id, ctx);
		System.out.println("Org:" + org.getAbout());
		return Response.status(Response.Status.OK).entity(org).build();

	}

	@POST
	@Path("/organization/{id}/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(OrganizationDTO edited, @PathParam("id") int id) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() != UserType.SUPERADMIN || logged.getUserType() != UserType.ADMIN) {
			return OrganizationService.editOrganization(edited, id, ctx, request);
		}
		return Response.status(Response.Status.FORBIDDEN).build();

	}

	@POST
	@Path("/organization/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addOrganization(OrganizationDTO dto) {
		return OrganizationService.addOrganization(dto, request, ctx);
	}

}
