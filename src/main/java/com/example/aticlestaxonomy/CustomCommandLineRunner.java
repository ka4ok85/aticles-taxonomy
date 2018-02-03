package com.example.aticlestaxonomy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.entities.RssFeed;
import com.example.aticlestaxonomy.repositories.ArticleRepository;
import com.example.aticlestaxonomy.repositories.RssFeedRepository;
import com.example.aticlestaxonomy.services.RssFeedService;

@Configuration
public class CustomCommandLineRunner implements CommandLineRunner {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private RssFeedRepository rssFeedRepository;
	
	@Autowired
	private RssFeedService rssFeedService;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Starting CustomCommandLineRunner");
		
		
		List<RssFeed> rssFeeds = rssFeedService.getRssFeedsAvaialbleForProcess();
		
/*		
		for (RssFeed rssFeed : rssFeeds) {
			System.out.println(rssFeed);
			rssFeedService.processFeed(rssFeed);
			rssFeedService.setLastFetchDatetimeToNow(rssFeed);
		
		}
*/
		
		List<CompletableFuture<Integer>> pageContentFutures = rssFeeds.stream()
		        .map(webPageLink -> rssFeedService.processFeed(webPageLink))
		        .collect(Collectors.toList());

		

		// Create a combined Future using allOf()
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
		        pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()])
		);
	
		CompletableFuture<List<Integer>> allPageContentsFuture = allFutures.thenApply(v -> {
			   return pageContentFutures.stream()
			           .map(pageContentFuture -> pageContentFuture.join())
			           .collect(Collectors.toList());
			});
		/*
		List<Integer> e = allPageContentsFuture.get();
		for (Integer integer : e) {
			System.out.println(integer);
		}
		*/
		System.out.println("Ended CustomCommandLineRunner");

		
		
	}

}
