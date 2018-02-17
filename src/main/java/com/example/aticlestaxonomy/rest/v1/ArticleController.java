package com.example.aticlestaxonomy.rest.v1;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aticlestaxonomy.dto.Article;
import com.example.aticlestaxonomy.dto.Category;
import com.example.aticlestaxonomy.dto.ErrorInfo;

@RestController("ArticleControllerV1")
@RequestMapping("/v1/")
public class ArticleController {
    @RequestMapping(value = "/articles/search", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Object /*ResponseEntity<?> /*ResponseEntity<List<Article>>*/ getProducts(@RequestBody List<Category> categories) {

		List<Article> articles = new ArrayList<Article>();
		if (categories.size() < 1 ) {
			ErrorInfo errorInfo = new ErrorInfo("Bad Request", HttpServletResponse.SC_BAD_REQUEST, "No categories were specified in Request", this.getClass().getName());
			return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}
	
}
