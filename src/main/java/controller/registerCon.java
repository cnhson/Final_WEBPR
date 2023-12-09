package controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CartDAO;
import DAO.UserDAO;

@WebServlet({ "/api/register" })
public class registerCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAO udao = new UserDAO();
		CartDAO cdao = new CartDAO();
		String username = (request.getParameter("username"));
		String email = (request.getParameter("email"));
		String password = (request.getParameter("password"));

		CompletableFuture<Void> asyncTask = udao.registerAccount(username, password, email).thenAccept(createdUID -> {
			if (createdUID != null) {
				CompletableFuture<Void> asyncTask2 = cdao.createUserCart(createdUID).thenAccept(success -> {
					if (success) {
						try {
							response.sendRedirect("/access");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println("Create user's cart failed");
					}
				});
				asyncTask2.join();
				System.out.println("Registration successful");

			} else {
				System.out.println("Registration failed");
			}
		});
		asyncTask.join();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
