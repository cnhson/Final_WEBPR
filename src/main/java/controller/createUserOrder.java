package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DAO.CartDAO;
import DAO.OrderDAO;
import model.Cart;
import model.LineItem;
import utility.CartSession;
import utility.Conversion;
import utility.CookieUtil;
import utility.DateTime;

@WebServlet("/api/order/create")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)
public class createUserOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Conversion conv = new Conversion();
			CookieUtil cul = new CookieUtil();
			CartSession cartsession = new CartSession();
			Cart cart = new Cart();
			OrderDAO odao = new OrderDAO();
			CartDAO cdao = new CartDAO();
			CartSession cs = new CartSession();
			DateTime dt = new DateTime();

			String userId = cul.getUserIdFromCookie(request);
			List<LineItem> cartSessionList = cs.getCartSession(request);

			String dateTime = dt.getCurrentDateTime();
			if (userId != null && cartSessionList != null) {
				cart.setTempItemList(cartSessionList);

				Integer total = cart.calculateTotal();
				String itemList = conv.ListToJson(cs.getCartSession(request));

				CompletableFuture<Void> asyncTask = odao.createUserOrder(userId, total, dateTime, itemList)
						.thenAccept(success1 -> {
							if (success1) {
								CompletableFuture<Void> asyncTask2 = cdao.addToCart(userId, "[]")
										.thenAccept(success2 -> {
											try (PrintWriter out = response.getWriter()) {

												if (success2) {
													cartsession.clearCartSession(request);
													out.print("createUserOrder,success");
													System.out.println("Clear cart successful");
												} else {
													out.print("createUserOrder,fail");
													System.out.println("Clear cart failed");
												}
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										});
								asyncTask2.join();
								System.out.println("Create order successful");
							} else {
								System.out.println("Create order failed");
							}
						});
				asyncTask.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Create order error.");
		}
	}

}
