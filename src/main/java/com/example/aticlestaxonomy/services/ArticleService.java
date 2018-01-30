package com.example.aticlestaxonomy.services;

public interface ArticleService {
	public com.example.aticlestaxonomy.entities.Article saveArticle(com.example.aticlestaxonomy.dto.Article article, Long rssFeedId);
}
