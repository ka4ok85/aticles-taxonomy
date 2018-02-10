package com.example.aticlestaxonomy;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.entities.RssFeed;
import com.example.aticlestaxonomy.services.ArticleService;
import com.example.aticlestaxonomy.services.RssFeedService;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EntitiesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

@Configuration
public class CustomCommandLineRunner implements CommandLineRunner {

	@Autowired
	protected NaturalLanguageUnderstanding service;
	
	@Autowired
	private RssFeedService rssFeedService;

	@Autowired
	private ArticleService articleService;

	private static final Logger log = LoggerFactory.getLogger(CustomCommandLineRunner.class);

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		log.info("Starting CustomCommandLineRunner");
		
		try (Stream<Article> articles = articleService.getArticlesAvaialbleForAnalysis()) {

			articles.forEach(
					//System.out::println
					(Article a) -> System.out.println(a)
			);
		}
		/*
		for (Article article : articles) {
			System.out.println(article);
		}
		*/
		
		
		/*
		CategoriesOptions categories = new CategoriesOptions();
		Features features = new Features.Builder().categories(categories).build();
		AnalyzeOptions parameters = new AnalyzeOptions.Builder().url("http://blog.generalmills.com/?p=26424").features(features).build();
		AnalysisResults results = service.analyze(parameters).execute();
		System.out.println(results);
		
		List<CategoriesResult> categoriesResultList = results.getCategories();
		for (CategoriesResult categoriesResult : categoriesResultList) {
			System.out.println(categoriesResult.getLabel() + ": " + categoriesResult.getScore());
		}
*/
		/*
		List<RssFeed> rssFeeds = rssFeedService.getRssFeedsAvaialbleForProcess();
		if (rssFeeds.size() == 0) {
			log.info("All feeds already processed.");
			log.info("Ended CustomCommandLineRunner");
			return ;
		}

		// tasks processing list of rssFeeds and collecting number of added articles per each feed
		List<CompletableFuture<Integer>> addedRssArticlesFutures = rssFeeds.stream()
		        .map(rssFeed -> rssFeedService.processFeed(rssFeed))
		        .collect(Collectors.toList());

		// completes when all individual futures completed 
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
				addedRssArticlesFutures.toArray(new CompletableFuture[addedRssArticlesFutures.size()])
		);

		// tasks summing added articles counts for each feed into total count 
		CompletableFuture<Integer> totalCountFuture = allFutures.thenApply(v -> {
			return addedRssArticlesFutures.stream()
			           .map(addedRssArticlesFuture -> addedRssArticlesFuture.join())
			           .collect(Collectors.summingInt(Integer::intValue));
		});

		Integer totalCount = totalCountFuture.get();

		log.info("Total added articles count: {}", totalCount);
		*/
		log.info("Ended CustomCommandLineRunner");
	}

}
