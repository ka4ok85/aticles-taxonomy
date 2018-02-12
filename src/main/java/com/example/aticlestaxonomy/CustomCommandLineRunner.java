package com.example.aticlestaxonomy;

import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.entities.ArticleJoinCategory;
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
	@Transactional
	public void run(String... args) throws Exception {
		log.info("Starting CustomCommandLineRunner");

		try (Stream<Article> articles = articleService.getArticlesAvaialbleForAnalysis()) {

			articles.forEach(
					article -> {
						Set<ArticleJoinCategory> articleJoinCategorySet = article.getArticleJoinCategorySet();
						categoryService.getArticleCategories(article.getUrl())
								.forEach((String category, Double value) -> {
									ArticleJoinCategory articleJoinCategory = new ArticleJoinCategory();
									articleJoinCategory.setArticleId(article.getId());
									articleJoinCategory.setCategoryId(categoryService.findByCategory(category).getId());
									articleJoinCategory.setWeight(value.floatValue());
									articleJoinCategorySet.add(articleJoinCategory);
								});
						article.setArticleJoinCategorySet(articleJoinCategorySet);
						article.setIsCategorySet(1L);
						articleRepository.save(article);

					}
			);
		}
/*
		  List<RssFeed> rssFeeds =
		  rssFeedService.getRssFeedsAvaialbleForProcess(); if (rssFeeds.size()
		  == 0) { log.info("All feeds already processed."); log.info(
		  "Ended CustomCommandLineRunner"); return ; }
		  
		  // tasks processing list of rssFeeds and collecting number of added
		  //articles per each feed 
		  List<CompletableFuture<Integer>>
		  addedRssArticlesFutures = rssFeeds.stream() .map(rssFeed ->
		  rssFeedService.processFeed(rssFeed)) .collect(Collectors.toList());
		  
		  // completes when all individual futures completed
		  CompletableFuture<Void> allFutures = CompletableFuture.allOf(
		  addedRssArticlesFutures.toArray(new
		  CompletableFuture[addedRssArticlesFutures.size()]) );
		  
		  // tasks summing added articles counts for each feed into total count
		  CompletableFuture<Integer> totalCountFuture = allFutures.thenApply(v
		  -> { return addedRssArticlesFutures.stream()
		  .map(addedRssArticlesFuture -> addedRssArticlesFuture.join())
		  .collect(Collectors.summingInt(Integer::intValue)); });
		  
		  Integer totalCount = totalCountFuture.get();
		  
		  log.info("Total added articles count: {}", totalCount);
*/
		log.info("Ended CustomCommandLineRunner");
	}

}
