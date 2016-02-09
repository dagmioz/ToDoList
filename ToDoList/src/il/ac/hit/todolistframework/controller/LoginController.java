package il.ac.hit.todolistframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.helpers.ConsoleLogger;
import il.ac.hit.todolistframework.model.HibernateToDoListDAO;
import il.ac.hit.todolistframework.model.ToDoListPlatformException;
import il.ac.hit.todolistframework.model.User;

public class LoginController {
	private static ConsoleLogger log;
	
	public LoginController(ConsoleLogger log){
		LoginController.log = log;
	}
	
	public boolean login(ServletContext context,HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		if(!user.setName(request.getParameter("userName")) || !user.setPassword(request.getParameter("password")))
		{
			log.error("user and/or password are empty or null");
			if(request.getParameter("login") != null)
				request.setAttribute("errorMessage", "Login failed: user and password cannot be empty or null");
			else if (request.getParameter("createNewAccount") != null)
				request.setAttribute("errorMessage", "Create-New-User failed: user and password cannot be empty or null");
			return false;
		}
		//input is valid
		log.debug("user details recieved: " + user);
			
		if(request.getParameter("login") != null)
		{
			log.info("Requested = 'Login'");
			
			try{
				log.debug("before instance");
				HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
				log.debug("after retrieved instance");
				User authorizedUser = model.login(user);
				if (authorizedUser!=null){
					request.getSession().setAttribute("userData",authorizedUser);
					log.info("Login was successful:" + authorizedUser.getName());
					return true;
				}
				else
					request.setAttribute("errorMessage", "Login failed: username or password are incorrect");
			}
			catch(ToDoListPlatformException ex){
				request.setAttribute("errorMessage", ex.getMessage());
			} 
		} 
		else if (request.getParameter("createNewAccount") != null)
		{
			log.info("Request = 'Create New Account'");
			
			try{
				HibernateToDoListDAO.getInstance().addUser(user);
				User authorizedUser = HibernateToDoListDAO.getInstance().getUser(user.getName());
				if (authorizedUser!=null){
					request.getSession().setAttribute("userData",authorizedUser);
					log.info("Login was successful. username: " + authorizedUser.getName());
					return true;
				}
				else
					log.info("Login failed: Unknown reason");
			}
			catch(ToDoListPlatformException e)
			{
				try{
					if(HibernateToDoListDAO.getInstance().getUser(user.getName())!=null){
						log.info("Login failed: User with the same name already exist");
						request.setAttribute("errorMessage","User with the same name already exist");
					}
				}
				catch(ToDoListPlatformException ex)
				{
					log.info("Login failed: " + ex.getMessage());
					request.setAttribute("errorMessage", "Login failed: DataBase Error");
				}
			}
		}
		return false;
	}
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("userData");
	}
	public void show(ServletContext context,HttpServletRequest request, HttpServletResponse response) {
		try {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			log.error(e.getMessage());
		}
	}
}
