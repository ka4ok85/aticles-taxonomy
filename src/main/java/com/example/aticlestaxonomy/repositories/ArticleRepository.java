package com.example.aticlestaxonomy.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.aticlestaxonomy.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
	List<Article> findByIsCategorySet(Long isCategorySet);

	List<Article> findFirst100ByIsCategorySet(Long isCategorySet);
}
