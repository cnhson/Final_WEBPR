package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CartDAO;
import model.Cart;
import model.LineItem;
import utility.CartSession;
import utility.Conversion;
import utility.CookieUtil;

@WebServlet({ "/api/cart/*" })
public class cartCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		String action = "";
		if (pathInfo != null) {
			action = pathInfo.split("/")[1];

			Conversion conv = new Conversion();
			CookieUtil cul = new CookieUtil();
			Cart cart = new Cart();
			CartDAO cdao = new CartDAO();
			CartSession cs = new CartSession();

			String userId = cul.getUserIdFromCookie(request);
			String productId = request.getParameter("productId");

			List<LineItem> cartSessionList = cs.getCartSession(request);
			if (userId != null && cartSessionList != null) {
				cart.setTempItemList(cartSessionList);
				if (action.equals("add")) {
					String name = request.getParameter("name");
					Integer price = Integer.parseInt(request.getParameter("price"));
					cart.addItemToCart(productId, name, 1, price, price);
					cs.setCartSession(request, cart.getTempItemList());

				} else if (action.equals("update")) {

					Integer quantity = Integer.parseInt(request.getParameter("quantity"));
					cart.updateCartItem(productId, quantity);
					cs.setCartSession(request, cart.getTempItemList());

				} else if (action.equals("delete")) {

					cart.deleteItemFromCart(productId);
					cs.setCartSession(request, cart.getTempItemList());
					try (PrintWriter out = response.getWriter()) {
						out.print("NeedLogin,/mycart");
					}

				}
				String itemList = conv.ListToJson(cs.getCartSession(request));

				cdao.addToCart(userId, itemList).thenAccept(success -> {
					if (success) {
						System.out.println("Update cart successful");
					} else {
						System.out.println("Update cart failed");
					}
				});
			} else {
				try (PrintWriter out = response.getWriter()) {
					out.print("NeedLogin,/");
				}
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
