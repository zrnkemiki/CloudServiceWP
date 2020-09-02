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

import dto.UserDTO;
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
	public static User addUser(UserDTO dto) {
	
		return null;
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
	public static User deleteUser(String email) {
		
		return null;
	}
	
	
	//  korisnik menja svoj profil
	public static User editUserProfile() {
		
		return null;
	}
	
	private static Users loadUsers(String path) {
		path += "/data/users.txt";
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
	
	
	// SUPER ADMIN:
	// prikaz svih korisnika iz svih organizacija
	// ADMIN:
	// prikaz svih korisnika iz svoje organizacije
	// samo se prosledi param u zavisnosti od ulogovanog korisnika
	
	public static Users getUsers(ServletContext ctx) {
		Users allUsers = (Users) ctx.getAttribute("users");
		if (allUsers == null) {
			allUsers = loadUsers(ctx.getRealPath(""));
			ctx.setAttribute("users", allUsers);
		}
	
		System.out.println("Ovo je u userService" + allUsers.getUsers().size());
		return allUsers;	
	}
	
	public static void writeUsers(ServletContext ctx) {
		String path = ctx.getRealPath("") + "/data/users.txt";
		Users allUsers = (Users) ctx.getAttribute("users");
		if (allUsers == null) {
			allUsers = loadUsers(ctx.getRealPath(""));
			ctx.setAttribute("users", allUsers);
		}
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

}
