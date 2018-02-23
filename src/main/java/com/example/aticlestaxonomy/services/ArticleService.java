package com.example.aticlestaxonomy.services;

import java.util.List;
import java.util.Map;

import com.example.aticlestaxonomy.dto.ArticleWithCategories;
import com.example.aticlestaxonomy.entities.Article;

public interface ArticleService {
	public com.example.aticlestaxonomy.entities.Article addArticle(com.example.aticlestaxonomy.dto.Article article, Long rssFeedId);
	public List<Article> getArticlesAvaialbleForAnalysis();
	public Article setCatgoriesForArticle(Article article, Map<String, Double> t);
	public List<Article> getArticlesByCategories(List<String> categories);
	public List<ArticleWithCategories> getArticlesWithCategoriesByCategories(List<String> categories);
}
