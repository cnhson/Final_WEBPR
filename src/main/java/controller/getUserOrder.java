package controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.OrderDAO;
import DAO.ProductDAO;
import utility.CookieUtil;

@WebServlet({ "/api/getOrder" })
public class getUserOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CookieUtil cul = new CookieUtil();
		String currentUserId = cul.getUserIdFromCookie(request);
		OrderDAO odao = new OrderDAO();
		if (currentUserId != null) {
			CompletableFuture<Void> asyncTask = odao.getUserOrders(currentUserId).thenAccept(data -> {
				if (data != null) {
					request.setAttribute("userOrders", data);
				} else {
					request.setAttribute("userOrders", null);
				}
			});

			try {
				asyncTask.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("/access");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
