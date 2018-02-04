package com.example.aticlestaxonomy.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.aticlestaxonomy.entities.RssFeed;

public interface RssFeedService {
	public List<RssFeed> getRssFeedsAvaialbleForProcess();
	public CompletableFuture<Integer> processFeed(RssFeed rssFeed);
}
