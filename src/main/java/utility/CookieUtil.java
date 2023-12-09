package utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public void setUserIdCookie(HttpServletResponse response, String userId) {
		Cookie loginCookie = new Cookie("userId", userId);
		loginCookie.setMaxAge(24 * 60 * 60);
		loginCookie.setPath("/");
		response.addCookie(loginCookie);
	}

	public String getUserIdFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userId".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public void clearUserCookie(HttpServletResponse response) {
		Cookie loginCookie = new Cookie("userId", "");
		loginCookie.setMaxAge(0);
		loginCookie.setPath("/");
		Cookie sessionCookie = new Cookie("JSESSIONID", "");
		sessionCookie.setMaxAge(0);
		sessionCookie.setPath("/");
		response.addCookie(loginCookie);
	}
}
