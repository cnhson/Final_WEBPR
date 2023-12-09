package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import model.Cart;
import model.LineItem;
import model.Order;
import model.Product;
import utility.Conversion;
import utility.DB;

public class OrderDAO {

	public CompletableFuture<Boolean> createUserOrder(String ownerId, Integer total, String created, String itemList) {

		return CompletableFuture.supplyAsync(() -> {
			Random rnd = new Random();
			int randomID = 100000 + rnd.nextInt(900000);
			Order order = new Order();
			order.setOrderId(String.valueOf(randomID));
			order.setOwnerId(ownerId);
			order.setTotal(total);
			order.setCreated(created);
			order.setItemList(itemList);

			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement("INSERT INTO webpr.order VALUES(?,?,?,?,?,?)");
				st.setString(1, order.getOrderId());
				st.setString(2, order.getOwnerId());
				st.setInt(3, order.getTotal());
				st.setString(4, order.getCreated());
				st.setString(5, order.getItemList());
				st.setInt(6, 0);
				
				int rowsAffected = st.executeUpdate();

				st.close();
				con.close();

				if (rowsAffected > 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false; 
			}
		});
	}



	public CompletableFuture<List<Order>> getUserOrders(String ownerId) {
		return CompletableFuture.supplyAsync(() -> {
			List<Order> orders = new ArrayList<>();
			List<LineItem> orderList = new ArrayList<>();
			Conversion conv = new Conversion();
			
			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT * FROM webpr.order where ownerId = ?");
				st.setString(1, ownerId);
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getString("orderId"));
					order.setOwnerId(rs.getString("ownerId"));
					order.setTotal(rs.getInt("total"));
					String itemListJson = (rs.getString("itemList"));
					if (itemListJson != null && !itemListJson.trim().isEmpty()) {
						System.out.println("order list not Null");
						orderList = conv.JsonToList(itemListJson);
					} else {
						System.out.println("order List Null");
						break;
					}
					/* order.setItemList(rs.getString("itemList")); */
					order.setTempItemList(orderList);
					order.setCreated(rs.getString("created"));
					order.setIsReceived(rs.getInt("isReceived"));
					orders.add(order);
				}

				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return orders;
		});
	}

	public CompletableFuture<Boolean> confirmOrder(String ownerId, String orderId) {

		return CompletableFuture.supplyAsync(() -> {
			Order order = new Order();
			order.setOwnerId(ownerId);
			order.setOrderId(orderId);
			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement("UPDATE webpr.order SET isReceived=1 WHERE ownerId=? and orderId =?");
				st.setString(1, order.getOwnerId());
				st.setString(2, order.getOrderId());
				int rowsAffected = st.executeUpdate();

				st.close();
				con.close();

				if (rowsAffected > 0)
					return true;
				else
					return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		});
	}
}
