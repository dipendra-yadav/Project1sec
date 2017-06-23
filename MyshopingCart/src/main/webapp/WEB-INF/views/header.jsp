<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- DC Page Header Start -->
	<div class="dc_gradient2">
		<h1>
			<span></span>
		</h1>
		<h2>
			<fmt:message key="title" />
		</h2>
	</div>
	<!-- DC Page Header End -->

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<ul class="nav navbar-nav ">
				<li><a href="<c:url value="/home"/>">Home</a></li>
				<li><a href="<c:url value="/aboutUs"/>">About us</a></li>
				<li><a href="<c:url value="/getAllProducts"/>">
						Products</a></li>



				<li><a href="<c:url value="/getAllCategories"/>">
						Categories</a></li>
				<li><a href="<c:url value="/getAllSuppliers"/>">
						Suppliers</a></li>
				<li><a href="<c:url value="/login"/>">login</a></li>
				<c:url value="/registration" var="url"></c:url>
				<li><a href="${url }">Register</a></li>

			</ul>
		</div>

	</nav>
</body>
</html>

