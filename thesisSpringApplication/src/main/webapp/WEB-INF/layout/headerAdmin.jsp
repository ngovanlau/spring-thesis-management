<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container-fluid">
		<c:url value="/admin/" var="adminUrl" />
		<c:url value="/admin/stats" var="statsUrl" />
		<c:url value="/logout" var="logoutUrl" />
		

		<a class="navbar-brand" href="${adminUrl}">Admin Dashboard</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item"><a class="nav-link" href="#">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="${adminUrl}">Users</a></li>
				<li class="nav-item"><a class="nav-link" href="${statsUrl}">Stats</a></li>
				

				<li class="nav-item"><a class="nav-link" href="${logoutUrl}">Logout</a></li>
			</ul>
		</div>
	</div>
</nav>