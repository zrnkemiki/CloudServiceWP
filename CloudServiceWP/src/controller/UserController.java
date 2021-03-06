package controller;

import java.io.InputStream;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import dto.CredentialsDTO;
import dto.OrganizationDTO;
import dto.UserDTO;
import enums.UserType;
import model.User;
import service.OrganizationService;
import service.UserService;

@Path("/")
public class UserController {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	

	@GET
	@Path("/user/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		Collection<User> allUsers = null;
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if(logged.getUserType() == UserType.SUPERADMIN) {
			allUsers = UserService.getUsers(ctx).getUsers().values();
		}
		else {
			allUsers = UserService.getUsersForAdmin(ctx, logged).getUsers().values();
		}
		return Response.ok(allUsers).build();
	}
	
	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(CredentialsDTO credentialsDto) {
		User loggedUser = UserService.authenticate(credentialsDto, ctx, request);
		if (loggedUser == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		System.out.println("Ulogovani korisnik" + loggedUser.getEmail());
		return Response.ok(loggedUser).build();
	}
	
	@POST
	@Path("/logout")
	public Response logout() {
		request.getSession().removeAttribute("loggedUser");
		return Response.ok().build();
	}
	
	@POST
	@Path("/user/logged")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoggedUser() {
		User logged = UserService.getLoggedUser(request);
		if (logged == null) {
			return Response.status(Response.Status.FORBIDDEN).entity(null).build();
		} else {
			return Response.status(Response.Status.OK).entity(logged).build();
		}
	}
	
	@GET
	@Path("/user/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByEmail(@PathParam("email") String email) {
		System.out.println("Usao sam u get user by mail" + email);
		User user = UserService.getUserByEmail(email, ctx);
		System.out.println("Vracam usera" + user.toString());
		return Response.status(Response.Status.OK).entity(user).build();
		
	}
	
	
	@POST
	@Path("/user/{email}/edit")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editUser(UserDTO edited, @PathParam("email") String email) {
		return UserService.editUserProfile(edited, email, ctx, request);
	}
	
	@DELETE
	@Path("/user/{email}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("email") String email) {
		return UserService.deleteUser(email, request, ctx);
	}
	
	
	@POST
	@Path("/uploadImage/{pictureName}/{option}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(@FormDataParam("pictureFile") InputStream uploadedInputStream,
								@FormDataParam("pictureFile") FormDataContentDisposition fileDetail,
								@PathParam("pictureName") String pictureName,
								@PathParam("option") int option) {
		
		boolean saved = UserService.saveImageToDiskRegister(uploadedInputStream, fileDetail, pictureName, ctx, option);
		if (saved) {
			return Response.ok().build();
		}
		return Response.status(Response.Status.CONFLICT).build();
		
	}
	

	@POST
	@Path("/user/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(UserDTO dto) {
		System.out.println("Usao u add user" + dto.getEmail());
		return UserService.addUser(dto, request, ctx);
	}


}
