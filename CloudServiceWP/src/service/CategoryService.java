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

import model.Categories;
import model.CategoryVM;

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



}
