package com.example.aticlestaxonomy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.aticlestaxonomy.entities.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
	List<Article> findByIsCategorySet(Long isCategorySet);

	List<Article> findFirst100ByIsCategorySet(Long isCategorySet);

	@Query(value = "SELECT articles.* FROM articles, article_join_category, categories WHERE `is_category_set`='1' AND article_join_category.article_id=articles.id AND article_join_category.category_id=categories.id AND categories.category in :categories LIMIT :limit", nativeQuery = true)
	List<Article> findByCategory(@Param("categories") List<String> categories, @Param("limit") int limit);

	@Query(value = "SELECT articles.* FROM articles, article_join_category, categories WHERE `is_category_set`='1' AND rss_feed_id = :rssFeedId and article_join_category.article_id=articles.id AND article_join_category.category_id=categories.id AND categories.category in :categories LIMIT :limit", nativeQuery = true)
	List<Article> findByCategoryAndRssFeed(@Param("categories") List<String> categories, @Param("rssFeedId") int rssFeedId, @Param("limit") int limit);
}
