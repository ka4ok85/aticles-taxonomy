package com.example.aticlestaxonomy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.aticlestaxonomy.services.ArticleService;
import com.example.aticlestaxonomy.services.ArticleServiceImplementation;
import com.example.aticlestaxonomy.services.CategoryService;
import com.example.aticlestaxonomy.services.CategoryServiceWatsonImplementation;
import com.example.aticlestaxonomy.services.RssFeedService;
import com.example.aticlestaxonomy.services.RssFeedServiceImplementation;
import com.example.aticlestaxonomy.services.memorycache.CategoryCache;

@Configuration
public class AppConfig {

	@Bean
	public RssFeedService rssFeedService() {
		return new RssFeedServiceImplementation();
	}

	@Bean
	public ArticleService articleService() {
		return new ArticleServiceImplementation();
	}

	@Bean
	public CategoryService categoryService() {
		return new CategoryServiceWatsonImplementation();
	}

	@Bean
	public CategoryCache categoryCache() {
		return new CategoryCache();
	}

}
