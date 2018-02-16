package com.example.aticlestaxonomy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.repositories.ArticleRepository;
import com.example.aticlestaxonomy.services.ArticleService;
import com.example.aticlestaxonomy.services.CategoryService;
import com.example.aticlestaxonomy.services.RssFeedService;

@Configuration
public class CustomCommandLineRunner implements CommandLineRunner {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RssFeedService rssFeedService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleRepository articleRepository;

	private static final Logger log = LoggerFactory.getLogger(CustomCommandLineRunner.class);

	@Override
	public void run(String... args) throws Exception {
		log.info("Starting CustomCommandLineRunner");

		List<Article> articles = articleService.getArticlesAvaialbleForAnalysis();
		if (articles.size() == 0) {
			log.info("All articles already processed.");
			log.info("Ended CustomCommandLineRunner");
			return;
		}

		List<CompletableFuture<Article>> futureArticlesList = articles.stream()
				.map(article -> {
					// get Categories from Watson and then Save them in DB
					CompletableFuture<Map<String, Double>> futureCategoriesMap = categoryService.getArticleCategories(article.getUrl());
					return futureCategoriesMap.thenApply(categoriesMap -> articleService.setCatgoriesForArticle(article, categoriesMap));
				})
				.collect(Collectors.toList());


		// trigger and wait until all futures are ready
		CompletableFuture<Void> allArticlesFuture = CompletableFuture.allOf(futureArticlesList.toArray(new CompletableFuture[futureArticlesList.size()]));
		allArticlesFuture.get();

		/*
		 * List<RssFeed> rssFeeds =
		 * rssFeedService.getRssFeedsAvaialbleForProcess(); if (rssFeeds.size()
		 * == 0) { log.info("All feeds already processed."); log.info(
		 * "Ended CustomCommandLineRunner"); return ; }
		 * 
		 * // tasks processing list of rssFeeds and collecting number of added
		 * //articles per each feed List<CompletableFuture<Integer>>
		 * addedRssArticlesFutures = rssFeeds.stream() .map(rssFeed ->
		 * rssFeedService.processFeed(rssFeed)) .collect(Collectors.toList());
		 * 
		 * // completes when all individual futures completed
		 * CompletableFuture<Void> allFutures = CompletableFuture.allOf(
		 * addedRssArticlesFutures.toArray(new
		 * CompletableFuture[addedRssArticlesFutures.size()]) );
		 * 
		 * // tasks summing added articles counts for each feed into total count
		 * CompletableFuture<Integer> totalCountFuture = allFutures.thenApply(v
		 * -> { return addedRssArticlesFutures.stream()
		 * .map(addedRssArticlesFuture -> addedRssArticlesFuture.join())
		 * .collect(Collectors.summingInt(Integer::intValue)); });
		 * 
		 * Integer totalCount = totalCountFuture.get();
		 * 
		 * log.info("Total added articles count: {}", totalCount);
		 */
		log.info("Ended CustomCommandLineRunner");
	}

}
