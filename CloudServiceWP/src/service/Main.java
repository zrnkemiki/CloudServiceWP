package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import enums.DiskType;
import enums.UserType;
import model.Activities;
import model.Activity;
import model.Categories;
import model.CategoryVM;
import model.Disk;
import model.Disks;
import model.Organization;
import model.Organizations;
import model.User;
import model.Users;
import model.VirtualMachine;
import model.VirtualMachines;

public class Main {

	public static void main(String[] args) {		
		
		// TODO Auto-generated method stub
		Organization o = new Organization(0, "FTN",  "Fakultet Tehnickih Nauka",  "ftn", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o1 = new Organization(1, "ETF",  "Elektrotehnicki Fakultet",  "etf", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o2 = new Organization(2, "Google",  "Google organization",  "google", new ArrayList<Integer>(), new ArrayList<Integer>());
		Organization o3 = new Organization(3, "Facebook", "Facebook organization",  "facebook", new ArrayList<Integer>(), new ArrayList<Integer>());
		
		User u1 = new User(0, "admin1", "a", "Petar", "Petrovic", "0112345", "silvester", o, UserType.ADMIN);
		User u2 = new User(1, "superadmin", "a", "Marko", "Markovic", "02145455", "superadmin", o, UserType.SUPERADMIN);
		User u3 = new User(2, "user1", "a", "Mirko", "Mirkovic", "0561323321", "mark", o, UserType.USER);
		User u4 = new User(3, "user2", "a", "Luka", "Lukovic", "05412312", "zdravko", o, UserType.USER);
		User u = new User(4, "user3", "a", "Nemanja", "Nemanjic", "23115454", "user3", o1, UserType.USER);
		User u5 = new User(5, "user4", "a", "Vukasin", "Vukovic", "23121434", "silvester", o2, UserType.USER);
		User u6 = new User(6, "admin3", "a", "Milos", "Milosevic", "2312312", "admin3", o1, UserType.ADMIN);
		User u7 = new User(7, "admin4", "a", "Mitar", "Mitrovic", "123123", "admin4", o2, UserType.ADMIN);
		Jsonb jsonb = JsonbBuilder.create();
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

	
		CategoryVM category = new CategoryVM(0, "Category 1", 8, 2, 4);
		CategoryVM category1 = new CategoryVM(1, "Category 2", 16, 64, 4);
		CategoryVM category2 = new CategoryVM(2, "Category 3", 10, 16, 8);
		
		
		Activity activity = new Activity(0,LocalDateTime.now().minusDays(30), null);
		Activity activity1 = new Activity(1,LocalDateTime.now().minusDays(10), LocalDateTime.now());
		Activity activity2 = new Activity(2,LocalDateTime.now().minusHours(5), LocalDateTime.now());
		

		Disk disk = new Disk(0, "Disk 1", o, DiskType.HDD, 2, 1);
		Disk disk1 = new Disk(1, "Disk 2", o1, DiskType.HDD, 4, 2);
		Disk disk2 = new Disk(2, "Disk 3", o2, DiskType.SSD, 3, 3);
		Disk disk3 = new Disk(3, "Disk 4", o, DiskType.SSD, 2, 1);
		Disk disk4 = new Disk(4, "Disk 5", o1, DiskType.SSD, 4, 2);
		Disk disk5 = new Disk(5, "Disk 6", o2, DiskType.SSD, 3, 3);
		
		
		
		VirtualMachine vm = new VirtualMachine(1, "VM1", o, category, category.getNumberOfCores(), category.getRam(), category.getNumberOfGpuCores(), new ArrayList<Disk>(), new ArrayList<Activity>());
		VirtualMachine vm1 = new VirtualMachine(2, "VM2", o1, category1, category1.getNumberOfCores(), category1.getRam(), category1.getNumberOfGpuCores(), new ArrayList<Disk>(), new ArrayList<Activity>());
		VirtualMachine vm2 = new VirtualMachine(3, "VM3", o2, category2, category2.getNumberOfCores(), category2.getRam(), category2.getNumberOfGpuCores(), new ArrayList<Disk>(), new ArrayList<Activity>());
		vm.getDisks().add(disk);
		vm.getDisks().add(disk3);
		vm1.getDisks().add(disk1);
		vm1.getDisks().add(disk4);
		vm2.getDisks().add(disk2);
		vm2.getDisks().add(disk5);
		vm.getActivities().add(activity1);
		vm.getActivities().add(activity2);
		vm.getActivities().add(activity);
		
		
		
		Organizations org = new Organizations();
		o.getUsers().add(0);
		o.getUsers().add(1);
		o.getUsers().add(2);
		o.getUsers().add(3);
		
		o.getVms().add(1);
		o1.getVms().add(2);
		o2.getVms().add(3);
		
		o1.getUsers().add(4);
		o1.getUsers().add(6);
		
		o2.getUsers().add(5);
		o2.getUsers().add(7);
		
		org.getOrganizations().put(o.getId(), o);
		org.getOrganizations().put(o1.getId(), o1);
		org.getOrganizations().put(o2.getId(), o2);
		org.getOrganizations().put(o3.getId(), o3);
		String s = jsonb.toJson(org);
		System.out.println(s);
		
		VirtualMachines vms = new VirtualMachines();
		vms.getVms().put(vm.getId(), vm);
		vms.getVms().put(vm1.getId(), vm1);
		vms.getVms().put(vm2.getId(), vm2);
		
		s = jsonb.toJson(vms);
		System.out.println(s);
		
		Disks disks = new Disks();
		disks.getDisks().put(disk.getId(), disk);
		disks.getDisks().put(disk1.getId(), disk1);
		disks.getDisks().put(disk2.getId(), disk2);
		disks.getDisks().put(disk3.getId(), disk3);
		disks.getDisks().put(disk4.getId(), disk4);
		disks.getDisks().put(disk5.getId(), disk5);
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
	

	}
}
