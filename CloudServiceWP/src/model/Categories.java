package model;

import java.util.HashMap;

public class Categories {
	
	private HashMap<Integer, CategoryVM> categories = new HashMap<Integer, CategoryVM>();

	public HashMap<Integer, CategoryVM> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<Integer, CategoryVM> categories) {
		this.categories = categories;
	}

	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categories(HashMap<Integer, CategoryVM> categories) {
		super();
		this.categories = categories;
	}


	
	

}
