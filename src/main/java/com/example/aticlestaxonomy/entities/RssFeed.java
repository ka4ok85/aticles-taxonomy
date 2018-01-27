package com.example.aticlestaxonomy.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rss_feeds")
public class RssFeed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "web_resource_id")
	private Long webResourceId;

	@Column(unique = true)
	private String url;

	@Column(name = "last_fetch_datetime")
	private LocalDateTime lastFetchDatetime;

	@Column(name = "fetch_window")
	private Long fetchWindow;

	private String status;

	@Column(name = "feed_type")
	private String feedType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWebResourceId() {
		return webResourceId;
	}

	public void setWebResourceId(Long webResourceId) {
		this.webResourceId = webResourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getLastFetchDatetime() {
		return lastFetchDatetime;
	}

	public void setLastFetchDatetime(LocalDateTime lastFetchDatetime) {
		this.lastFetchDatetime = lastFetchDatetime;
	}

	public Long getFetchWindow() {
		return fetchWindow;
	}

	public void setFetchWindow(Long fetchWindow) {
		this.fetchWindow = fetchWindow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}

	@Override
	public String toString() {
		return "RssFeed [id=" + id + ", webResourceId=" + webResourceId + ", url=" + url + ", lastFetchDatetime="
				+ lastFetchDatetime + ", fetchWindow=" + fetchWindow + ", status=" + status + ", feedType=" + feedType
				+ "]";
	}

}
