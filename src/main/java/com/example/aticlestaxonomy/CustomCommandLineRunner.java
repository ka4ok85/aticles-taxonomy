package com.example.aticlestaxonomy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
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
//getArticleCategories
		  List<Article> articles = articleService.getArticlesAvaialbleForAnalysis();
		  if (articles.size() == 0) { 
			  log.info("All articles already processed."); 
			  log.info("Ended CustomCommandLineRunner"); 
			  return ; 
		  }
		  System.out.println(articles.size());
/*
		  List<CompletableFuture<Map<String,Double>>> articlesFutures = 
				  articles.stream()
				  			.map(article -> categoryService.getArticleCategories2(article.getUrl()))
				  			.collect(Collectors.toList()
		  );
*/
		  /*
		  List<CompletableFuture<Article>> articlesFutures2 = 
				  articles.stream()
				  			.map((Article article) -> {
				  				CompletableFuture<Map<String, Double>> categories = categoryService.getArticleCategories2(article.getUrl())	;
				  				categories.thenApply(
				  					x -> {
				  						CompletableFuture<Article> d = articleService.setCatgoriesForArticle(article, categories);
				  						return d;
				  					}
				  				)
				  				//CompletableFuture<Article> updatedArticle = articleService.setCatgoriesForArticle(article, categories);
				  				//return null;
				  				//return updatedArticle;
				  			}
				  			)
				  			//.map(d -> articleService.setCatgoriesForArticle(article, categories))
				  			.collect(Collectors.toList()
		  );
		  */
		  
//		  List<CompletableFuture<Map<String,Double>>> articlesFutures = 
				Function<Article, CompletableFuture<Article>> mapper = new Function<Article, CompletableFuture<Article>>() {

					@Override
					public CompletableFuture<Article> apply(Article article) {
						CompletableFuture<Map<String, Double>> categories = categoryService.getArticleCategories(article.getUrl())	;

						 // raw
						Function<Map<String, Double>, Article> fn = new Function<Map<String,Double>, Article>() {

							//@Override
							public CompletableFuture<Article> apply(CompletableFuture<Map<String, Double>> t) {
								//CompletableFuture<Article> d = articleService.setCatgoriesForArticle(article, t);
		  						return articleService.setCatgoriesForArticle(article, t);
								//return null;
							}
// public CompletableFuture<Article> setCatgoriesForArticle(Article article, CompletableFuture<Map<String,Double>> categories);

							@Override
							public Article apply(Map<String, Double> t) {
								return articleService.setCatgoriesForArticle2(article, t);
								//return null;
							}

							
						};

						/*
						Function<CompletableFuture<Map<String, Double>>, CompletableFuture<Article>> fn = new Function<CompletableFuture<Map<String,Double>>, CompletableFuture<Article>>() {

							//@Override
							public CompletableFuture<Article> apply(CompletableFuture<Map<String, Double>> t) {
								//CompletableFuture<Article> d = articleService.setCatgoriesForArticle(article, t);
		  						return articleService.setCatgoriesForArticle(article, t);
								//return null;
							}
// public CompletableFuture<Article> setCatgoriesForArticle(Article article, CompletableFuture<Map<String,Double>> categories);
						
						};
//						CompletableFuture<Article> z = categories.thenApply(fn);
						CompletableFuture<Article> z = categories.thenApply(
								v -> { return new CompletableFuture<Article>();}
						);
						*/
						CompletableFuture<Article> z = categories.thenApply(fn);
						return   z;
						/*
						CompletableFuture<Article> z = categories.thenApply(
			  					x -> {
			  						CompletableFuture<Article> d = articleService.setCatgoriesForArticle(article, categories);
			  						return d;
			  					}
			  				);
						
						return   z;
						*/
					}
					
				};

				List<CompletableFuture<Article>> articlesFutures2 = articles.stream().map(mapper)
				  			.collect(Collectors.toList());
		  
		  //CompletableFuture<Void> allFutures = CompletableFuture.allOf(articlesFutures.toArray(new CompletableFuture[articlesFutures.size()]) );
		  CompletableFuture<Void> allFutures2 = CompletableFuture.allOf(articlesFutures2.toArray(new CompletableFuture[articlesFutures2.size()]) );
		  
		  allFutures2.get();
/*
		try (Stream<Article> articles = articleService.getArticlesAvaialbleForAnalysis()) {

			articles.forEach(
					article -> {
						log.info("Starting processing article ID={}, URL={}", article.getId(), article.getUrl());

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

						log.info("End processing article ID={}, URL={}", article.getId(), article.getUrl());
					}
			);

		}
*/
		
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
