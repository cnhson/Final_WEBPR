package model;

import java.io.Serializable;

public class LineItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String productId;
	private String name;
	private Integer quantity;
	private Integer price;
	private Integer subtotal;

	public LineItem() {
		this.productId = "";
		this.name = "";
		this.quantity = 0;
		this.price = 0;
		this.subtotal = 0;
	}

	public LineItem(String productId, String name, Integer quantity, Integer price, Integer subtotal) {
		this.productId = productId;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Integer subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
