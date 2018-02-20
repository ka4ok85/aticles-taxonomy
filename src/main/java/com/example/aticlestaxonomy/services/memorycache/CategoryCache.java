package com.example.aticlestaxonomy.services.memorycache;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.repositories.CategoryRepository;

public class CategoryCache {
	private Map<Integer, String> categories = new HashMap<Integer, String>();

	@Autowired
	private CategoryRepository categoryRepository;

	@PostConstruct
	public void init() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>CategoryCache init");
		categoryRepository.findAll()
				.forEach(category -> categories.put(category.getId().intValue(), category.getCategory()));
	}
}
