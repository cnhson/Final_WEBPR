package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cartId;
	private String ownerId;
	private String itemList;
	private List<LineItem> tempItemList;

	public Cart() {
		this.cartId = "";
		this.ownerId = "";
		this.itemList = "";
		this.tempItemList = new ArrayList<>();
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getItemList() {
		return itemList;
	}

	public void setItemList(String itemList) {
		this.itemList = itemList;
	}

	public List<LineItem> getTempItemList() {
		return tempItemList;
	}

	public void setTempItemList(List<LineItem> tempItemList) {
		this.tempItemList = tempItemList;
	}

	public void addItemToCart(String productId, String name, Integer quantity, Integer price, Integer subtotal) {
		List<LineItem> cartList = getTempItemList();
		if (cartList.isEmpty()) {
			cartList.add(new LineItem(productId, name, quantity, price, subtotal));
		} else {
			boolean itemExists = false;

			for (LineItem item : getTempItemList()) {
				if (item.getProductId().equals(productId)) {
					item.setQuantity(item.getQuantity() + 1);
					item.setSubtotal(item.getPrice() * item.getQuantity());
					itemExists = true;
					break;
				}
			}
			if (!itemExists) {
				cartList.add(new LineItem(productId, name, quantity, price, subtotal));
			}
		}
	}

	public void updateCartItem(String productId, int quantity) {
		for (LineItem item : getTempItemList()) {
			if (item.getProductId().equals(productId)) {
				item.setQuantity(quantity);
				item.setSubtotal(item.getPrice() * quantity);
				break;
			}
		}

	}

	public Integer calculateTotal() {
		Integer total = 0;

		for (LineItem item : getTempItemList()) {
			total += item.getSubtotal();
		}

		return total;
	}

	public void deleteItemFromCart(String productId) {
		getTempItemList().removeIf(item -> item.getProductId().equals(productId));
	}
}
