package com.example.aticlestaxonomy.services;

import java.util.List;

public interface ArticleService {
	public com.example.aticlestaxonomy.entities.Article saveArticle(com.example.aticlestaxonomy.dto.Article article, Long rssFeedId);
	public List<com.example.aticlestaxonomy.entities.Article> getArticlesAvaialbleForAnalysis();
}
