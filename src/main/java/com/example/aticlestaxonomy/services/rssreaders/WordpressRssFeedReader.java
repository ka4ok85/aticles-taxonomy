package com.example.aticlestaxonomy.services.rssreaders;

import java.time.LocalDateTime;

import com.example.aticlestaxonomy.services.AbstractRssFeedReader;

public class WordpressRssFeedReader extends AbstractRssFeedReader {

	public WordpressRssFeedReader(String url, LocalDateTime lastFetchDate) {
		super(url, lastFetchDate);
	}

	@Override
	protected int readRssFeed() {
		System.out.println("I am WordpressRssFeedReader readRssFeed method");
		return 0;
	}

}
