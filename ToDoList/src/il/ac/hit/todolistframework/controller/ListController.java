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
 * ListController class is a controller that groups the functions related to managing and displaying the list of to-do items for a user.
 * instantiated in the FrontController servlet. 
 */
public class ListController {
	private static ConsoleLogger log;
	
	public ListController(){
		this.log = new ConsoleLogger();
	}
	/**
	 * constructor for the ListController class
	 * @param log
	 */
	public ListController(ConsoleLogger log) {
		this.log = log;
	}
	
	/**
	 * (generally GET Request)
	 * add an item to the to-do list items of a user
	 * @param request - the request object
	 * @param response - the response object
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * first extracts the user data (list and identity details) from the session attribute 'userData'
		 * 2nd, extracts the whatToDo data, create new item with this data.
		 * then adds the item to the database
		 * lastly updates the users' session.
		 */
		User user = (User)request.getSession().getAttribute("userData");
		
		String whatToDo = request.getParameter("whatToDo");
		Item item = new Item();
		if(!item.setWhatToDo(whatToDo))
		{
			log.warning("item content is null or empty");
			request.setAttribute("errorMessage","item content is null or empty");
		}
		else
		{
			item.setUser(user);
			try {
				HibernateToDoListDAO.getInstance().addItem(item);
				log.info("Succesfully added an item");
				log.info("Update session data (items list for user): user=" + user.getName());
				//update session data:
				request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
			}
			catch (ToDoListPlatformException e)
			{
				log.error("Failed to add an item: " + e.getMessage());
				request.setAttribute("errorMessage","Failed to add an item");
			}
		}
	}

	/**
	 * (generally GET Request)
	 * save a change in a given to do item. if the item does not exist it is added to the list.
	 * @param request - the request object
	 * @param response - the response object
	 */
	public void edit(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * first extracts the user data (list and identity details) from the session attribute 'userData'
		 * get the id and the what-to-do data
		 * if the id is not valid ignore it and go use add() function to add this new whatToDo data to the list as new item.
		 * if the id and the whatToDo are valid save or update the item.
		 * lastly updates the user session attributes.
		 */
		User user = (User)request.getSession().getAttribute("userData");
		
		String whatToDo = request.getParameter("whatToDo");
		String idStr = request.getParameter("itemId");
		Item item = new Item();
		int id = -1;
		
		try{
			id = Integer.parseInt(idStr);
		}
		catch(NumberFormatException e){
			log.debug("item id is not a valid integer, calling add() function to add as a new item");
			add(request, response);
			return;
		}
		
		//id is a valid int
		if(id > -1){
			item.setIdItem(id);
		}
		else
		{
			log.debug("item id(="+id+") <= -1 , calling add() function to add as a new item");
			add(request, response);
			return;
		}
		
		//id is a valid int and >= 0
		if(!item.setWhatToDo(whatToDo))
		{
			log.warning("item content is null or empty");
			request.setAttribute("errorMessage","item content is null or empty");
		}
		else
		{
			item.setUser(user);
			try {
				HibernateToDoListDAO.getInstance().updateItem(item);
				log.info("Succesfully updated an item");
				log.info("Update session data (items list for user): user=" + user.getName());
				//update session data:
				request.getSession().setAttribute("userData",HibernateToDoListDAO.getInstance().getUser(user.getName()));
			}
			catch (ToDoListPlatformException e)
			{
				log.error("Failed to update an item: " + e.getMessage());
				request.setAttribute("errorMessage","Failed to update an item");
			}
		}
	}
	
	/**
	 * (generally GET Request)
	 * remove an item from the to-do list items of a user
	 * @param request - the request object
	 * @param response - the response object
	 */
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * first extracts the user data (list and identity details) from the session attribute 'userData'
		 * then looking for an item with the given id and deletes it.
		 * lastly updates the user session attributes.
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
			else{
				log.error("Unable to Complete (Delete) item: "+item);
				request.setAttribute("errorMessage","Unable to Complete (Delete) item");
			}
		}
		catch(NumberFormatException | ToDoListPlatformException e)
		{
			log.error("Unable to Complete (Delete) item: " + e.getMessage());
			request.setAttribute("errorMessage","Unable to Complete (Delete) item");
		}
	}
	
	/**
	 * (generally POST Request)
	 * removes a list of items that were requested to be deleted.
	 * @param context - require the servlet context
	 * @param request - the request object
	 * @param response - the response object
	 */
	public void removeGroup(ServletContext context, HttpServletRequest request, HttpServletResponse response)
	{
		/*
		 * get a list of IDs of all the items that requested to be deleted
		 * if the list is not null, for each item in the list find the item in the user items list in the session
		 * and delete it from the database.
		 * lastly update the user data in the session.
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
	 * (generally GET Request)
	 * dispatching the list web page
	 * @param context - require the servlet context
	 * @param request - the request object
	 * @param response - the response object
	 */
	public void show(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * simply dispatching the List.jsp page
		 */
		try {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/List.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			log.error(e.getMessage());
		}		
	}
	
}
