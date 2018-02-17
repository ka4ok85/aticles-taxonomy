package com.example.aticlestaxonomy.rest.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.aticlestaxonomy.dto.Article;
import com.example.aticlestaxonomy.dto.Category;

@RestController("ArticleControllerV1")
@RequestMapping("/v1/")
public class ArticleController {

	@RequestMapping(value = "/articles/search", method = RequestMethod.POST)
	public ResponseEntity<List<Article>> getProducts(@RequestBody String categories) {

		List<Article> articles = new ArrayList<Article>();
		System.out.println(categories);

		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}
	
}
