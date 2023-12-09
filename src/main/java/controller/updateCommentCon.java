package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.OrderDAO;
import model.LineItem;
import DAO.CommentDAO;
import utility.CookieUtil;
import utility.DateTime;

@WebServlet({ "/api/updateComment" })
public class updateCommentCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CookieUtil cul = new CookieUtil();

		String currentUserId = cul.getUserIdFromCookie(request);
		String commentId = (request.getParameter("commentId"));
		String orderId = (request.getParameter("orderId"));
		String productId = (request.getParameter("productId"));
		String content = (request.getParameter("content"));
		Integer rating = Integer.parseInt(request.getParameter("rating"));

		CommentDAO codao = new CommentDAO();
		if (currentUserId != null && orderId != null) {

			CompletableFuture<Void> asyncTask = codao.updateComment(commentId, orderId, productId, content, rating)
					.thenAccept(result -> {
						try (PrintWriter out = response.getWriter()) {
							if (result) {
								out.print("updateComment,success");
							} else {
								out.print("updateComment,fail");
							}
						} catch (IOException e) {

							e.printStackTrace();
						}
					});
			try {
				asyncTask.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
