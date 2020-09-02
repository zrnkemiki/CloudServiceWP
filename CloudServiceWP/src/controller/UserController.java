package controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
	public void test() {
		UserService.getUsers(ctx);
	}

}
