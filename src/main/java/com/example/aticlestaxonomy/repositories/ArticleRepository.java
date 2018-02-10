package com.example.aticlestaxonomy.repositories;

import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import com.example.aticlestaxonomy.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Stream<Article> findByIsCategorySet(Long isCategorySet);
}
