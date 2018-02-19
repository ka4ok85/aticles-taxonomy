package com.example.aticlestaxonomy.rest.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.aticlestaxonomy.dto.Article;
import com.example.aticlestaxonomy.dto.Category;
import com.example.aticlestaxonomy.dto.ErrorInfo;
import com.example.aticlestaxonomy.services.ArticleService;
import com.example.aticlestaxonomy.services.CategoryService;

@RestController("ArticleControllerV1")
@RequestMapping("/v1/")
public class ArticleController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/articles/search", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> getArticles(@RequestBody List<Category> categories) {

		List<Article> articles = new ArrayList<Article>();
		if (categories.size() < 1) {
			ErrorInfo errorInfo = new ErrorInfo("Bad Request", HttpServletResponse.SC_BAD_REQUEST,
					"No categories were specified in Request", this.getClass().getName());

			return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
		}

		List<com.example.aticlestaxonomy.entities.Category> categoriesList = new ArrayList<com.example.aticlestaxonomy.entities.Category>();
		for (Category category : categories) {
			if (null == categoryService.findByCategory(category.getName())) {
				ErrorInfo errorInfo = new ErrorInfo("Bad Request", HttpServletResponse.SC_BAD_REQUEST,
						"Category " + category.getName() + " does not exist", this.getClass().getName());

				return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
			} else {
				categoriesList.add(categoryService.findByCategory(category.getName()));
				System.out.println(category.getName());
			}
		}

		return new ResponseEntity<List<com.example.aticlestaxonomy.entities.Article>>(articleService.getArticlesByCategories(categoriesList), HttpStatus.OK);
		/*
		Long storeId = userDetailsService.getStoreId();
		Page<Product> products;
		if (isAlreadyInStore == null || isAlreadyInStore == true) {
			products = productService.getProductsByStoreId(pageable, storeId);
		} else {
			products = productService.getProductsNotInStore(pageable, storeId);
		}

    	try {    	
    		globalProductService.addProductIntoLocation(productLocationChangeWrapper.getProductId(), storeId, productLocationChangeWrapper.getLocationId(), productLocationChangeWrapper.getQuantity());
    		return true;
		} catch (Exception e) {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setMessage(e.getMessage());
			return new ResponseEntity<String>(errorInfo.getMessage(), HttpStatus.BAD_REQUEST);
		}    
*/
		//return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}

}
