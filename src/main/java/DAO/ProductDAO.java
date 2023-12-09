package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import model.Product;
import utility.DB;

public class ProductDAO {

	public CompletableFuture<List<Product>> getAllProducts() {
		return CompletableFuture.supplyAsync(() -> {
			List<Product> products = new ArrayList<>();

			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT * FROM Product");
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Product product = new Product();
					product.setProductId(rs.getString("productId"));
					product.setName(rs.getString("name"));
					product.setStock(rs.getInt("stock"));
					product.setPrice(rs.getInt("price"));
					product.setOwnerId(rs.getString("ownerId"));
					product.setType(rs.getString("type"));
					product.setDescription(rs.getString("description"));
					product.setImage(rs.getString("image"));
					products.add(product);
				}

				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return products;
		});
	}
	
	public CompletableFuture<List<Product>> getUserProduct(String ownerId) {
		return CompletableFuture.supplyAsync(() -> {
			List<Product> products = new ArrayList<>();

			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT * FROM Product where ownerId =?");
				st.setString(1, ownerId);
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Product product = new Product();
					product.setProductId(rs.getString("productId"));
					product.setName(rs.getString("name"));
					product.setStock(rs.getInt("stock"));
					product.setPrice(rs.getInt("price"));
					product.setOwnerId(rs.getString("ownerId"));
					product.setType(rs.getString("type"));
					product.setDescription(rs.getString("description"));
					product.setImage(rs.getString("image"));
					products.add(product);
				}

				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return products;
		});
	}
	
	public CompletableFuture<List<Product>> findProduct(String productName) {
		return CompletableFuture.supplyAsync(() -> {
			List<Product> products = new ArrayList<>();

			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT * FROM Product where name like'%" +productName+"%'");
				
				ResultSet rs = st.executeQuery();

				while (rs.next()) {
					Product product = new Product();
					product.setProductId(rs.getString("productId"));
					product.setName(rs.getString("name"));
					product.setStock(rs.getInt("stock"));
					product.setPrice(rs.getInt("price"));
					product.setOwnerId(rs.getString("ownerId"));
					product.setType(rs.getString("type"));
					product.setDescription(rs.getString("description"));
					product.setImage(rs.getString("image"));
					products.add(product);
				}

				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return products;
		});
	}

	public CompletableFuture<Boolean> createProduct(String productId, String name, Integer stock, Integer price,
			String ownerId, String type, String description, String image) {

		return CompletableFuture.supplyAsync(() -> {

			Product product = new Product();
			product.setProductId(productId);
			product.setName(name);
			product.setStock(stock);
			product.setPrice(price);
			product.setOwnerId(ownerId);
			product.setType(type);
			product.setDescription(description);
			product.setImage(image);

			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement("INSERT INTO product VALUES(?,?,?,?,?,?,?,?)");
				st.setString(1, product.getProductId());
				st.setString(2, product.getName());
				st.setInt(3, product.getStock());
				st.setInt(4, product.getPrice());
				st.setString(5, product.getOwnerId());
				st.setString(6, product.getType());
				st.setString(7, product.getDescription());
				st.setString(8, product.getImage());

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

	public CompletableFuture<Boolean> updateProduct(String productId, String name, Integer stock, Integer price,
			String ownerId, String type, String description, String image) {

		return CompletableFuture.supplyAsync(() -> {
			Product product = new Product();
			product.setProductId(productId);
			product.setName(name);
			product.setStock(stock);
			product.setPrice(price);
			product.setOwnerId(ownerId);
			product.setType(type);
			product.setDescription(description);
			product.setImage(image);
			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement(
						"UPDATE product SET name=?, stock=?, price=?, type=?, description=?, image=? WHERE ownerId=? and productId=?");
				st.setString(1, product.getName());
				st.setInt(2, product.getStock());
				st.setInt(3, product.getPrice());
				st.setString(4, product.getType());
				st.setString(5, product.getDescription());
				st.setString(6, product.getImage());
				st.setString(7, product.getOwnerId());
				st.setString(8, product.getProductId());

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
