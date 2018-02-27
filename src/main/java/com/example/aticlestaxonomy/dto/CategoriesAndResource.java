package com.example.aticlestaxonomy.dto;

import java.util.List;

public class CategoriesAndResource {
	private List<Category> categories;
	private Long webResourceId;
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public Long getWebResourceId() {
		return webResourceId;
	}
	public void setWebResourceId(Long webResourceId) {
		this.webResourceId = webResourceId;
	}
	@Override
	public String toString() {
		return "CategoriesAndResource [categories=" + categories + ", webResourceId=" + webResourceId + "]";
	}
	
	
}
