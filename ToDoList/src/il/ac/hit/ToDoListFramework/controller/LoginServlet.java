package il.ac.hit.todolistframework.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.model.HibernateToDoListDAO;
import il.ac.hit.todolistframework.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/LoginServlet", "/login/*" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		if(request.getSession().getAttribute("userData")!=null){
			if(url.endsWith("/logout"))
				request.getSession().removeAttribute("userData");
			else
		         response.sendRedirect("/ToDoList/myToDoItems");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			User user = new User();
		    user.setName(request.getParameter("userName"));
		    user.setPassword(request.getParameter("password"));
			if(request.getAttribute("login") != null)
			{
			    User authorizedUser = HibernateToDoListDAO.getInstance().login(user);
			    if (authorizedUser!=null)
			    {	    
			    	System.out.println(authorizedUser);
			         request.getSession().setAttribute("userData",authorizedUser);
			         //RequestDispatcher dispatcher = null;
			         response.sendRedirect("/ToDoList/myToDoItems");
			         
			         //dispatcher = getServletContext().getRequestDispatcher("/myToDoList.jsp");
			         //dispatcher.forward(request, response);
			         //response.sendRedirect("/myToDoItems"); //logged-in page      		
			    }    
			    else 
			         response.sendRedirect("invalidLogin.jsp"); //error page 
			} 
			else if (request.getAttribute("createNewAccount") != null)
			{
				HibernateToDoListDAO.getInstance().addUser(user);
				User authorizedUser = HibernateToDoListDAO.getInstance().login(user);
			    if (authorizedUser!=null)
			    {
			        request.getSession().setAttribute("userData",authorizedUser);
			        response.sendRedirect("/ToDoList/myToDoItems");
			    }    
			    else 
			         response.sendRedirect("invalidLogin.jsp"); //error page 
			}
		}
		catch (Exception ex) 	    
		{
		    ex.printStackTrace(); 
		}
	}
}
