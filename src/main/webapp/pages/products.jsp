<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/api/products" />
<div class="product-container">
	<c:if test="${not empty products}">
		<c:forEach var="product" items="${products}">
			<div class="product-frame">
				<img src="${product.image}" alt="product" style="height: 285px; width: 285px; border-radius: 5px;" />
				<p class="product_name">${product.name}</p>

				<div style="display: flex; margin-top: 0.5rem">
					<p class="product_stock">${product.stock}</p>
					<p class="product_type">${product.type}</p>
				</div>
				<p class="product_des">${product.description}</p>
				<div style="display: flex">
					<p class="product_price">$ ${product.price}</p>
					<button type="button" class="button" onclick="addToCartFunc('${product.productId}', '${product.price}')">
						Add</button>
				</div>

			</div>
		</c:forEach>
	</c:if>
</div>
<script>
	function addToCartFunc(productId, price) {
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/api/cart/add", true);
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					console.log("Request successful");
					if (xhr.responseText) {
						var redirectUrl = xhr.responseText.split("NeedLogin,")[1];

						if (redirectUrl) {
							window.location.href = redirectUrl;
						}
					}
				} else {
					console.log("Request failed with status: " + xhr.status);
				}
			}
		};
		// Include cookies in the request
		xhr.withCredentials = true;
		var data = "productId=" + encodeURIComponent(productId) + "&price="
				+ encodeURIComponent(price);
		xhr.send(data);
	}
</script>
