<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<style>
body {
	padding-top: 70px; 
}

.form-background {
	background-color: #d1ecf1; /* Màu nền xanh */
	padding: 20px;
	border-radius: 10px;
	margin-top: 50px;
}

h1 {
	text-align: center;
	margin-bottom: 30px;
}

.form-floating {
	margin-bottom: 1rem;
}

.form-floating label {
	color: #495057;
}
</style>

<div class="container-fluid">
	<div class="form-background">
		<h1 class="text-center">Add User</h1>
		<div class="row justify-content-center">
			<div class="col-md-6">

				<c:if test="${errMsg != null}">
					<div class="alert alert-danger">${errMsg}</div>
				</c:if>

				<c:url value="/admin/add/user" var="actionAddUser" />

				<form:form method="post" action="${actionAddUser}"
					modelAttribute="user">
					<form:errors path="*" element="div" cssClass="alert alert-danger" />
					
					<form:input path="id" type="hidden" value="${user.id}"/>

					<div class="row g-3">
						<div class="col">
							<div class="form-floating">
								<form:input path="useruniversityid" type="text"
									class="form-control" id="useruniversityidId"
									placeholder="User university id" />
								<label for="useruniversityidId">User university id <span
									class="text-danger">*</span></label>
							</div>
						</div>

					</div>

					<div class="row g-3">
						<div class="col">
							<div class="form-floating">
								<form:input path="firstName" type="text" class="form-control"
									id="firstNameUser" placeholder="First Name" required="true" />
								<label for="firstNameUser">First Name <span
									class="text-danger">*</span></label>
							</div>
						</div>
						<div class="col">
							<div class="form-floating">
								<form:input path="lastName" type="text" class="form-control"
									id="lastNameUser" placeholder="Last Name" required="true" />
								<label for="lastNameUser">Last Name <span
									class="text-danger">*</span>
								</label>
							</div>
						</div>
					</div>
					<div class="row g-3">
						<div class="col">
							<div class="form-floating">
								<form:input path="email" type="email" class="form-control"
									id="email" placeholder="Email" name="email" required="true" />
								<label for="email">Email <span class="text-danger">*</span>
								</label>

							</div>

						</div>
						<div class="col">
							<div class="form-floating">
								<form:input path="phone" type="text" class="form-control"
									id="phone" placeholder="Phone" required="true" />
								<label for="phone">Phone <span class="text-danger">*</span>
								</label>
							</div>
						</div>
					</div>
					<div class="row g-3">
						<div class="col">
							<div class="form-floating">
								<form:input type="date" path="birthday" class="form-control"
									id="birthday" placeholder="Birthday" name="birthday" />
								<label for="birthday">Birthday <span class="text-danger">*</span>
								</label>
							</div>
						</div>
						<div class="col">
							<div class="form-floating">
								<form:select path="gender" class="form-select" id="gender">
									<option value="male" selected>Male</option>
									<option value="female">Female</option>
								</form:select>
								<label for="gender">Gender <span class="text-danger">*</span></label>
							</div>
						</div>
					</div>

					<div class="row g-3">
						<div class="col">
							<div class="form-floating">
								<form:select class="form-select" name="roleId" id="roleId"
									path="roleId">
									<c:forEach items="${roles}" var="r">
										<c:choose>
											<c:when test="${r.id == user.roleId.id}">
												<option value="${r.id}" selected>${r.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${r.id}">${r.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
								<label for="roleId">ROLE ID <span class="text-danger">*</span></label>
							</div>
						</div>
						<div class="col">
							<div class="form-floating">
								<form:select class="form-select" name="facultyId" id="facultyId"
									path="facultyId">
									<c:forEach items="${faculties}" var="fa">
										<c:choose>
											<c:when test="${fa.id == user.facultyId.id}">
												<option value="${fa.id}" selected>${fa.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${fa.id}">${fa.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
								<label for="facultyId">FACULTY ID <span
									class="text-danger">*</span></label>
							</div>
						</div>
					</div>
					<div class="d-grid mb-3">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>