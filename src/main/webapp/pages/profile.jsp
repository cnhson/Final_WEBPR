
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/api/getUserProfile" />
<div>
	<c:if test="${not empty userProfile}">
		<form action="${pageContext.request.contextPath}/api/updateProfile" method="post">
			<label for="chk" aria-hidden="true">Profile</label>
			<input type="text" name="username" value="${userProfile.username}" required>
			<input type="text" name="email" value="${userProfile.email}" required>
			<input type="text" name="password" value="${userProfile.password}" required>
			<input type="text" name="address" placeholder="address" value="${userProfile.address}" required>
			<button style="margin: 0 auto; display: block;" class="button">Update</button>
		</form>

	</c:if>
</div>

