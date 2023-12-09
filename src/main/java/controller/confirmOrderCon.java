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

@WebServlet({ "/api/confirmOrder" })
public class confirmOrderCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CookieUtil cul = new CookieUtil();

		String currentUserId = cul.getUserIdFromCookie(request);
		String orderId = (request.getParameter("orderId"));
		OrderDAO odao = new OrderDAO();
		if (currentUserId != null && orderId != null) {
			CompletableFuture<Void> asyncTask = odao.confirmOrder(currentUserId, orderId).thenAccept(data -> {
				try {
					if (data) {
						response.sendRedirect("/history");
					} else {
						response.sendRedirect("/history");
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
		} else {
			response.sendRedirect("/history");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
