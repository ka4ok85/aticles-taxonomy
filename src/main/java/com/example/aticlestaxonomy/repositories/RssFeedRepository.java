package com.example.aticlestaxonomy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.aticlestaxonomy.entities.RssFeed;

public interface RssFeedRepository extends CrudRepository<RssFeed, Long> {

	@Query(value = "SELECT * FROM rss_feeds WHERE `status`='enabled' and DATE_ADD(last_fetch_datetime, INTERVAL fetch_window second) <= NOW()", nativeQuery = true)
	public List<RssFeed> findRssFeedsReadyForProcessing();
}
