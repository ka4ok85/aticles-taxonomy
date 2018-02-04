package com.example.aticlestaxonomy.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.aticlestaxonomy.dto.Article;

public abstract class AbstractRssFeedReader {

	protected String url;
	protected LocalDateTime lastFetchDate;
	protected List<Article> articles = new ArrayList<Article>();

	public AbstractRssFeedReader(String url, LocalDateTime lastFetchDate) {
		super();
		this.url = url;
		this.lastFetchDate = lastFetchDate;
	}

	protected abstract List<Article> readRssFeed() throws Exception;

}
