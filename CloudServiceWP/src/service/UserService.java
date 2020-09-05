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

import dto.CredentialsDTO;
import dto.UserDTO;
import enums.UserType;
import model.User;
import model.Users;

public class UserService {

	private static BufferedWriter bw;
	private static FileWriter fw;

	// SUPER ADMIN:
	// moze da dodaje korisnika ili admina
	// ADMIN:
	// isto moze da dodaje i korisnika i admina
	// ali je polje 'organizacija' uvek inicijalizovano na
	// organizaciju samog admina koji poziva funkciju
	public static User addUser(UserDTO dto, HttpServletRequest request, ServletContext ctx) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == UserType.ADMIN) {
			dto.setOrganization(logged.getOrganization());
		}
		// dto.setOrganization(OrganizationService.getOrganizationID(dto.getOrganizationId()));
		User newUser = new User(dto);
		Users allUsers = getUsers(ctx);
		newUser.setId(allUsers.getUsers().size() + 1);
		allUsers.getUsers().put(newUser.getEmail(), newUser);

		saveUsers(ctx, allUsers);
		return newUser;
	}

	// SUPER ADMINISTRATOR:
	// ne moze da menja organizaciju i email
	// ADMIN ORGANIZACIJE:
	// sve isto, samo moze da menja usere samo iz svoje organizacije
	public static User editUser(UserDTO dto) {

		return null;
	}

	// super admin ili admin
	// korisnik ne moze da obrise samog sebe --> error 400
	public static Response deleteUser(String email, HttpServletRequest request, ServletContext ctx) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getEmail().equalsIgnoreCase(email)) {
			return Response.status(Response.Status.FORBIDDEN).build();
		} 
		else {
			Users allUsers = getUsers(ctx);
			User u = allUsers.getUsers().get(email);
			allUsers.getUsers().remove(email);
			saveUsers(ctx, allUsers);
			OrganizationService.removeUserFromOrganization(u, ctx);
			return Response.status(Response.Status.OK).build();
		}
	}

	// korisnik menja svoj profil
	public static Response editUserProfile(UserDTO edited, String email, ServletContext ctx,
			HttpServletRequest request) {
		Users allUsers = getUsers(ctx);

		if (allUsers.getUsers().containsKey(edited.getEmail())) {
			// ako postoji user sa tim email-om hendlaj u error na frontu poruku
			return Response.status(Response.Status.CONFLICT).build();
		}

		// ako neko polje nije popunjeno, vrati gresku
		if (edited.getFirstName().equals("") || edited.getLastName().equals("") || edited.getPassword().equals("")) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		User userForEdit = getUserByEmail(email, ctx);

		if (userForEdit == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		userForEdit.setEmail(edited.getEmail());
		userForEdit.setFirstName(edited.getFirstName());
		userForEdit.setLastName(edited.getLastName());
		userForEdit.setPassword(edited.getPassword());

		allUsers.getUsers().remove(email);
		allUsers.getUsers().put(userForEdit.getEmail(), userForEdit);
		saveUsers(ctx, allUsers);

		request.getSession().removeAttribute("loggedUser");
		request.getSession().setAttribute("loggedUser", userForEdit);

		return Response.status(Response.Status.OK).build();
	}

	public static User getLoggedUser(HttpServletRequest request) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		return logged;
	}

	private static Users loadUsers(String path) {
		path += "data/users.txt".replace("/", System.getProperty("file.separator"));
		BufferedReader in = null;
		Users users = null;
		try {
			in = new BufferedReader(new FileReader(path));

			String s = in.readLine();
			Jsonb jsonb = JsonbBuilder.create();
			users = jsonb.fromJson(s, Users.class);
			System.out.println("Ovo je u Users" + users.getUsers().keySet());

			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;

	}

	public static User getUserByEmail(String email, ServletContext ctx) {
		Users allUsers = getUsers(ctx);
		User found = allUsers.getUsers().get(email);
		return found;
	}

	// SUPER ADMIN:
	// prikaz svih korisnika iz svih organizacija
	// ADMIN:
	// prikaz svih korisnika iz svoje organizacije
	// samo se prosledi param u zavisnosti od ulogovanog korisnika

	// Pozeljno izmeniti da radi sa iteratorom!!!
	public static Users getUsersForAdmin(ServletContext ctx, User admin) {
		Users allUsers = (Users) ctx.getAttribute("users");
		Users returnUsers = new Users();
		if (allUsers == null) {
			allUsers = loadUsers(ctx.getRealPath(""));
			ctx.setAttribute("users", allUsers);
		}
		for (String key : allUsers.getUsers().keySet()) {
			if (allUsers.getUsers().get(key).getOrganization().getId() == admin.getOrganization().getId()
					&& allUsers.getUsers().get(key).getUserType() != UserType.SUPERADMIN) {
				returnUsers.getUsers().put(allUsers.getUsers().get(key).getEmail(), allUsers.getUsers().get(key));
			}
		}
		return returnUsers;
	}

	public static Users getUsers(ServletContext ctx) {
		Users allUsers = (Users) ctx.getAttribute("users");
		if (allUsers == null) {
			allUsers = loadUsers(ctx.getRealPath(""));
			ctx.setAttribute("users", allUsers);
		}

		return allUsers;
	}

	public static void saveUsers(ServletContext ctx, Users allUsers) {
		String path = ctx.getRealPath("") + "data/users.txt".replace("/", System.getProperty("file.separator"));
		System.out.println("Ovo je putanja: " + path);
		String data = "";
		Jsonb jsonb = JsonbBuilder.create();
		data = jsonb.toJson(allUsers);
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.removeAttribute("users");
	}

	public static User authenticate(CredentialsDTO credentialsDto, ServletContext ctx, HttpServletRequest request) {
		System.out.println(credentialsDto.getEmail() + credentialsDto.getPassword());
		String email = credentialsDto.getEmail();
		String password = credentialsDto.getPassword();

		Users users = getUsers(ctx);

		for (User u : users.getUsers().values()) {
			if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedUser", u);
				return u;
			}
		}

		return null;
	}

}
