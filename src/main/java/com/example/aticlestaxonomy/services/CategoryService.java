package com.example.aticlestaxonomy.services;

import java.util.List;
import java.util.Map;

public interface CategoryService {
	public List<Map<String, Double>> getArticleCategories(String url);
}
