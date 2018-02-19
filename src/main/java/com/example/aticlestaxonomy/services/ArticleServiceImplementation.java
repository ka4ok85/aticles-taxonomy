package com.example.aticlestaxonomy.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.entities.Category;
import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.entities.ArticleJoinCategory;
import com.example.aticlestaxonomy.repositories.ArticleRepository;

public class ArticleServiceImplementation implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryService categoryService;

	@Override
	public Article addArticle(com.example.aticlestaxonomy.dto.Article article, Long rssFeedId) {
		Article articleEntity = new Article();
		articleEntity.setTitle(article.getTitle());
		articleEntity.setUrl(article.getLink());
		articleEntity.setCreatedDatetime(article.getPubDate());
		articleEntity.setFetchedDatetime(LocalDateTime.now());
		articleEntity.setIsCategorySet(0L);
		articleEntity.setRssFeedId(rssFeedId);

		return articleRepository.save(articleEntity);
	}

	@Override
	public List<Article> getArticlesAvaialbleForAnalysis() {
		return articleRepository.findFirst100ByIsCategorySet(0L);
	}

	public Article setCatgoriesForArticle(final Article article, Map<String, Double> categories) {
		Set<ArticleJoinCategory> articleJoinCategorySet = article.getArticleJoinCategorySet();
		categories.forEach((String category, Double value) -> {
			ArticleJoinCategory articleJoinCategory = new ArticleJoinCategory();
			articleJoinCategory.setArticleId(article.getId());
			articleJoinCategory.setCategoryId(categoryService.findByCategory(category).getId());
			articleJoinCategory.setWeight(value.floatValue());
			articleJoinCategorySet.add(articleJoinCategory);
		});
		article.setArticleJoinCategorySet(articleJoinCategorySet);
		article.setIsCategorySet(1L);

		return articleRepository.save(article);
	}

	@Override
	public List<Article> getArticlesByCategories(List<Category> categories) {
		List<String> categoriesList = categories.stream().map(category -> category.getCategory()).collect(Collectors.toList());

		return articleRepository.findByCategory(categoriesList);
	}

}
