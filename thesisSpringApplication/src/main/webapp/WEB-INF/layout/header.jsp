<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
	<div class="container-fluid">
		<c:url value="/" var="homePage" />

		<a class="navbar-brand" href="${homePage}">Home Page</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#mynavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="mynavbar">
			<ul class="navbar-nav me-auto">
				
			</ul>


			<form class="d-flex">
				<input class="form-control me-2" type="text" placeholder="Search">
				<button class="btn btn-primary" type="button">Search</button>
			</form>
		</div>
	</div>
</nav>