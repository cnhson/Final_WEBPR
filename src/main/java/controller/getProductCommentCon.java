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

@WebServlet({ "/api/getProductComment" })
public class getProductCommentCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String orderId = (request.getParameter("productId"));

		CommentDAO codao = new CommentDAO();
		if (orderId != null) {

			CompletableFuture<Void> asyncTask = codao.getCommentByProductId(orderId)
					.thenAccept(result -> {
						try (PrintWriter out = response.getWriter()) {
							if (result != null) {
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
