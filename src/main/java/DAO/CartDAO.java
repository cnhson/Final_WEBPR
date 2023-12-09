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
import utility.Conversion;
import utility.DB;

public class CartDAO {

	public CompletableFuture<Boolean> createUserCart(String ownerId) {

		return CompletableFuture.supplyAsync(() -> {
			Random rnd = new Random();
			int randomID = 100000 + rnd.nextInt(900000);
			Cart cart = new Cart();
			cart.setCartId(String.valueOf(randomID));
			cart.setOwnerId(ownerId);

			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement("INSERT INTO cart VALUES(?,?,?)");
				st.setString(1, cart.getCartId());
				st.setString(2, cart.getOwnerId());
				st.setString(3, "");

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

	public CompletableFuture<Boolean> addToCart(String ownerId, String itemList) {

		return CompletableFuture.supplyAsync(() -> {
			Cart cart = new Cart();
			cart.setOwnerId(ownerId);
			cart.setItemList(itemList);
			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement("UPDATE cart SET itemList=? WHERE ownerId=?");
				st.setString(1, cart.getItemList());
				st.setString(2, cart.getOwnerId());
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

	public CompletableFuture<List<LineItem>> getUserCart(String ownerId) {
		return CompletableFuture.supplyAsync(() -> {
			List<LineItem> cartList = new ArrayList<>();
			Cart cart = new Cart();
			Conversion conv = new Conversion();
			cart.setOwnerId(ownerId);
			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT itemList FROM cart where ownerId = ?");
				st.setString(1, cart.getOwnerId());
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					String itemListJson = (rs.getString("itemList"));
					if (itemListJson != null && !itemListJson.trim().isEmpty()) {
						System.out.println("Not Null");
						cartList = conv.JsonToList(itemListJson);
					} else {
						System.out.println("Null");
						break;
					}
				}

				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return cartList;
		});
	}

}
