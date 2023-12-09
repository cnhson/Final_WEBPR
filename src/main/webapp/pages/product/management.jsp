<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="margin-top: 1rem; margin-left: auto; margin-right: 7rem; width: fit-content;">
	<a href="create">
		<button class="button2">New +</button>
	</a>
</div>
<jsp:include page="/api/getUserProduct" />
<div class="product-container">
	<c:if test="${not empty userProducts}">
		<c:forEach var="product" items="${userProducts}">
			<form action="${pageContext.request.contextPath}/api/updateProduct" method="post" enctype="multipart/form-data"
				style="border-radius: 5px; border: 1px solid #cdcdcd; margin: 5px 5px; width: 24.25%">
				<input type="hidden" name="productId" value="${product.productId}" required>
				<input type="text" name="name" placeholder="Name" value="${product.name}" required>
				<input type="text" name="stock" placeholder="Stock" value="${product.stock}" required>
				<input type="text" name="price" placeholder="Price" value="${product.price}" required>
				<input type="text" name="type" placeholder="Type" value="${product.type}" required>
				<input type="text" name="description" placeholder="Description" value="${product.description}" required>
				<input class="image_picker" type="file" name="fileInput" id="fileInput" onchange="displayImage(this)"
					accept="image/*" />
				<div style="display: block; margin-left: auto; margin-right: auto; width: fit-content;">
					<img id="imagePreview" src="${product.image}" alt="Image Preview"
						style="max-width: 120px; max-height: 120px; margin-bottom: 1rem;">
				</div>
				<button style="margin: 0 auto; display: block;" class="button">Update</button>
			</form>

		</c:forEach>
	</c:if>
</div>
<script>
	function addToCartFunc(productId, price) {
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "/api/updateProduct", true);
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				if (xhr.status == 200) {
					console.log("Request successful");
					if (xhr.responseText) {
						var redirectUrl = xhr.responseText
								.split("updateProduct,")[1];

						if (redirectUrl == success) {
							window.location.href = management;
						}
					}
				} else {
					console.log("Request failed with status: " + xhr.status);
				}
			}
		};
		// Include cookies in the request
		xhr.withCredentials = true;
		var data = "productId=" + encodeURIComponent(productId) + "&stock="
				+ encodeURIComponent(stock) + "&price="
				+ encodeURIComponent(price) + "&type="
				+ encodeURIComponent(type) + "&description="
				+ encodeURIComponent(description);
		xhr.send(data);
	}

	function displayImage(input) {
		var file = input.files[0];
		var imagePreview = document.getElementById('imagePreview');

		if (file) {
			var reader = new FileReader();

			reader.onload = function(e) {
				imagePreview.src = e.target.result;
				imagePreview.style.display = 'block';
			};

			reader.readAsDataURL(file);
		} else {
			imagePreview.style.display = 'none';
		}
	}
</script>
