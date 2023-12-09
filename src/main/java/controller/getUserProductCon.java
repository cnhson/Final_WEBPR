package controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;
import utility.CookieUtil;

@WebServlet({ "/api/getUserProduct" })
public class getUserProductCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CookieUtil cul = new CookieUtil();
		String ownerId = cul.getUserIdFromCookie(request);

		ProductDAO pdao = new ProductDAO();
		if (ownerId != null) {
			CompletableFuture<Void> asyncTask = pdao.getUserProduct(ownerId).thenAccept(data -> {
				if (data != null) {
					request.setAttribute("userProducts", data);
				} else {
					request.setAttribute("userProducts", null);
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
