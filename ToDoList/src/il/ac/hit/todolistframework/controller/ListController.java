package il.ac.hit.todolistframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.helpers.ConsoleLogger;
import il.ac.hit.todolistframework.model.*;

/**
 * 
 * @author digic
 *
 */
public class ListController {
	private static ConsoleLogger log;
	/**
	 * 
	 * @param log
	 */
	public ListController(ConsoleLogger log) {
		this.log = log;
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 
		 */
		//log.info("Add an item");
		User user = (User)request.getSession().getAttribute("userData");
		
		String whatToDo = request.getParameter("whatToDo");
		Item item = new Item();
		if(!item.setWhatToDo(whatToDo))
		{
			log.warning("item content is null or empty");
			//send message to web page?
		}
		else
		{
			item.setUser(user);
			try {
				HibernateToDoListDAO.getInstance().addItem(item);
				log.info("Succesfully added an item");
				log.info("Update session data (items list for user): user=" + user.getName()); //update session data:
				request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
			}
			catch (ToDoListPlatformException e)
			{
				log.error("Failed to add an item: " + e.getMessage());
				//send message to web page?
			}
		}
	}

	public void edit(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 
		 */
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 
		 */
		User user = (User)request.getSession().getAttribute("userData");
		
		log.info("Trying to Complete (Delete) an item: id="+request.getParameter("itemId"));
		try{
			Integer itemId = Integer.parseInt(request.getParameter("itemId"));
			Item item = user.getItems().stream().filter(x->x.getIdItem() == itemId).findFirst().get();
			if(HibernateToDoListDAO.getInstance().deleteItem(item))
			{
				log.info("Succesfully completed (deleted) item");
				log.info("Update session data (items list for user): user=" + user.getName()); //update session data:
				request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
			}
			else
				log.error("Unable to Complete (Delete) item: "+item);
		}
		catch(NumberFormatException | ToDoListPlatformException e)
		{
			log.error("Unable to Complete (Delete) item: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param context
	 * @param request
	 * @param response
	 */
	public void removeGroup(ServletContext context, HttpServletRequest request, HttpServletResponse response)
	{
		/*
		 * 
		 */
		String[] itemsStr = request.getParameterValues("MyToDoItemCB");
		if(itemsStr!=null)
		{
			User user = (User)request.getSession().getAttribute("userData");
			log.info("Deleting a group of items: " + itemsStr);
			for(String itemStr:itemsStr)
			{
				try{
					Integer itemId = Integer.parseInt(itemStr);
					Item item = user.getItems().stream().filter(x->x.getIdItem() == itemId).findFirst().get();
					if(HibernateToDoListDAO.getInstance().deleteItem(item))
						log.info("Successfully deleted item: id=" + item.getIdItem());
					else
						log.error("Unable to delete item: id=" + item.getIdItem());	
				} catch(NumberFormatException | ToDoListPlatformException e) {
					log.error(e.getMessage() + "; Unable to delete item: "+e.getMessage());
				}
			}
			
			//update session data:
			try {
				request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
				log.info("Updated session user data: user= " + user.getName());
			} catch (ToDoListPlatformException e) {
				log.error(e.getMessage() + "; Unable to update session user data: user= " + user.getName());
			}
		}
	}
	/**
	 * 
	 * @param context
	 * @param request
	 * @param response
	 */
	public void show(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 
		 */
		try {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/List.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			log.error(e.getMessage());
		}		
	}
	
}
