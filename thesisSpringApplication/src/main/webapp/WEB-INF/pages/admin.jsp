<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
body {
	padding-top: 70px;
}
</style>

<div class="container">
	<div>
	    <c:url value="/admin" var="pageUrl" />
		<c:url value="/admin/addUser" var="addUserUrl" />
		<c:url value="/admin/updateUser" var="updateUserUrl" />
		
		<a href="${addUserUrl}" class="btn btn-primary mt-1 mb-1">Add new
			user</a>
	</div>
	<table class="table table-striped">
		<thead>
			<tr>
				<th></th>
				<th>Avatar</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Role</th>
				<th>Faculty</th>
				<th>Actions</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="u">
				<tr>
					<td></td>
					<td><img class="card-img-top" src="${u.avatar}"
						style="width: 300px" alt="Card image"></td>
					<td>${u.firstName}</td>
					<td>${u.lastName}</td>
					<td>${formatterColumn.roleParentIdFormatter(u.roleId.id)}</td>
					<td></td>


					<td><a href="<c:url value="/admin/updateUser/${u.id}" />"
						class="btn btn-primary mt-1 mb-1">Update</a> <a
						onclick="confirmDeleteProduct()"
						href="<c:url value="/admin/deleteUser/${u.id}" />"
						class="btn btn-danger mt-1 mb-1">Delete</a></td>
				</tr>
			</c:forEach>


		</tbody>
	</table>
	<ul class="pagination">
		<li class="page-item"><a class="page-link" href="${pageUrl}?page=${page-1}">Previous</a></li>
		<c:forEach begin="1" end="${totalPage}" var="t">
			<li class="page-item"><a class="page-link" href="${pageUrl}?page=${t}">${t}</a></li>		
		</c:forEach>
		<li class="page-item"><a class="page-link" href="${pageUrl}?page=${page+1}">Next</a></li>
	</ul>
</div>

<script>
	function confirmDeleteProduct() {
		if (confirm("Bạn chắc chắn xóa không?") !== true) {
			event.preventDefault();
		} else {
		}
	}
</script>