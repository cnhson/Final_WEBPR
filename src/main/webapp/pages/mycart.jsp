<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/api/cart" />
<div class="cart-container">
	<c:if test="${not empty cartList}">
		<table class="cart-table">
			<thead>
				<tr>
					<th style="width: 10%">Image</th>
					<th style="width: 30%">Name</th>
					<th>Quantity</th>
					<th>Price</th>
					<th>Subtotal</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${cartList}">
					<tr>
						<td class="cart-row">
							<img style="width: 8rem; height: 8rem; border-radius: 10px" src="/uploads/${item.productId}.png" />
						</td>
						<td class="cart-row">${item.name}</td>
						<td style="width: 14rem;">
							<div class="cart-row-box">
								<input id="quantity-${item.productId}"
									style="text-align: center; margin: 0 10px 0 0; width: 5.5rem; padding: 24px;" type="text" name="quantity"
									placeholder="User name" value="${item.quantity}" required style="width: 3rem;">
								<button style="margin: 0" type="button" class="button" onclick="myCartFunc('${item.productId}', 'update')">Update</button>
							</div>
						</td>
						<td class="cart-row" id="price-${item.productId}">$ ${item.price}</td>
						<td class="cart-row" id="subtotal-${item.productId}">${item.subtotal}</td>
						<td class="cart-row">
							<button type="button" class="button" onclick="myCartFunc('${item.productId}', 'delete')">Delete</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div style="margin-right: auto; display: flex; margin-top: 2rem">
			<a style="width: fit-content" href="/">
				<button style="margin-left: 1rem" type="button" class="button2">Continue shopping</button>
			</a>
		</div>
		<div style="margin-left: auto; display: flex; margin-top: 2rem">
			<div class="cart-total" id="total"></div>
			<div id="paypal-button-container"></div>
			<button style="margin-left: 1rem" type="button" class="button" onclick="createOrder()">Checkout</button>
		</div>
	</c:if>
	<c:if test="${empty cartList}">
		<div style="font-size: 2.5rem; margin: 2rem auto; font-weight: 600;">Your cart is empty</div>
	</c:if>
</div>

<script>
	function myCartFunc(productId, prompt) {
		var xhr = new XMLHttpRequest();
		var quantity = document.getElementById("quantity-" + productId).value;
		var price = document.getElementById("price-" + productId).innerHTML
				.split("$ ")[1];
		var subtotalEle = document.getElementById("subtotal-" + productId);
		var tempSubtotal = parseInt(quantity) * parseInt(price);
		xhr.open("POST", "/api/cart/" + prompt, true);
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					subtotalEle.innerText = tempSubtotal.toFixed(0);
					console.log("Request successful");
					calculateTotalSubtotal();

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
		xhr.withCredentials = true;
		var data;

		if (prompt == "update") {
			data = "productId=" + encodeURIComponent(productId) + "&quantity="
					+ encodeURIComponent(quantity);

		} else if (prompt == "delete") {
			data = "productId=" + encodeURIComponent(productId);
		}
		xhr.send(data);

	}

	function createOrder() {
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/api/order/create", true);
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					console.log("Request successful");

					if (xhr.responseText) {
						var redirectUrl = xhr.responseText
								.split("createUserOrder,")[1];
						console.log(redirectUrl);
						if (redirectUrl == "success") {
							window.location.href = "/history";
						}
					}
				} else {
					console.log("Request failed with status: " + xhr.status);
				}
			}
		};
		xhr.withCredentials = true;
		xhr.send();
	}

	function calculateTotalSubtotal() {
		var subtotalElements = document.querySelectorAll('[id^="subtotal-"]');
		var totalSubtotal = 0;

		subtotalElements.forEach(function(element) {
			totalSubtotal += parseFloat(element.textContent);
		});

		document.getElementById('total').innerText = '$ '
				+ totalSubtotal.toFixed(2);
	}

	calculateTotalSubtotal();
</script>

