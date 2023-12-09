package controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CartDAO;
import model.LineItem;
import utility.CartSession;
import utility.CookieUtil;

@WebServlet({ "/api/cart" })
public class getCartCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CartDAO cdao = new CartDAO();
		CartSession cs = new CartSession();
		CookieUtil cul = new CookieUtil();
		String userId = cul.getUserIdFromCookie(request);
		List<LineItem> currentTempCartList = cs.getCartSession(request);
		if (userId != null) {
			CompletableFuture<Void> asyncTask2 = cdao.getUserCart(cul.getUserIdFromCookie(request)).thenAccept(data -> {

				if (data != null) {
					cs.setCartSession(request, data);
					request.setAttribute("cartList", currentTempCartList);
				} else {

					cs.setCartSession(request, data);
					request.setAttribute("cartList", null);
					System.out.println("Get cart failed");
				}
				/* System.out.println("Added cart to httpsession"); */
			});
			asyncTask2.join();

		} else {
			request.setAttribute("cartList", null);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
