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
import utility.CartSession;


@WebServlet({ "/api/products" })
public class getAllProductsCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductDAO pdao = new ProductDAO();
		CompletableFuture<Void> asyncTask = pdao.getAllProducts().thenAccept(data -> {
			if (data != null) {
				request.setAttribute("products", data);
			} else {
				request.setAttribute("products", null);
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
