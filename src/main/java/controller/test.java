package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LineItem;
import utility.CartSession;
import utility.Conversion;
import DAO.CartDAO;
import utility.CookieUtil;

@WebServlet({ "/api/test/*" })
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CartSession cs = new CartSession();
		System.out.println(cs.getCartSession(request));
		
		
		String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            // Get the last part of the path
            System.out.println(pathInfo.split("/")[1]);
        } 
		
//		Conversion conv = new Conversion();
//		List<LineItem> lineItems = List.of(new LineItem("1", 1, 200, 300), new LineItem("2", 2, 150, 300));
//
//		String jsonArray = conv.ListToJson(lineItems);
//		//System.out.println(jsonArray);
//
//		CartDAO cdao = new CartDAO();
//		CookieUtil cul = new CookieUtil();
//		String userId = cul.getUserIdFromCookie(request);
//	
//		if (userId != null) {
//			cdao.getUserCart(userId).thenAccept(data -> {
//				if (data != null) {
//					System.out.println(data);
//				} else {
//					System.out.println("Get cart failed");
//				}
//			});
//		} else {
//			// response.sendRedirect("/");
//			System.out.println("Empty");
//		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
