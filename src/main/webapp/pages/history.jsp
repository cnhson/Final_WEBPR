<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/api/getOrder" />
<jsp:include page="/api/getUserProfile" />
<div class="product-container">
	<c:if test="${not empty userOrders}">
		<c:forEach var="item" items="${userOrders}">
			<form action="${pageContext.request.contextPath}/api/confirmOrder" method="post">
				<input type="hidden" name="orderId" value="${item.orderId}" required>
				<div class="product-frame">
					<p>
						<b>Order #${item.orderId}</b>
					</p>

					<p style="font-weight: 600; color: #000000a8; margin-top: 0.2rem">${item.created}</p>
					<c:if test="${not empty userProfile}">
						<p style="font-weight: 500; color: #000000a8; margin-top: 0.5rem">Address: ${userProfile.address}</p>
					</c:if>
					<c:forEach var="orderList" items="${item.tempItemList}">
						<div class="order-frame">
							<img style="height: 50px; width: 50px; border-radius: 50%; margin: auto 0;"
								src="/uploads/${orderList.productId}.png">
							<div class="order-detail">
								<p>
									<b>${orderList.name}</b>
								</p>
								<div style="display: flex; margin-top: 1.2rem">
									<p>
										<b>$ ${orderList.price}</b>
									</p>
									<p style="margin-left: auto">
										<b>Qty: ${orderList.quantity}</b>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
					<c:choose>
						<c:when test="${item.isReceived == '1'}">
							<div style="display: flex; margin-top: 1rem" id="div2">
								<div class="cart-total" style="padding: 15px 8px" id="total">$${item.total}</div>

							</div>
						</c:when>
						<c:otherwise>
							<div style="display: flex; margin-top: 1rem" id="div2">
								<div class="cart-total" id="total">$${item.total}</div>
								<button class="button2" style="padding: 0 0.5rem">Confirm</button>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</form>
		</c:forEach>
	</c:if>
</div>