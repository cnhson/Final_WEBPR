package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import model.Comment;
import model.Order;
import utility.DB;

public class CommentDAO {

	public CompletableFuture<Comment> getCommentByProductId(String productId) {
		return CompletableFuture.supplyAsync(() -> {
			Comment comment = null;

			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("SELECT * FROM Comment WHERE productId = ?");
				st.setString(1, productId);

				ResultSet rs = st.executeQuery();

				if (rs.next()) {
					comment = new Comment();
					comment.setCommentId(rs.getString("commentId"));
					comment.setOrderId(rs.getString("orderId"));
					comment.setContent(rs.getString("content"));
					comment.setRating(rs.getInt("rating"));
					comment.setCreated(rs.getString("created"));
				}

				rs.close();
				st.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return comment;
		});
	}

	public CompletableFuture<Boolean> createComment(String orderId, String productId, String content, int rating,
			String created) {
		return CompletableFuture.supplyAsync(() -> {
			Random rnd = new Random();
			int randomID = 100000 + rnd.nextInt(900000);
			Comment comment = new Comment();
			comment.setCommentId(String.valueOf(randomID));
			comment.setOrderId(orderId);
			comment.setProductId(productId);
			comment.setContent(content);
			comment.setRating(rating);
			comment.setCreated(created);
			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement(
						"INSERT INTO Comment (commentId, orderId, productId, content, rating, created) VALUES (?, ?, ?, ?, ?, ?)");
				st.setString(1, comment.getCommentId());
				st.setString(2, comment.getOrderId());
				st.setString(3, comment.getProductId());
				st.setString(4, comment.getContent());
				st.setInt(5, comment.getRating());
				st.setString(6, comment.getCreated());

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

	public CompletableFuture<Boolean> updateComment(String commentId, String orderId, String productId, String content,
			int rating) {
		return CompletableFuture.supplyAsync(() -> {

			Comment comment = new Comment();
			comment.setCommentId(commentId);
			comment.setOrderId(orderId);
			comment.setProductId(productId);
			comment.setContent(content);
			comment.setRating(rating);
			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement(
						"UPDATE Comment SET content=?, rating=? WHERE commentId=? and orderId=? and productId = ?");
				st.setString(1, comment.getContent());
				st.setInt(2, comment.getRating());
				st.setString(3, comment.getCommentId());
				st.setString(4, comment.getOrderId());
				st.setString(5, comment.getProductId());

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

	public CompletableFuture<Boolean> deleteComment(String commentId) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Connection con = DB.initializeDatabase();
				PreparedStatement st = con.prepareStatement("DELETE FROM Comment WHERE commentId = ?");
				st.setString(1, commentId);

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
