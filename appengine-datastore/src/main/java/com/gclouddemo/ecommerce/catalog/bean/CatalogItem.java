/**
 * 
 */
package com.gclouddemo.ecommerce.catalog.bean;

import java.math.BigDecimal;

/**
 * Represents a catalog item.
 */
public class CatalogItem {
	private long id;
	private String summary;
	private String description;
	private BigDecimal price;
	private String thumb;
	private String image;
	private String category;
	private String subcategory;
	private String details;
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public CatalogItem() {
	}
	
	public CatalogItem(long id, String summary, String description, BigDecimal price, String thumb, String image,
			String category, String subcategory, String details) {
		super();
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.price = price;
		this.thumb = thumb;
		this.image = image;
		this.category = category;
		this.subcategory = subcategory;
		this.details = details;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
}
