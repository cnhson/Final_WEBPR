package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import model.Product;
import model.User;
import utility.DB;

public class UserDAO {

	public CompletableFuture<String> registerAccount(String username, String password, String email) {

		return CompletableFuture.supplyAsync(() -> {
			Random rnd = new Random();
			int randomID = 100000 + rnd.nextInt(900000);
			User user = new User();
			user.setUserId(String.valueOf(randomID));
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setPaypal("");
			user.setAddress("");
			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
				st.setString(1, user.getUserId());
				st.setString(2, user.getUsername());
				st.setString(3, user.getEmail());
				st.setString(4, user.getPassword());
				st.setString(5, user.getAddress());
				st.setString(6, user.getPaypal());

				int rowsAffected = st.executeUpdate();

				st.close();
				con.close();

				if (rowsAffected > 0)
					return String.valueOf(randomID);
				else
					return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null; // Return false if an exception occurs
			}
		});
	}

	public CompletableFuture<String> loginAccount(String email, String password) {
		return CompletableFuture.supplyAsync(() -> {
			User user = new User();
			String storedUserId = null;
			user.setEmail(email);
			user.setPassword(password);

			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT userId, email, password FROM User WHERE email = ?");
				st.setString(1, user.getEmail());

				ResultSet rs = st.executeQuery();

				if (rs.next()) {
					String storedPassword = rs.getString("password");
					if (storedPassword.equals(user.getPassword())) {
						storedUserId = rs.getString("userId");
					}
				}
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return storedUserId;
		});
	}

	public CompletableFuture<User> getUserProfile(String userId) {
		return CompletableFuture.supplyAsync(() -> {
			User user = new User();
			user.setUserId(userId);

			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("select * from user where userId = ?");
				st.setString(1, user.getUserId());

				ResultSet rs = st.executeQuery();

				if (rs.next()) {
					user.setUserId(rs.getString("userId"));
					user.setUsername(rs.getString("username"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setAddress(rs.getString("address"));
				}
				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}


			return user;
		});
	}

	public CompletableFuture<Boolean> updateAccount(String userId, String email, String password, String address,
			String paypal) {

		return CompletableFuture.supplyAsync(() -> {
			User user = new User();
			user.setUserId(userId);
			user.setPassword(password);
			user.setEmail(email);
			user.setPaypal(paypal);
			user.setAddress(address);
			try {
				Connection con = DB.initializeDatabase();

				PreparedStatement st = con
						.prepareStatement("UPDATE user SET email=?, password=?, address=?, paypal=? WHERE userId=?");
				st.setString(1, user.getEmail());
				st.setString(2, user.getPassword());
				st.setString(3, user.getAddress());
				st.setString(4, user.getPaypal());
				st.setString(5, user.getUserId());

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
