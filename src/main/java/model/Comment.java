package model;

import java.io.Serializable;

public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	private String commentId;
	private String orderId;
	private String productId;
	private String content;
	private Integer rating;
	private String created;

	public Comment() {
		this.commentId = "";
		this.orderId = "";
		this.productId = "";
		this.content = "";
		this.rating = 0;
		this.created = "";
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContent() {
		return content;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
