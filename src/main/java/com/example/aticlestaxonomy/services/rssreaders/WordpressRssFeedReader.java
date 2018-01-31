package com.example.aticlestaxonomy.services.rssreaders;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.example.aticlestaxonomy.dto.Article;
import com.example.aticlestaxonomy.services.AbstractRssFeedReader;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class WordpressRssFeedReader extends AbstractRssFeedReader {

	public WordpressRssFeedReader(String url, LocalDateTime lastFetchDate) {
		super(url, lastFetchDate);
	}

	@Override
	protected List<Article> readRssFeed() {

		CloseableHttpClient client = HttpClients.createMinimal();
		HttpUriRequest request = new HttpGet(url);

		CloseableHttpResponse response;
		try {
			response = client.execute(request);
			InputStream stream = response.getEntity().getContent();
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(stream));
			
			LocalDateTime articleDateTime;
			for (SyndEntry feedEntry : feed.getEntries()) {
				Date date = feedEntry.getPublishedDate();
				articleDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				
				if (this.lastFetchDate.isBefore(articleDateTime)) {
					Article article = new Article(feedEntry.getTitle(), articleDateTime, feedEntry.getUri());
					this.articles.add(article);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		}

		return this.articles;
	}

}
