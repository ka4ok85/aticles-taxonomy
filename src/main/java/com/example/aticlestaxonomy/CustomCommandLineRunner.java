package com.example.aticlestaxonomy;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.aticlestaxonomy.entities.RssFeed;
import com.example.aticlestaxonomy.services.RssFeedService;

@Configuration
public class CustomCommandLineRunner implements CommandLineRunner {

	@Autowired
	private RssFeedService rssFeedService;
	
	private static final Logger log = LoggerFactory.getLogger(CustomCommandLineRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Starting CustomCommandLineRunner");
		
		List<RssFeed> rssFeeds = rssFeedService.getRssFeedsAvaialbleForProcess();
		
		List<CompletableFuture<Integer>> pageContentFutures = rssFeeds.stream()
		        .map(webPageLink -> rssFeedService.processFeed(webPageLink))
		        .collect(Collectors.toList());


		// Create a combined Future using allOf()
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
		        pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()])
		);

		CompletableFuture<Integer> allPageContentsFuture = allFutures.thenApply(v -> {

			return pageContentFutures.stream()
			           .map(pageContentFuture -> pageContentFuture.join())
			           .collect(Collectors.summingInt(Integer::intValue));
			});
		Integer totalCount = allPageContentsFuture.get();

		log.info("Total added articles count: {}", totalCount);
		log.info("Ended CustomCommandLineRunner");
	}

}
