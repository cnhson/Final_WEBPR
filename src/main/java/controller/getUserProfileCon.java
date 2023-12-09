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

@WebServlet({ "/api/getUserProfile" })
public class getUserProfileCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAO udao = new UserDAO();

		CookieUtil cul = new CookieUtil();
		String userId = cul.getUserIdFromCookie(request);

		CompletableFuture<Void> asyncTask = udao.getUserProfile(userId).thenAccept(data -> {
			if (data != null) {
				System.out.println(data.getUsername());
				request.setAttribute("userProfile", data);
			} else {
				request.setAttribute("userProfile", null);
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
