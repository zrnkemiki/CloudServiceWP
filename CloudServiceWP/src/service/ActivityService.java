package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;

import model.Activities;
import model.Activity;
import model.VirtualMachines;

public class ActivityService {
	private static BufferedWriter bw;
	private static FileWriter fw;

	public static Response deleteActivity(int id, ServletContext ctx) {
		Activities activities = getActivities(ctx);
		Activity activity = getActivityByID(id, ctx);
		activities.getActivities().remove(id);
		saveActivities(ctx, activities);
		VirtualMachines vms = VirtualMachineService.getVirtualMachines(ctx);
		
		// Obrisi tu aktivnost iz liste aktivnosti unuutar VM
		for (Object key : vms.getVms().keySet()) {
			Iterator i = vms.getVms().get(key).getActivities().iterator();
			while (i.hasNext()) {
				Activity a = (Activity) i.next();
				System.out.println("Broj aktivnosti: " + vms.getVms().get(key).getActivities().size());
				if (a.getId() == id){
					i.remove();
					System.out.println("Broj aktivnosti: " + vms.getVms().get(key).getActivities().size());
				}

			}
		}
		System.out.println("Cuvam");
		VirtualMachineService.saveVirtualMachines(ctx, vms);
		return Response.status(Response.Status.OK).build();
	

	/*
	 * for (VirtualMachine virtualMachine : vmCollection) { for (Activity a :
	 * virtualMachine.getActivities()) { if(a.getId()==activity.getId()) {
	 * //virtualMachine.getActivities().remove(a); //PROBLEM SA GETOVANJEM VRACA
	 * NULL POINTER?????????? System.out.
	 * println("Postoji problem jer get(key) vraca NULL POinter ???ZASTO???");
	 * System.out.println("Ovo je velciina" + vms.getVms().size());
	 * System.out.println("Ovo je keyset" + vms.getVms().keySet());
	 * System.out.println("Ovo je id"+
	 * virtualMachine.getActivities().get(0).getId());
	 * vms.getVms().get(virtualMachine.getId()).setActivities(virtualMachine.
	 * getActivities()); VirtualMachineService.saveOrganizations(ctx, vms); } } }
	 */

	}

	public static void saveActivities(ServletContext ctx, Activities activities) {
		String path = ctx.getRealPath("") + "data/activities.txt".replace("/", System.getProperty("file.separator"));

		String data = "";
		Jsonb jsonb = JsonbBuilder.create();
		data = jsonb.toJson(activities);
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.removeAttribute("activities");
	}

	public static Activity getActivityByID(int id, ServletContext ctx) {
		Activities activities = getActivities(ctx);
		Activity activity = null;
		Collection<Activity> act = activities.getActivities().values();
		for (Activity activity2 : act) {
			if (activity2.getId() == id) {
				activity = activity2;
			}
		}
		return activity;
	}

	public static Activities getActivities(ServletContext ctx) {
		Activities activities = (Activities) ctx.getAttribute("activities");
		if (activities == null) {
			activities = loadActivities(ctx.getRealPath(""));
			ctx.setAttribute("activities", activities);
		}

		return activities;
	}

	public static Activities loadActivities(String path) {
		path += "data/activities.txt".replace("/", System.getProperty("file.separator"));
		BufferedReader in = null;
		Activities activities = null;
		try {
			in = new BufferedReader(new FileReader(path));

			String s = in.readLine();
			Jsonb jsonb = JsonbBuilder.create();
			activities = jsonb.fromJson(s, Activities.class);

			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return activities;
	}
}
