<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MultiMarket</title>
<link rel="stylesheet" href="/resources/styles/layout.css">
</head>
<body>
	<jsp:include page="header.jsp" />
<!-- 	<div class="breadcumb"></div> -->
	<div class="content">
		<jsp:include page="/pages/${param.page}.jsp" />
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
