<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255" import="il.ac.hit.todolistframework.model.*,java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<style type="text/css">
    <%@include file="bootstrap.min.css"%>
    <!-- Used the free bootstrap: "http://bootswatch.com/slate/" -->
</style>
<title>To-Do-List > my Items</title>
</head>
<body>

<%
	User userData = (User)request.getSession().getAttribute("userData");
	if(userData!=null)
	{
%>
<!-- 
Navigation bar:
-->
<nav class="navbar navbar-default">
	<div class="container-fluid">
	    <div class="navbar-header">
	    	<a class="navbar-brand">To-Do-List</a>
	    </div>
		<ul class="nav navbar-nav navbar-right">
	    	<li><a class="navbar-brand btn disabled"> Hello <%= userData.getName() %></a></li>
	    	<li><a href="logout">Logout</a></li>
	    </ul>
	</div>
</nav>
<!--
End of navigation bar.
-->

<div class="container bs-component">
	<!-- 
	Add new item:
	 -->
	<form class="form-horizontal" action="MyToDoItems/Add" method="GET">
		<div class="form-group">
			&nbsp;
		  	<div class="input-group">
			    <span class="input-group-addon">>_</span>
			    <input class="form-control" type="text" name="whatToDo" placeholder="Remember to...">
			    <span class="input-group-btn">
			    	<input class="btn btn-info" type="submit" value="Add Item" />
			    </span>
	  		</div>
	  		&nbsp;
	  	</div>
	</form>
	<!-- 
	End of add new item.
	 -->
	<!-- 
	Table of items:
	 -->
	<form action="MyToDoItems" method="POST">
		<table class="table table-striped table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" name="allItems" value="all" onclick="toggle(this)" /></th>
					<th>To-Do Item</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody id="checkboxList">
			<%
				for(Item toDoItem:userData.getItems())
				{
					out.print("<tr>");
					out.print("<td><input type=\"checkbox\" name=\"MyToDoItemCB\" value=\""+toDoItem.getIdItem()+"\" onclick=\"miniToggle(this)\" /></td>");
					out.print("<td>"+toDoItem.getWhatToDo()+"</td>");
					
					out.print("<td>");
					out.print("<a class=\"btn btn-default btn-sm btn-success\" href=\"MyToDoItems/Complete?itemId="+toDoItem.getIdItem()+"\">Complete</a>");
					out.print("&nbsp;");
					out.print("<a class=\"btn btn-default btn-sm btn-warning\" href=\"MyToDoItems/Complete?itemId="+toDoItem.getIdItem()+"\">Edit</a>");
					out.print("</td>");
					out.print("</tr>");
				}
			%>
			</tbody>
			<tfoot>
				<tr>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		<input class="btn btn-primary" type="submit" value="Complete Selection" />
	</form>
	<!--
	End of table of items.
	 -->
	<%
		}
		else{ //if the user is not logged in:
			%>
			<!-- 
			Navigation bar:
			 -->
			<nav class="navbar navbar-default">
				<div class="container-fluid">
				    <div class="navbar-header">
				    	<a class="navbar-brand">To-Do-List</a>
				    </div>
					<ul class="nav navbar-nav navbar-right">
				    	<li><a class="navbar-brand btn disabled">Hello Guest!</a></li>
				    	<li><a href="login">Login</a></li>
				    </ul>
				</div>
			</nav>
			<!--
			End of navigation bar.
			 -->
			 
			<div class="container bs-component">
				<h1>You are not logged in!</h1>
			</div>
			<%
		}
	%>
</div>
<script type="text/javascript">
function toggle(source) { // script to check/uncheck all checkboxes - very basic implementation.
	var checkboxes = document.getElementsByName('MyToDoItemCB');
	for(var i=0; i < checkboxes.length; i++) {
		checkboxes[i].checked = source.checked;
	}
}

function miniToggle(source) {
	var masterCheckbox = document.getElementsByName('allItems');
	if(!source.checked)
		masterCheckbox[0].checked = false;
	else
		allToggled(); //check all
}

function allToggled() {
	var masterCheckbox = document.getElementsByName('allItems');
	var checkboxes = document.getElementsByName('MyToDoItemCB');
	var finalToggleStatus = true;
  	for(var i=0; i < checkboxes.length; i++) {
  		finalToggleStatus = finalToggleStatus & checkboxes[i].checked;
	}
  	masterCheckbox[0].checked = finalToggleStatus;
}
</script>

</body>
</html>