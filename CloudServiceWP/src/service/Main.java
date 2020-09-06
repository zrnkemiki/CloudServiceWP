package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import enums.DiskType;
import model.Activities;
import model.Activity;
import model.Categories;
import model.CategoryVM;
import model.Disk;
import model.Disks;
import model.Organization;
import model.VirtualMachine;
import model.VirtualMachines;

public class Main {

	public static void main(String[] args) {		
		
		// TODO Auto-generated method stub
		Organization o = new Organization(0, "organizacija",  "organizacija",  "organizacija", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o1 = new Organization(1, "Organizacija 1",  "Organizacija 1",  "Organizacija 1", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o2 = new Organization(2, "Organizacija 2",  "Organizacija 2",  "Organizacija 2", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o3 = new Organization(3, "Organizacija 3", "Organizacija 3",  "Organizacija 3", new ArrayList<Integer>(), new ArrayList<Integer>());
		/*
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
		
		*/
		CategoryVM category = new CategoryVM(0, "Kategorija 1", 8, 2, 4);
		CategoryVM category1 = new CategoryVM(1, "Kategorija 2", 16, 64, 4);
		CategoryVM category2 = new CategoryVM(2, "Kategorija 3", 10, 16, 8);
		
		
		Activity activity = new Activity(0,LocalDateTime.now().minusDays(30), null);
		Activity activity1 = new Activity(1,LocalDateTime.now().minusDays(10), LocalDateTime.now());
		Activity activity2 = new Activity(2,LocalDateTime.now().minusHours(5), LocalDateTime.now());
		

		Disk disk = new Disk(0, "Disk1", o, DiskType.SSD, 2, 1);
		Disk disk1 = new Disk(1, "Disk2", o1, DiskType.SSD, 4, 2);
		Disk disk2 = new Disk(2, "Disk3", o2, DiskType.SSD, 3, 3);
		
		
		
		VirtualMachine vm = new VirtualMachine(1, "VM1", o, category, category.getNumberOfCores(), category.getRam(), category.getNumberOfGpuCores(), new ArrayList<Disk>(), new ArrayList<Activity>());
		VirtualMachine vm1 = new VirtualMachine(2, "VM2", o1, category1, category1.getNumberOfCores(), category1.getRam(), category1.getNumberOfGpuCores(), new ArrayList<Disk>(), new ArrayList<Activity>());
		VirtualMachine vm2 = new VirtualMachine(3, "VM3", o2, category2, category2.getNumberOfCores(), category2.getRam(), category2.getNumberOfGpuCores(), new ArrayList<Disk>(), new ArrayList<Activity>());
		vm.getDisks().add(disk);
		vm1.getDisks().add(disk1);
		vm2.getDisks().add(disk2);
		vm.getActivities().add(activity1);
		vm.getActivities().add(activity2);
		vm.getActivities().add(activity);
		
		Jsonb jsonb = JsonbBuilder.create();
		VirtualMachines vms = new VirtualMachines();
		vms.getVms().put(vm.getId(), vm);
		vms.getVms().put(vm1.getId(), vm1);
		vms.getVms().put(vm2.getId(), vm2);
		
		String s = jsonb.toJson(vms);
		System.out.println(s);
		
		Disks disks = new Disks();
		disks.getDisks().put(disk.getId(), disk);
		disks.getDisks().put(disk1.getId(), disk1);
		disks.getDisks().put(disk2.getId(), disk2);
		s = jsonb.toJson(disks);
		System.out.println(s);
		
		Categories categories = new Categories();
		categories.getCategories().put(category.getId(), category);
		categories.getCategories().put(category1.getId(), category1);
		categories.getCategories().put(category2.getId(), category2);
		s = jsonb.toJson(categories);
		System.out.println(s);
		
		Activities activities = new Activities();
		activities.getActivities().put(activity.getId(), activity);
		activities.getActivities().put(activity1.getId(), activity1);
		activities.getActivities().put(activity2.getId(), activity2);
		s = jsonb.toJson(activities);
		System.out.println(s);
	
		HashMap<Integer, String> mapa = new HashMap<Integer, String>();
		mapa.put(1, "a");
		mapa.put(2, "b");
		
		System.out.println(mapa.keySet());
		int b = 1;
		mapa.remove(b);
		System.out.println(mapa.keySet());
	}
}
