package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CartDAO;
import DAO.UserDAO;
import utility.CookieUtil;

@WebServlet({ "/api/loginCheck" })
public class loginCheckCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CookieUtil cul = new CookieUtil();
		String userId = cul.getUserIdFromCookie(request);
		if (userId == null) {
			request.setAttribute("loginCheck", false);
		} else {
			request.setAttribute("loginCheck", true);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
