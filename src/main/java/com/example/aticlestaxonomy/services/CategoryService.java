package com.example.aticlestaxonomy.services;

import java.util.Map;

import com.example.aticlestaxonomy.entities.Category;

public interface CategoryService {
	public Map<String, Double> getArticleCategories(String url);
	public Category findByCategory(String category);
}
