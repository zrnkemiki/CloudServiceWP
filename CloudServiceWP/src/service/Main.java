package service;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import enums.UserType;
import model.User;
import model.Users;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User u = new User(0, "test", "test", "test", "test", "test", "test", 0, UserType.SUPERADMIN);
		Jsonb jsonb = JsonbBuilder.create();
		Users users = new Users();
		users.getUsers().put(u.getEmail(), u);
		String s = jsonb.toJson(users);
		System.out.println(s);
		

	}

}
