package service;

import java.util.ArrayList;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import enums.UserType;
import model.Organization;
import model.Organizations;
import model.User;
import model.Users;
import model.VirtualMachine;

public class Main {

	public static void main(String[] args) {		
		
		// TODO Auto-generated method stub
		Organization o = new Organization(0, "organizacija",  "organizacija",  "organizacija", new ArrayList<Integer>(), new ArrayList<Integer>());
		User u1 = new User(0, "test1", "a", "test", "test", "test", "test", o, UserType.ADMIN);
		User u2 = new User(1, "test2", "a", "test", "test", "test", "test", o, UserType.SUPERADMIN);
		User u3 = new User(2, "test3", "a", "test", "test", "test", "test", o, UserType.USER);
		User u4 = new User(3, "test4", "a", "test", "test", "test", "test", o, UserType.USER);
		User u = new User(4, "test", "a", "test", "test", "test", "test", o, UserType.USER);
		Jsonb jsonb = JsonbBuilder.create();
		Organizations org = new Organizations();
		org.getOrganizations().put(o.getId(), o);
		String s = jsonb.toJson(org);
		System.out.println(s);

		Users users = new Users();
		users.getUsers().put(u.getEmail(), u);
		users.getUsers().put(u1.getEmail(), u1);
		users.getUsers().put(u2.getEmail(), u2);
		users.getUsers().put(u3.getEmail(), u3);
		users.getUsers().put(u4.getEmail(), u4);
		String us = jsonb.toJson(users);
		System.out.println(us);
		Organizations organizations = jsonb.fromJson(s, Organizations.class);
		System.out.println(organizations.getOrganizations().size());
		

	}

}
