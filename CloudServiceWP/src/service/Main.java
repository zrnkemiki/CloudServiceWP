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
		Organization o1 = new Organization(1, "Organizacija 1",  "Organizacija 1",  "Organizacija 1", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o2 = new Organization(2, "Organizacija 2",  "Organizacija 2",  "Organizacija 2", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o3 = new Organization(3, "Organizacija 3", "Organizacija 3",  "Organizacija 3", new ArrayList<Integer>(), new ArrayList<Integer>());
		User u1 = new User(0, "admin1", "a", "test", "test", "test", "test", o, UserType.ADMIN);
		User u2 = new User(1, "superadmin", "a", "test", "test", "test", "test", o, UserType.SUPERADMIN);
		User u3 = new User(2, "user1", "a", "test", "test", "test", "test", o, UserType.USER);
		User u4 = new User(3, "user2", "a", "test", "test", "test", "test", o, UserType.USER);
		User u = new User(4, "user3", "a", "test", "test", "test", "test", o1, UserType.USER);
		User u5 = new User(5, "user4", "a", "test", "test", "test", "test", o2, UserType.USER);
		User u6 = new User(6, "admin3", "a", "test", "test", "test", "test", o1, UserType.ADMIN);
		User u7 = new User(7, "admin4", "a", "test", "test", "test", "test", o2, UserType.ADMIN);
		Jsonb jsonb = JsonbBuilder.create();
		Organizations org = new Organizations();
		org.getOrganizations().put(o.getId(), o);
		org.getOrganizations().put(o1.getId(), o1);
		org.getOrganizations().put(o2.getId(), o2);
		org.getOrganizations().put(o3.getId(), o3);
		String s = jsonb.toJson(org);
		System.out.println(s);

		Users users = new Users();
		users.getUsers().put(u.getEmail(), u);
		users.getUsers().put(u1.getEmail(), u1);
		users.getUsers().put(u2.getEmail(), u2);
		users.getUsers().put(u3.getEmail(), u3);
		users.getUsers().put(u4.getEmail(), u4);
		users.getUsers().put(u5.getEmail(), u5);
		users.getUsers().put(u6.getEmail(), u6);
		users.getUsers().put(u7.getEmail(), u7);
		String us = jsonb.toJson(users);
		System.out.println(us);
		Organizations organizations = jsonb.fromJson(s, Organizations.class);
		System.out.println(organizations.getOrganizations().size());
		Organization nova = org.getOrganizations().get(0);
		System.out.println(nova.getAbout());
		
		

	}

}
