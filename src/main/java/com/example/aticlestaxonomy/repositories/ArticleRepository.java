package com.example.aticlestaxonomy.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.aticlestaxonomy.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
