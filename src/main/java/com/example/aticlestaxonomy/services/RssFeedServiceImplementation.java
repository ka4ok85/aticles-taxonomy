package com.example.aticlestaxonomy.services;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

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

	private RssFeed setLastFetchDatetimeToNow(RssFeed rssFeed) {
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
				AbstractRssFeedReader rssFeedReader = (AbstractRssFeedReader) constructor.newInstance(rssFeed.getUrl(),
						rssFeed.getLastFetchDatetime());
				List<Article> freshArticles = null;
				try {
					freshArticles = rssFeedReader.readRssFeed();
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
				for (Article article : freshArticles) {
					try {
						articleService.addArticle(article, rssFeed.getId());
						addedArticles++;
				    } catch (DataIntegrityViolationException e) {
				    	log.warn("Article is already fetched! URL: {}", article.getLink());
				    }
				}

				this.setLastFetchDatetimeToNow(rssFeed);
				log.info("Finished processing feed ID={}, URL={}", rssFeed.getId(), rssFeed.getUrl());
			} catch (Exception e) {
				log.error("Error during processing feed ID={}, URL={}. Error Message: {}", rssFeed.getId(), rssFeed.getUrl(), e.getMessage());
			}

			return addedArticles;
		});
	}

}
