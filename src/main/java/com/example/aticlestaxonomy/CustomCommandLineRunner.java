package com.example.aticlestaxonomy;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collector;
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
		
		/*
		List<RssFeed> y = rssFeedRepository.findRssFeedsReadyForProcessing();
		for (RssFeed rssFeed : y) {
			System.out.println(rssFeed);
		}
		*/
		
		
		List<RssFeed> rssFeeds = rssFeedService.getRssFeedsAvaialbleForProcess();
		//System.out.println(rssFeeds);
		
		//CompletableFuture<Integer> future = new CompletableFuture<Integer>();
		
		
		/*
		Collector<Stream<Integer>, Integer, Integer> y;
		Stream<Integer> t = rssFeeds.stream().map(rssFeedService::processFeed).collect(y);
		*/
		
		
		for (RssFeed rssFeed : rssFeeds) {
			System.out.println(rssFeed);
			rssFeedService.processFeed(rssFeed);
			rssFeedService.setLastFetchDatetimeToNow(rssFeed);
			/*
			CompletableFuture<Integer> getUsersDetail(RssFeed r) {
				return CompletableFuture.thenApplyAsync((RssFeed r) -> {
					rssFeedService.processFeed(r);
				});	
			};
			*/			
			
		}

		
		System.out.println("Ended CustomCommandLineRunner");

		
		
	}

}
