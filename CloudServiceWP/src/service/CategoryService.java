package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import dto.CategoryDTO;
import model.Categories;
import model.CategoryVM;
import model.User;

public class CategoryService {

	private static BufferedWriter bw;
	private static FileWriter fw;
	
	public static CategoryVM getCategoryByID(int id, ServletContext ctx) {
		Categories cats = getCategories(ctx);
		CategoryVM category = null;
		Collection<CategoryVM> collectionCat = cats.getCategories().values();
		for (CategoryVM catTemp : collectionCat) {
			if (catTemp.getId() == id) {
				category = catTemp;
			}
		}
		return category;
	}
	
	public static Categories getCategories(ServletContext ctx) {
		Categories categories = (Categories) ctx.getAttribute("categories");
		if (categories == null) {
			categories = loadCategories(ctx.getRealPath(""));
			ctx.setAttribute("categories", categories);
		}

		return categories;
	}

	public static Categories loadCategories(String path) {
		path += "/data/categories.txt";
		BufferedReader in = null;
		Categories categories = null;
		try {
			in = new BufferedReader(new FileReader(path));

			String s = in.readLine();
			Jsonb jsonb = JsonbBuilder.create();
			categories = jsonb.fromJson(s, Categories.class);

			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}

	public static void saveCategories(ServletContext ctx, Categories allcats) {
		String path = ctx.getRealPath("") + "/data/categories.txt";

		String data = "";
		Jsonb jsonb = JsonbBuilder.create();
		data = jsonb.toJson(allcats);
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.removeAttribute("categories");
	}

	public static Response deleteCategory(int id, ServletContext ctx, HttpServletRequest request) {
		Categories categories = getCategories(ctx);
		if(!VirtualMachineService.checkForCategoryConflict(id, ctx)) {
			categories.getCategories().remove(String.valueOf(id));
			saveCategories(ctx, categories);
			System.out.println("Categories updated.");
			return Response.status(Response.Status.OK).build();
		}
		else {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}

	public static Response addCategory(CategoryDTO dto, ServletContext ctx, HttpServletRequest request) {
		User logged = (User) request.getSession().getAttribute("loggedUser");
		if (logged.getUserType() == null) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
		else if(dto.getName().equals("")) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
			
		CategoryVM category = new CategoryVM();
		Categories categories = getCategories(ctx);
		
		category.setId(categories.getCategories().size() + 1);
		category.setName(dto.getName());
		category.setNumberOfCores(dto.getNumberOfCores());
		category.setNumberOfGpuCores(dto.getNumberOfGpuCores());
		category.setRam(dto.getRam());
		
		categories.getCategories().put(category.getId(), category);

		saveCategories(ctx, categories);
		return Response.status(Response.Status.CREATED).build();
		
	}



}
