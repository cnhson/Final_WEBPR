package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DAO.ProductDAO;
import model.Comment;
import utility.CookieUtil;

@WebServlet("/api/updateProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)
public class updateProductCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			CookieUtil cul = new CookieUtil();
			ProductDAO pdao = new ProductDAO();

			String productId = request.getParameter("productId");
			String name = (request.getParameter("name"));
			Integer stock = Integer.parseInt(request.getParameter("stock"));
			Integer price = Integer.parseInt(request.getParameter("price"));
			String ownerId = cul.getUserIdFromCookie(request);
			String type = (request.getParameter("type"));
			String description = (request.getParameter("description"));

			Part filePart = request.getPart("fileInput");
			String fileName = productId + ".png";
			String imagePath = "/uploads/" + fileName;

			String projectOnDisk = getServletContext().getRealPath("/").split(".metadata")[0];
			String projectRootPath = getServletContext().getRealPath("/").split("\\\\wtpwebapps\\\\")[1]
					+ "src\\main\\webapp\\";
			String uploadDirectory = projectOnDisk + projectRootPath + "uploads";

			File uploadDir = new File(uploadDirectory);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			try (InputStream input = filePart.getInputStream()) {
				Files.copy(input, new File(uploadDirectory, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().println(e);
			}

			if (ownerId != null) {
				CompletableFuture<Void> asyncTask = pdao
						.updateProduct(productId, name, stock, price, ownerId, type, description, imagePath)
						.thenAccept(result -> {
							try {
								if (result) {
									response.sendRedirect("/product/management");
								} else {
									response.sendRedirect("/product/management");
								}
							} catch (IOException e) {

								e.printStackTrace();
							}
						});
				try {
					asyncTask.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error uploading file.");
		}
	}

}
