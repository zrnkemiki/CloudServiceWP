package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import dto.OrganizationDTO;
import enums.UserType;
import model.Organization;
import model.Organizations;
import model.User;

public class OrganizationService {

	private static BufferedWriter bw;
	private static FileWriter fw;

	public static Organizations getOrganizationsForFrontEnd(HttpServletRequest request, ServletContext ctx) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		Organizations organizations = new Organizations();
		if (logged.getUserType() == UserType.ADMIN) {
			organizations.getOrganizations().put(logged.getOrganization().getId(), logged.getOrganization());
		} else if (logged.getUserType() == UserType.SUPERADMIN) {
			organizations = getOrganizations(ctx);
		}

		return organizations;
	}

	public static Organizations getOrganizations(ServletContext ctx) {
		Organizations organizations = (Organizations) ctx.getAttribute("organizations");
		if (organizations == null) {
			organizations = loadOrganizations(ctx.getRealPath(""));
			ctx.setAttribute("organizations", organizations);
		}

		return organizations;
	}

	public static Organizations loadOrganizations(String path) {
		path += "/data/organizations.txt";
		BufferedReader in = null;
		Organizations organizations = null;
		try {
			in = new BufferedReader(new FileReader(path));

			String s = in.readLine();
			Jsonb jsonb = JsonbBuilder.create();
			organizations = jsonb.fromJson(s, Organizations.class);

			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return organizations;
	}

	public static void saveOrganizations(ServletContext ctx, Organizations allOrganizations) {
		String path = ctx.getRealPath("") + "/data/organizations.txt";

		String data = "";
		Jsonb jsonb = JsonbBuilder.create();
		data = jsonb.toJson(allOrganizations);
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.removeAttribute("organizations");
	}

	public static Organization getOrganizationByID(int id, ServletContext ctx) {
		Organizations organizations = getOrganizations(ctx);
		Organization organization = organizations.getOrganizations().get(id);
		return organization;
	}

	// SUPER ADMIN:
	public static Response addOrganization(OrganizationDTO dto, HttpServletRequest request, ServletContext ctx) {
		User u = (User) request.getSession().getAttribute("loggedUser");
		if (u.getUserType() != UserType.SUPERADMIN) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		Organization org = new Organization(dto);
		Organizations organizations = getOrganizations(ctx);
		org.setId(organizations.getOrganizations().size() + 1);
		organizations.getOrganizations().put(org.getId(), org);
		saveOrganizations(ctx, organizations);

		return Response.status(Response.Status.CREATED).build();
	}

	public static void removeUserFromOrganization(User u, ServletContext ctx) {
		Organization organization = getOrganizationByID(u.getOrganization().getId(), ctx);
		for (int i = 0; i < organization.getUsers().size(); i++) {
			if (organization.getUsers().get(i) == u.getId()) {
				organization.getUsers().remove(i);
			}
		}
		Organizations organizations = getOrganizations(ctx);
		organizations.getOrganizations().put(organization.getId(), organization);
		saveOrganizations(ctx, organizations);

	}

	// SUPER ADMIN:
	// ADMIN:
	// slicno i jedan i drugi
	// moze da prima DTO ili Organization zavisi kako bude lakse
	public static Organization editOrganization() {

		return null;
	}

}
