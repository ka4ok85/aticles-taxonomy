package com.example.aticlestaxonomy.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

public class CategoryServiceWatsonImplementation extends AbstractCategoryServiceImplementation {
	@Autowired
	protected NaturalLanguageUnderstanding service;

	private static final Logger log = LoggerFactory.getLogger(CategoryServiceWatsonImplementation.class);

	@Override
	public CompletableFuture<Map<String, Double>> getArticleCategories(String url) {
		return CompletableFuture.supplyAsync((Supplier<Map<String, Double>>) () -> {
			log.debug("Starting CategoryServiceWatsonImplementation for URL {}", url);

			Map<String, Double> results = new HashMap<String, Double>();

			CategoriesOptions categories = new CategoriesOptions();
			Features features = new Features.Builder().categories(categories).build();
			AnalyzeOptions parameters = new AnalyzeOptions.Builder().url(url).features(features).build();
			AnalysisResults analysisResults = service.analyze(parameters).execute();

			List<CategoriesResult> categoriesResultList = analysisResults.getCategories();
			// TODO: change to stream?
			for (CategoriesResult categoriesResult : categoriesResultList) {
				results.put(categoriesResult.getLabel(), categoriesResult.getScore());
			}

			log.debug("Finished CategoryServiceWatsonImplementation for URL {}", url);

			return results;
		});
	}

}
