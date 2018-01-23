package com.example.aticlestaxonomy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="article_join_category")
public class ArticleJoinCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "article_id", unique = false, nullable = false)
	@NotNull
	private Long articleId;
	
	@Column(name = "category_id", unique = false, nullable = false)
	@NotNull
	private Long categoryId;

	@Column(name = "weight", unique = false, nullable = false)
	@NotNull
	private Float weight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "ArticleJoinCategory [id=" + id + ", articleId=" + articleId + ", categoryId=" + categoryId + ", weight="
				+ weight + "]";
	}
	
}
