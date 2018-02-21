package com.example.aticlestaxonomy.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleWithCategories {

	private String url;
	private String title;
	private LocalDateTime createdDatetime;
	private List<CategoryWithWeight> categories;

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
