<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>To-Do-List Login Page</title>
</head>
<body>
<center>
<h1>To-Do-List Login Page</h1>
<form action="login" method="POST">
UserName: <input name="userName"></input>
<br />
Password: <input type="password" name="password"></input>
<br />
<input type="submit" name="login" value="Login">
&nbsp;
<input type="submit" name="createNewAccount" value="Create New Account">
</form>
</center>
</body>
</html>