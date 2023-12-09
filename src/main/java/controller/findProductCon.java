package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ProductDAO;

@WebServlet({ "/api/findProduct" })
public class findProductCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductDAO pdao = new ProductDAO();
		String productName = (request.getParameter("productName"));
		CompletableFuture<Void> asyncTask = pdao.findProduct(productName).thenAccept(data -> {
			if (data != null) {
				request.setAttribute("foundProducts", data);
				request.setAttribute("productName", productName);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/search.jsp");
				try {

					dispatcher.forward(request, response);

				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				request.setAttribute("foundProducts", null);
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
