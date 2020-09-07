package controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import service.ActivityService;
@Path("/")
public class ActivityController {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	
	
	@DELETE
	@Path("/activity/{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteActivity(@PathParam("id") int id) {
		return ActivityService.deleteActivity(id, ctx);
		 
	}
	


}
