package com.example.aticlestaxonomy.services;

import java.util.List;

import com.example.aticlestaxonomy.entities.RssFeed;

public interface RssFeedService {
	public List<RssFeed> getRssFeedsAvaialbleForProcess();
	public boolean processFeed(RssFeed rssFeed);
	public RssFeed setLastFetchDatetimeToNow(RssFeed rssFeed);
}
