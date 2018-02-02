package com.example.aticlestaxonomy.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.dto.Article;
import com.example.aticlestaxonomy.entities.RssFeed;
import com.example.aticlestaxonomy.repositories.RssFeedRepository;

public class RssFeedServiceImplementation implements RssFeedService {
	
	@Autowired
	private RssFeedRepository rssFeedRepository;

	@Autowired
	private ArticleService articleService;
	
	private static final Logger log = LoggerFactory.getLogger(RssFeedServiceImplementation.class);

	@Override
	public List<RssFeed> getRssFeedsAvaialbleForProcess() {
		return rssFeedRepository.findRssFeedsReadyForProcessing();
	}

	@Override
	public RssFeed setLastFetchDatetimeToNow(RssFeed rssFeed) {
		rssFeed.setLastFetchDatetime(LocalDateTime.now());
		rssFeedRepository.save(rssFeed);

		return rssFeed;
	}

	@Override
	public CompletableFuture<Integer> processFeed(RssFeed rssFeed) {
		
		return CompletableFuture.supplyAsync((Supplier<Integer>) () -> {

			log.info("Starting processing feed ID={}, URL={}", rssFeed.getId(), rssFeed.getUrl());

			int addedArticles = 0;
			String concreteRssFeedReaderClassName = rssFeed.getFeedType() + "RssFeedReader";
			Class<?> clazz;
			try {
				clazz = Class.forName("com.example.aticlestaxonomy.services.rssreaders." + concreteRssFeedReaderClassName);
				Constructor<?> constructor = clazz.getConstructor(String.class, LocalDateTime.class);
				AbstractRssFeedReader rssFeedReader = (AbstractRssFeedReader) constructor.newInstance(rssFeed.getUrl(), rssFeed.getLastFetchDatetime());
				List<Article> freshArticles = rssFeedReader.readRssFeed();
				for (Article article : freshArticles) {
					
					articleService.saveArticle(article, rssFeed.getId());
					addedArticles++;
				}
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}

						
		    try {
		        TimeUnit.SECONDS.sleep(5);
		    } catch (InterruptedException e) {
		       throw new IllegalStateException(e);
		    }
			
		    log.info("Finished processing feed ID={}, URL={}", rssFeed.getId(), rssFeed.getUrl());
		    
		    return addedArticles;
		});

	}


}
