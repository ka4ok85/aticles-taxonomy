package com.example.aticlestaxonomy.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.aticlestaxonomy.entities.RssFeed;
import com.example.aticlestaxonomy.repositories.RssFeedRepository;

public class RssFeedServiceImplementation implements RssFeedService {

	@Autowired
	private RssFeedRepository rssFeedRepository;

	@Override
	public List<RssFeed> getRssFeedsAvaialbleForProcess() {
		return rssFeedRepository.findRssFeedsReadyForProcessing();
	}

	@Override
	public int processFeed(RssFeed rssFeed) {
/*
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

		String combined = Stream.of(future1, future2, future3)
				.map(CompletableFuture::join)
				.collect(Collectors.joining(" "));

		System.out.println(combined);
*/
		String concreteRssFeedReaderClassName = rssFeed.getFeedType() + "RssFeedReader";
		Class<?> clazz;
		try {
			clazz = Class.forName("com.example.aticlestaxonomy.services.rssreaders." + concreteRssFeedReaderClassName);
			Constructor<?> constructor = clazz.getConstructor(String.class, LocalDateTime.class);
			AbstractRssFeedReader rssFeedReader = (AbstractRssFeedReader) constructor.newInstance(rssFeed.getUrl(), rssFeed.getLastFetchDatetime());
			rssFeedReader.readRssFeed();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public RssFeed setLastFetchDatetimeToNow(RssFeed rssFeed) {
		rssFeed.setLastFetchDatetime(LocalDateTime.now());
		rssFeedRepository.save(rssFeed);

		return rssFeed;
	}

}
