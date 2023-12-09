
<div>
	<form action="${pageContext.request.contextPath}/api/createProduct" method="post" enctype="multipart/form-data">
		<label for="chk" aria-hidden="true">Create Product</label>
		<input type="text" name="name" placeholder="Name" required>
		<input type="text" name="stock" placeholder="Stock" required>
		<input type="text" name="price" placeholder="Price" required>
		<input type="text" name="type" placeholder="Type" required>
		<input type="text" name="description" placeholder="Description" required>
		<input class="image_picker" type="file" name="fileInput" id="fileInput" onchange="displayImage(this)" accept="image/*" />
		<div style="display: block; margin-left: auto; margin-right: auto; width: fit-content;">
			<img id="imagePreview" src="#" alt="Image Preview"
				style="max-width: 120px; max-height: 120px; display: none; margin-bottom: 1rem;">
		</div>
		<button style="margin: 0 auto; display: block;" class="button">Create</button>
	</form>
</div>

<script>
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

