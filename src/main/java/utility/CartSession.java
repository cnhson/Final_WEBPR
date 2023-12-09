package utility;

import model.LineItem;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CartSession {

	public void setCartSession(HttpServletRequest request, List<LineItem> cartItems) {
		try {
			HttpSession session = request.getSession();
			session.setAttribute("cartItemList", cartItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<LineItem> getCartSession(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			List<LineItem> lineItemList = (List<LineItem>) session.getAttribute("cartItemList");
			return lineItemList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void clearCartSession(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
