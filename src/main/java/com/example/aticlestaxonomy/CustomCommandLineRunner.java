package com.example.aticlestaxonomy;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.aticlestaxonomy.entities.Article;
import com.example.aticlestaxonomy.repositories.ArticleRepository;

@Configuration
public class CustomCommandLineRunner implements CommandLineRunner {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Starting CustomCommandLineRunner");
		
		
		Optional<Article> optionalArticle = articleRepository.findById(1L);
		Article article = optionalArticle.orElse(null);
		
		System.out.println(article);
		
		
		
	}

}
