<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="header_bar">
	<div class="header_item">
		<a class="logo_icon" href="/">
			<img style="height: 4rem" src="/resources/images/logo.png" alt="cart" />
		</a>
		<div class="nav_links">
			<a href="/" class="nav_link_item">Home</a>
			<a href="/product/management" class="nav_link_item">My products</a>
			<a href="/history" class="nav_link_item">Order history</a>
		</div>
		<div style="margin: auto 1rem; display: flex">
			<form action="${pageContext.request.contextPath}/api/findProduct" method="post">
				<input type="text" name="productName" placeholder="Search something" />

			</form>
		</div>
		<div class="user_info">
			<a class="cart_icon" href="/mycart">
				<img style="width: 1.5rem; height: 1.5rem;" src="/resources/images/cart.png" alt="cart" />
			</a>
			<div class="dropdown">
				<jsp:include page="/api/loginCheck" />
				<a class="user_icon" onclick="toggleDropdown()">
					<img class="user_icon" src="/resources/images/fox_icecream.jpg" alt="fox" />
				</a>
				<div class="dropdown-content" id="dropdownContent">
					<c:choose>
						<c:when test="${loginCheck}">
							<a href="/profile">Profile</a>
							<a href="/api/logout">Logout</a>
						</c:when>
						<c:otherwise>
							<a href="/access">Login/Register</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</header>

<script>
	function toggleDropdown() {
		var dropdownContent = document.getElementById("dropdownContent");
		dropdownContent.style.display = (dropdownContent.style.display === "block") ? "none"
				: "block";
	}

	window.onclick = function(event) {
		if (!event.target.matches('.user_icon')) {
			var dropdowns = document.getElementsByClassName("dropdown-content");
			for (var i = 0; i < dropdowns.length; i++) {
				var openDropdown = dropdowns[i];
				if (openDropdown.style.display === "block") {
					openDropdown.style.display = "none";
				}
			}
		}
	}
</script>