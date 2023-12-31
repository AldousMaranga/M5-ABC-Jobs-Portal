<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../../header.jsp">
	<jsp:param value="Update Profile" name="HTMLtitle" />
</jsp:include>

<div class="container">
	<form:form modelAttribute="profile" action="update-profile"
		method="post">
		<div class="row border rounded-3 p-3 mb-4">
			<div
				class="col-2 border-none rounded-circle align-self-stretch text-center fs-1 d-flex align-items-center justify-content-center bg-primary bg-gradient text-white">
				<span>${f}</span> <span>${l}</span>
			</div>
			<div class="col-8">
				<div class="form-floating mb-3">
					<input type="text" class="form-control" id="firstName"
						name="firstName" value="${firstName}" required> <label
						for="firstName">First Name</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" class="form-control" id="lastName"
						name="lastName" value="${lastName}" required> <label
						for="lastName">Last Name</label>
				</div>

				<input type="text" class="form-control" value="${title}"
					name="title" placeholder="Your title">
			</div>
			<div class="col-2 align-self-center">
				<button type="submit" class="btn btn-primary">Save Changes</button>
			</div>
		</div>

		<div class="row">
			<div class="col-9">
				<div class="mb-3">
					<h3>About Me</h3>
					<hr>
					<textarea class="form-control" name="about" id="about" cols="10">${about}</textarea>
				</div>
			</div>

			<div class="col-3">
				<div>
					<div class="d-flex align-items-center mb-3">
						<i class='bx bx-building fs-2'></i>
						<div class="form-floating ml-3">
							<input type="text" class="form-control" id="company"
								name="companyName" value="${company}"> <label
								for="company">Company</label>
						</div>
					</div>
					<div class="d-flex align-items-center mb-3">
						<i class='bx bx-envelope fs-2'></i>
						<div class="form-floating ml-3">
							<input type="text" class="form-control" id="email" name="email"
								value="${email}" disabled> <label for="email">Email</label>
						</div>
					</div>
					<div class="d-flex align-items-center mb-3">
						<i class='bx bx-globe fs-2'></i>
						<div class="form-floating ml-3">
							<input type="text" class="form-control" id="website"
								name="website" value="${website}"> <label for="website">Website</label>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</div>

<script src="../js/profile.js">
	
</script>

<jsp:include page="../../footer.jsp"></jsp:include>