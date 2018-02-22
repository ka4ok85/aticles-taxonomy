package com.example.aticlestaxonomy.services.memorycache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.repositories.CategoryRepository;

public class CategoryCache {
	private static Map<Long, String> categories = new HashMap<Long, String>();
	private static Map<String, Long> ids = new HashMap<String, Long>();

	@Autowired
	private CategoryRepository categoryRepository;

	@PostConstruct
	public void init() {
		categoryRepository.findAll()
				.forEach(category -> {
					categories.put(category.getId(), category.getCategory());
					ids.put(category.getCategory(), category.getId());
				}

		);
	}

	public static String findById(Long id) {
		return categories.get(id);
	}

	public static Long findByCategory(String category) {
		return ids.get(category);
	}
}
