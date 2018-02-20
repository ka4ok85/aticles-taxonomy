package com.example.aticlestaxonomy.dto;

public class CategoryWithWeight {

	private String category;
	private Float weight;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "CategoryWithWeight [category=" + category + ", weight=" + weight + "]";
	}

}
