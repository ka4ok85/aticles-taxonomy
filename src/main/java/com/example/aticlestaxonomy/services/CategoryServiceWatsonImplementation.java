package com.example.aticlestaxonomy.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.CategoriesResult;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

public class CategoryServiceWatsonImplementation extends AbstractCategoryServiceImplementation {
	@Autowired
	protected NaturalLanguageUnderstanding service;

	@Override
	public Map<String, Double> getArticleCategories(String url) {
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

		return results;
	}

}
