<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<style type="text/css">
    <%@include file="bootstrap.min.css" %>
    <!-- Used the free bootstrap: "http://bootswatch.com/slate/" -->
</style>
<title>To-Do-List Login Page</title>
</head>
<body>

<div align="center">
	<h1>Welcome to To-Do-List</h1>
</div>
<div class="container bs-component">
	<!-- 
	Login form:
	-->
	<form class="form-horizontal" action="/ToDoList/login" method="POST">
		<fieldset>
			<legend>Login</legend>
			<div class="form-group">
				<label class="col-lg-2 control-label">UserName: </label>
				<div class="col-lg-10">
					<input class="form-control" type="text" name="userName" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-lg-2 control-label">Password: </label>
				<div class="col-lg-10">
					<input class="form-control" type="password" name="password" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">
					<button class="btn btn-default" type="submit" name="login">Login</button>
				    <button class="btn btn-primary" type="submit" name="createNewAccount">Create New Account</button>
				    <div class="form-group has-error">
                  		<label class="control-label">
                  			<%
                  			String errorMessage = (String)request.getAttribute("errorMessage");
                  			if(errorMessage!=null)
                  				out.print(errorMessage);
                  			%>
                  		</label>
                	</div>
				</div>
			</div>
		</fieldset>
	</form>
	<!-- 
	End of login form:
	-->
</div>

</body>
</html>