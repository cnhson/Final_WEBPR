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

@WebServlet({ "/api/logout" })
public class logoutCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CookieUtil cul = new CookieUtil();
		CartSession cartsession = new CartSession();
		try {
			cartsession.clearCartSession(request);
			cul.clearUserCookie(response);
			response.sendRedirect("/access");
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
