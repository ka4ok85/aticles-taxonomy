package com.example.aticlestaxonomy.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.entities.Category;
import com.example.aticlestaxonomy.repositories.CategoryRepository;

abstract public class AbstractCategoryServiceImplementation implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category findByCategory(String category) {
		return categoryRepository.findByCategory(category);
	}
}
