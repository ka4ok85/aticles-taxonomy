package com.example.aticlestaxonomy.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "articles")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	@NotNull
	private String url;

	@Column(nullable = false)
	@NotNull
	private String title;

	@Column(name = "created_datetime", nullable = false)
	@NotNull
	private LocalDateTime createdDatetime;

	@Column(name = "fetched_datetime", nullable = false)
	@NotNull
	private LocalDateTime fetchedDatetime;

	@Column(name = "rss_feed_id")
	private Long rssFeedId;

	@Column(name = "is_category_set", nullable = false)
	@NotNull
	private Long isCategorySet;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", referencedColumnName = "id")
	private Set<ArticleJoinCategory> articleJoinCategorySet = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDateTime getFetchedDatetime() {
		return fetchedDatetime;
	}

	public void setFetchedDatetime(LocalDateTime fetchedDatetime) {
		this.fetchedDatetime = fetchedDatetime;
	}

	public Long getRssFeedId() {
		return rssFeedId;
	}

	public void setRssFeedId(Long rssFeedId) {
		this.rssFeedId = rssFeedId;
	}

	public Long getIsCategorySet() {
		return isCategorySet;
	}

	public void setIsCategorySet(Long isCategorySet) {
		this.isCategorySet = isCategorySet;
	}

	public Set<ArticleJoinCategory> getArticleJoinCategorySet() {
		return articleJoinCategorySet;
	}

	public void setArticleJoinCategorySet(Set<ArticleJoinCategory> articleJoinCategorySet) {
		this.articleJoinCategorySet = articleJoinCategorySet;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", url=" + url + ", title=" + title + ", createdDatetime=" + createdDatetime
				+ ", fetchedDatetime=" + fetchedDatetime + ", rssFeedId=" + rssFeedId + ", isCategorySet="
				+ isCategorySet + ", articleJoinCategorySet=" + articleJoinCategorySet + "]";
	}

}
