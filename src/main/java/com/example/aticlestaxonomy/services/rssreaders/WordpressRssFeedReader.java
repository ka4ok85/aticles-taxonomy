package com.example.aticlestaxonomy.services.rssreaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.example.aticlestaxonomy.services.AbstractRssFeedReader;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class WordpressRssFeedReader extends AbstractRssFeedReader {

	public WordpressRssFeedReader(String url, LocalDateTime lastFetchDate) {
		super(url, lastFetchDate);
	}

	@Override
	protected int readRssFeed() {
		System.out.println("I am WordpressRssFeedReader readRssFeed method");
		
		
		String url = "http://stackoverflow.com/feeds/tag?tagnames=rome";
		url = "http://feeds.feedburner.com/generalmillsblog";
		//url = this.url;
		try (CloseableHttpClient client = HttpClients.createMinimal()) {
		  HttpUriRequest request = new HttpGet(url);
		  try (
				  CloseableHttpResponse response = client.execute(request);
				  InputStream stream = response.getEntity().getContent()) {
			  		SyndFeedInput input = new SyndFeedInput();
			  		SyndFeed feed = input.build(new XmlReader(stream));
			  		System.out.println(feed.getTitle());
		  } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		/*
		
		
		URL feedUrl;
		try {
			feedUrl = new URL(url);
	        SyndFeedInput input = new SyndFeedInput();
	        SyndFeed feed = input.build(new XmlReader(feedUrl));
	        
	        System.out.println(feed);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

*/ catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		
		return 0;
	}

}
