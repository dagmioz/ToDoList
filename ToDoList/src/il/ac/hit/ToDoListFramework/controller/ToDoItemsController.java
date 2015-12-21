package il.ac.hit.todolistframework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.model.HibernateToDoListDAO;
import il.ac.hit.todolistframework.model.Item;
import il.ac.hit.todolistframework.model.ToDoListPlatformException;
import il.ac.hit.todolistframework.model.User;

/**
 * Servlet implementation class ToDoItemsController
 */
@WebServlet({ "/ToDoItemsController", "/myToDoItems" })
public class ToDoItemsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
    //private void 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("myToDoItems (Get)");
		String url = request.getRequestURI();
		/* /myToDoItems/
		*	-add?whatToDo
		*	-complete/id
		*	-deleteGroup (Post)
		*/
		if(url.endsWith("myToDoItems/add")){
			String whatToDo = request.getParameter("whatToDo");
			if( whatToDo != null){
				System.out.println("myToDoItems (Get) Parameter [add]");
				//request.getSession().getAttribute("whatToDo");
			}
		}
		/*RequestDispatcher dispatcher = null;
		*/
		
		/*
		String sessionId = "";
		String userName="";
		
		Cookie[] cookieArray = request.getCookies();
		for(int i=0; cookieArray!=null && i<cookieArray.length; i++)
		{
			if(cookieArray[i].getName().equals("todoSessionID"))
			{
				Pattern regex = Pattern.compile("^id=(?<sessionId>[a-zA-Z0-9]+);(?<userName>[a-zA-Z0-9]+)$");
				Matcher matcher = regex.matcher(cookieArray[i].getValue());
				
				if(matcher.find())
				{
					sessionId = matcher.group("sessionId");
					userName = matcher.group("userName");
				}
			}	
		}*/
		/*
		 * this is a known user:
		 * -same session
		 * action:
		 * -retrieve items from the model that belong to this  
		 * -redirect to myToDoList.asp
		 */
		//if(request.getSession().getId().equals(sessionId))
		/*  String referer = request.getHeader("Referer");
		  // handle empty referer.....
		  response.sendRedirect(referer);
		if(url.endsWith("/edit")){
			
		}
		else if(url.endsWith("/new"))
		{
			
		}
		else if(request.getAttribute(""))
		*/
		if(url.endsWith("myToDoItems"))
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/myToDoList.jsp");
			dispatcher.forward(request, response);
			/*User user = (User) request.getSession().getAttribute("userData");
			try{
				User user = HibernateToDoListDAO.getInstance().getUser(userName);
				if(user !=null)
				{
					request.setAttribute("myToDoItems", user.getItems());
				}
				else
				{
					
				}
			} catch (ToDoListPlatformException e) {
				e.printStackTrace();
			}
			*/
			
		}
		//unknown user
		/*
		else if(url.endsWith("Login"))
		{
			
			dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}
		else{
			response.getWriter().write(url);
		}*/
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
