package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orderId;
	private String ownerId;
	private String itemList;
	private Integer total;
	private String created;
	private Integer isReceived;
	private List<LineItem> tempItemList;

	public Order() {
		super();
		this.orderId = "";
		this.ownerId = "";
		this.itemList = "";
		this.total = 0;
		this.created = "";
		this.isReceived = 0;
		this.tempItemList = new ArrayList<>();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Integer getIsReceived() {
		return isReceived;
	}

	public void setIsReceived(Integer isReceived) {
		this.isReceived = isReceived;
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
}
