package com.example.aticlestaxonomy.rest.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.aticlestaxonomy.dto.Category;
import com.example.aticlestaxonomy.dto.ErrorInfo;
import com.example.aticlestaxonomy.dto.ArticleWithCategories;
import com.example.aticlestaxonomy.services.ArticleService;
import com.example.aticlestaxonomy.services.CategoryService;
import com.example.aticlestaxonomy.services.memorycache.CategoryCache;

@RestController("ArticleControllerV1")
@RequestMapping("/v1/")
public class ArticleController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	@Value("${endpoint.articles-search.categories.max}")
	private int categoriesMax;

	@Value("${endpoint.articles-search.articles.max}")
	private int articlesMax;

	@RequestMapping(value = "/articles/search", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> getArticles(@RequestBody List<Category> categories) {
		if (categories.size() < 1) {
			ErrorInfo errorInfo = new ErrorInfo("Bad Request", HttpServletResponse.SC_BAD_REQUEST,
					"No categories were specified in Request", this.getClass().getName());

			return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
		}

		if (categories.size() > categoriesMax) {
			ErrorInfo errorInfo = new ErrorInfo("Bad Request", HttpServletResponse.SC_BAD_REQUEST,
					"More than " + categoriesMax + " categories were specified in Request", this.getClass().getName());

			return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
		}

		List<String> categoriesList = new ArrayList<String>();
		for (Category category : categories) {
			if (null == CategoryCache.findByCategory(category.getName())) {
				ErrorInfo errorInfo = new ErrorInfo("Bad Request", HttpServletResponse.SC_BAD_REQUEST,
						"Category " + category.getName() + " does not exist", this.getClass().getName());

				return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
			} else {
				categoriesList.add(category.getName());
			}
		}

		return new ResponseEntity<List<ArticleWithCategories>>(articleService.getArticlesWithCategoriesByCategories(categoriesList, articlesMax), HttpStatus.OK);
	}

}
