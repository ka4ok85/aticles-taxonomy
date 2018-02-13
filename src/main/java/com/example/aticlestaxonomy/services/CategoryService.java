package com.example.aticlestaxonomy.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.example.aticlestaxonomy.entities.Category;

public interface CategoryService {
	public CompletableFuture<Map<String, Double>> getArticleCategories(String url);
	public Category findByCategory(String category);
}
