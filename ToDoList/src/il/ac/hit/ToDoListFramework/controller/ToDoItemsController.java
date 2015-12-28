package il.ac.hit.todolistframework.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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

import il.ac.hit.todolistframework.model.*;

/**
 * Servlet implementation class ToDoItemsController
 */
@WebServlet({ "/ToDoItemsController", "/MyToDoItems", "/MyToDoItems/Add", "/MyToDoItems/Complete" })
public class ToDoItemsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
         
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("|-isLoggedIn");
		return request.getSession().getAttribute("userData")!=null;
	}
	
    //private void 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MyToDoItemsController|doGet");
		String url = request.getRequestURI();
		
		System.out.println("Analyzing url:"+url);
		
		if(!isLoggedIn(request,response))
		{
			//response.sendRedirect("/ToDoList/MyToDoItems");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MyToDoList.jsp");
			dispatcher.forward(request, response);
			//can instead redirect to login page:
			//response.sendRedirect("/ToDoList/Login");
			return;
		}
		
		User user = (User)request.getSession().getAttribute("userData");
		
		if(url.endsWith("MyToDoItems/Complete"))
		{
			System.out.println("Trying to Complete (Delete) an item: id="+request.getParameter("itemId"));
			try{
				Integer itemId = Integer.parseInt(request.getParameter("itemId"));
				Item item = user.getItems().stream().filter(x->x.getIdItem() == itemId).findFirst().get();
				if(HibernateToDoListDAO.getInstance().deleteItem(item)){
					System.out.println("Succesfully completed (deleted) item");
					System.out.println("Update session data (items list for user): user=" + user.getName()); //update session data:
					request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
				}
				else
					System.out.println("Unable to Complete (Delete) item: "+item);
			}
			catch(NumberFormatException e)
			{
				System.out.println("Unable to Complete (Delete) item: " + e.getMessage());
			}
			catch(ToDoListPlatformException e)
			{
				System.out.println("Unable to Complete (Delete) item: " + e.getMessage());
			}
			response.sendRedirect("/ToDoList/MyToDoItems");
		}
		else if(url.endsWith("MyToDoItems/Add"))
		{
			System.out.println("Add an item");
			String whatToDo = request.getParameter("whatToDo");
			Item item = new Item();
			if(!item.setWhatToDo(whatToDo))
			{
				System.out.println("item content is null or empty");
				//send message to web page?
			}
			else
			{
				item.setUser(user);
				try {
					HibernateToDoListDAO.getInstance().addItem(item);
					System.out.println("Succesfully added an item");
					System.out.println("Update session data (items list for user): user=" + user.getName()); //update session data:
					request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
				}
				catch (ToDoListPlatformException e)
				{
					System.out.println("Failed to add an item: " + e.getMessage());
					//send message to web page?
				}
			}
			response.sendRedirect("/ToDoList/MyToDoItems");
		}
		else
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MyToDoList.jsp");
			dispatcher.forward(request, response);
		}
	}//End of doGet

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MyToDoItemsController|doPost");

		String[] itemsStr = request.getParameterValues("MyToDoItemCB");
		if(itemsStr!=null)
		{
			if(!isLoggedIn(request,response))
			{
				//response.sendRedirect("/ToDoList/MyToDoItems");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MyToDoList.jsp");
				dispatcher.forward(request, response);
				//can instead redirect to login page:
				//response.sendRedirect("/ToDoList/Login");
				return;
			}
			
			User user = (User)request.getSession().getAttribute("userData");
			
			System.out.println("Deleting a group of items: " + itemsStr);
			
			for(String itemStr:itemsStr)
			{
				try{
					Integer itemId = Integer.parseInt(itemStr);
					Item item = user.getItems().stream().filter(x->x.getIdItem() == itemId).findFirst().get();
					if(!HibernateToDoListDAO.getInstance().deleteItem(item))
						System.out.println("Unable to delete item: id=" + item.getIdItem()+"; unknown reason.");
					else
						System.out.println("Successfully deleted item: id=" + item.getIdItem());
				} catch(NumberFormatException e) {
					System.out.println("Unable to delete item: "+e.getMessage());
				} catch (ToDoListPlatformException e) {
					System.out.println("Unable to delete item: "+e.getMessage());
				}
			}
			//update session data:
			try {
				request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
				System.out.println("Updated session user data: user= " + user.getName());
			} catch (ToDoListPlatformException e) {
				System.out.println("Unable to update session user data: user= " + user.getName() + ";"+ e.getMessage());
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/MyToDoList.jsp");
		dispatcher.forward(request, response);
	}//End of doPost

}
