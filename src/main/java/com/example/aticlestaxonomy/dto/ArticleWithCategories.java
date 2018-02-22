package com.example.aticlestaxonomy.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.services.memorycache.CategoryCache;

public class ArticleWithCategories {

	private String url;
	private String title;
	private LocalDateTime createdDatetime;
	private List<CategoryWithWeight> categories = new ArrayList<CategoryWithWeight>();

	public ArticleWithCategories(Article article) {
		setUrl(article.getUrl());
		setTitle(article.getTitle());
		setCreatedDatetime(article.getCreatedDatetime());
		
		article.getArticleJoinCategorySet().stream().forEach(articleJoinCategory -> 
			{
				CategoryWithWeight category = new CategoryWithWeight();
				category.setWeight(articleJoinCategory.getWeight());
				category.setCategory(CategoryCache.findById(articleJoinCategory.getCategoryId()));
				categories.add(category);
			}
		);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(LocalDateTime createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public List<CategoryWithWeight> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryWithWeight> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "ArticleWithCategories [url=" + url + ", title=" + title + ", createdDatetime=" + createdDatetime
				+ ", categories=" + categories + "]";
	}

}
