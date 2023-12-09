<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/resources/styles/main.css">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	<div class="main">
		<input type="checkbox" id="chk" aria-hidden="true">

		<div class="signup">
			<form action="${pageContext.request.contextPath}/api/register" method="post">
				<label for="chk" aria-hidden="true">Sign up</label>
				<input type="text" name="username" placeholder="User name" required>
				<input type="email" name="email" placeholder="Email" required>
				<input type="password" name="password" placeholder="Password" required>
				<button>Sign up</button>
			</form>
		</div>

		<div class="login">
			<form action="${pageContext.request.contextPath}/api/login" method="post">
				<label style="padding-top: 1rem" for="chk" aria-hidden="true">Login</label>
				<input type="email" name="email" placeholder="Email" required>
				<input type="password" name="password" placeholder="Password" required>
				<button>Login</button>
			</form>
		</div>
	</div>
</body>

</html>
