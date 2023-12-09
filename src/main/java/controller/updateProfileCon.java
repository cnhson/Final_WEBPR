package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet({ "/api/updateProfile" })
public class updateProfileCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAO udao = new UserDAO();
		CookieUtil cul = new CookieUtil();

		String userId = cul.getUserIdFromCookie(request);
		String email = (request.getParameter("email"));
		String password = (request.getParameter("password"));
		String address = (request.getParameter("address"));

		CompletableFuture<Void> asyncTask = udao.updateAccount(userId, email, password, address, "")
				.thenAccept(success -> {
					try {
						if (success) {
							response.sendRedirect("/profile");

						} else {
							response.sendRedirect("/profile");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
