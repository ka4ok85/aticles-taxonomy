package com.example.aticlestaxonomy.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.repositories.ArticleRepository;

public class ArticleServiceImplementation implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public Article saveArticle(com.example.aticlestaxonomy.dto.Article article, Long rssFeedId) {
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
		// TODO Auto-generated method stub
		return null;
	}

}
