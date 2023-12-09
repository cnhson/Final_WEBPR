package model;

import java.io.Serializable;


public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productId;
	private String name;
	private Integer stock;
	private Integer price;
	private String ownerId;
	private String type;
	private String description;
	private String image;

	public Product() {
		super();
		this.productId = "";
		this.name = "";
		this.stock = 0;
		this.price = 0;
		this.ownerId = "";
		this.type = "";
		this.description = "";
		this.image = "";
	}

	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
