<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>details</title>
<style type="text/css">
.feedback {
	font-size: 32px;
	font-family: "Arial Black";
	color: red;
}
</style>
</head>
<body>
	<div>
		<form action="./Service" method="post">
			<fieldset>
				<fieldset>
					<label>Student id <span>*</span></label> <input type="text"
						name="id" value="${requestScope.student.id}" />
				</fieldset>
				<fieldset>
					<label>Student name <span>*</span></label> <input type="text"
						name="name" value="${requestScope.student.name}" />
				</fieldset>
				<fieldset>
					<label>Student birthday <span>*</span></label> <input type="text"
						name="birthday" value="${requestScope.student.birthday}" />
				</fieldset>
				<fieldset>
					<label>Student description <span>*</span></label> <input
						type="text" name="description" value="${requestScope.student.description}" />
				</fieldset>
				<fieldset>
					<label>Student avgscore <span>*</span></label> <input type="text"
						name="avgscore" value="${requestScope.student.avgscore}" />
				</fieldset>
				<c:if
					test="${null==requestScope.student|| empty requestScope.student}">
					<input type="reset" value="reset" />
					<input type="submit" value="Add Now!" />
					<input type="hidden" name="business" value="addNewStudent" />
				</c:if>
				<c:if test="${not empty requestScope.student}">
					<input type="submit" value="Update Now!" />
					<input type="hidden" name="business" value="updateStudent" />
				</c:if>
			</fieldset>
		</form>
	</div>
	<div>
		You must pay attention to the following, otherwise it will cause the failure of storage<br/>
		Student id: make sure not to repeat<br/>
		Student birthday: must be in conformity with the date format<br/>
		Student avgscore: must be a number<br/>
	</div>
	<c:if test="${not empty requestScope.feedback}">
		<div class="feedback">
			Storage failure, please check your input
		</div>
	</c:if>
</body>