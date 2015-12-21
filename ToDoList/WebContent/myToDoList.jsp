<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="il.ac.hit.todolistframework.model.*,java.util.List"%>
<%
//<jsp:useBean id="rex" scope="session" class="com.abelski.Rectangle"/>
//<jsp:setProperty name="rex" property="*"/>
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Insert title here</title>
</head>
<body>

<%
	User userData = (User)request.getSession().getAttribute("userData");
	if(userData!=null)
	{
%>
<h3>Hello <%= userData.getName() %></h3>
&nbsp;
<form action="login/logout" method="GET">
<input type="submit" name="logout" value="logout">
</form>

<div align="center">
<table>
<tr>
<th></th>
<th>To-Do Item</th>
<th>Actions</th>
</tr>
<%
	for(Item toDoItem:userData.getItems())
	{
		out.print("<tr>");
		out.print("<td><input type=\"checkbox\" name=\"myToDoItem\" value=\""+toDoItem.getIdItem()+"\" /></td>");
		out.print("<td>"+toDoItem.getWhatToDo()+"</td>");
		out.print("<td><input type=\"submit\" name=\"complete\" value=\"complete\"></td>");
		out.print("</tr>");
	}
%>
</table>
<%
	}
	else{
		out.print("<h1>You do not have any To-Do items</h1>");
	}
	out.print("<input type=\"button\" name=\"create\" value=\"create\">");
%>
</div>

</body>
</html>