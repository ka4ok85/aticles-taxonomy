package com.example.aticlestaxonomy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.aticlestaxonomy.services.WebResourceService;
import com.example.aticlestaxonomy.services.WebResourceServiceImplementation;

@Configuration
public class AppConfig {

	@Bean
	public WebResourceService webResourceService() {
		return new WebResourceServiceImplementation();
	}
}
