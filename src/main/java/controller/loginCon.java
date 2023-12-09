package controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CartDAO;
import DAO.UserDAO;
import model.LineItem;
import utility.CartSession;
import utility.CookieUtil;

@WebServlet({ "/api/login" })
public class loginCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAO udao = new UserDAO();
		CartDAO cdao = new CartDAO();
		CookieUtil cul = new CookieUtil();
		CartSession cs = new CartSession();
		String email = (request.getParameter("email"));
		String password = (request.getParameter("password"));

		CompletableFuture<Void> asyncTask = udao.loginAccount(email, password).thenAccept(storeUserId -> {
			if (storeUserId != null) {
				cul.setUserIdCookie(response, storeUserId);
				CompletableFuture<Void> asyncTask2 = cdao.getUserCart(cul.getUserIdFromCookie(request)).thenAccept(data -> {
					if (data != null) {
						cs.setCartSession(request, data);
					} else {

						cs.setCartSession(request, data);
						System.out.println("Get cart failed");
					}
					System.out.println("Added cart to httpsession");
				});
				asyncTask2.join();
				try {
					response.sendRedirect("/");
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Login successful");
			} else {
				System.out.println("Login failed");
			}
		});

		try {
			asyncTask.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
