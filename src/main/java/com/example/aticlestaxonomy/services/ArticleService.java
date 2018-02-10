package com.example.aticlestaxonomy.services;

import java.util.stream.Stream;

import com.example.aticlestaxonomy.entities.Article;

public interface ArticleService {
	public com.example.aticlestaxonomy.entities.Article saveArticle(com.example.aticlestaxonomy.dto.Article article, Long rssFeedId);
	public Stream<Article> getArticlesAvaialbleForAnalysis();
}
