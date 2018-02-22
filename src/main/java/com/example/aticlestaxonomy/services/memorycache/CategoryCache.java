package com.example.aticlestaxonomy.services.memorycache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.repositories.CategoryRepository;

public class CategoryCache {
	private static Map<Long, String> categories = new HashMap<Long, String>();

	@Autowired
	private CategoryRepository categoryRepository;

	@PostConstruct
	public void init() {
		categoryRepository.findAll().forEach(
				category -> categories.put(category.getId(), category.getCategory())
		);
	}

	public static String findById(Long id) {
		return categories.get(id);
	}
}
