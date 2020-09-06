package controller;

import java.util.ArrayList;
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

import dto.CategoryDTO;
import dto.VirtualMachineDTO;
import enums.UserType;
import model.CategoryVM;
import model.User;
import service.CategoryService;
import service.VirtualMachineService;

@Path("/")
public class CategoryController {

	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;

	@DELETE
	@Path("/category/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@PathParam("id") int id) {
		return CategoryService.deleteCategory(id, ctx, request);
	}

	@GET
	@Path("/category/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCategories() {
		Collection<CategoryVM> allCategories = new ArrayList<CategoryVM>();
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == UserType.SUPERADMIN || logged.getUserType() == UserType.ADMIN
				|| logged.getUserType() == UserType.USER) {
			allCategories = CategoryService.getCategories(ctx).getCategories().values();
			return Response.ok(allCategories).build();
		} else {
			return Response.status(Response.Status.FORBIDDEN).build();
		}

	}
	
	@POST
	@Path("/category/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCategory(CategoryDTO dto) {
		return CategoryService.addCategory(dto, ctx, request);
	}

}
