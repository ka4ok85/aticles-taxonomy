package com.example.aticlestaxonomy.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.aticlestaxonomy.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
	Category findByCategory(String category);
}
