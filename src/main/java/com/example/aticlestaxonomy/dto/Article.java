package com.example.aticlestaxonomy.dto;

import java.time.LocalDateTime;

public class Article {
	private String title;
	private LocalDateTime pubDate;
	private String link;

	public Article(String title, LocalDateTime pubDate, String link) {
		super();
		this.title = title;
		this.pubDate = pubDate;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getPubDate() {
		return pubDate;
	}

	public void setPubDate(LocalDateTime pubDate) {
		this.pubDate = pubDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", pubDate=" + pubDate + ", link=" + link + "]";
	}

}
