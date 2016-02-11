package il.ac.hit.todolistframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.helpers.ConsoleLogger;
import il.ac.hit.todolistframework.model.HibernateToDoListDAO;
import il.ac.hit.todolistframework.model.IToDoListDAO;
import il.ac.hit.todolistframework.model.ToDoListPlatformException;
import il.ac.hit.todolistframework.model.User;

/**
 * LoginController class is a controller that is actually a group of functions that
 * each of them is participating in the login and logout process (and creation of a new user also).
 * This class is instantiated in the FrontController servlet.
 */
public class LoginController {
	private static ConsoleLogger log;
	
	public LoginController(){
		this.log = new ConsoleLogger();
	}
	/**
	 * constructor for the LoginController class
	 * @param log
	 */
	public LoginController(ConsoleLogger log){
		LoginController.log = log;
	}
	/**
	 * (generally POST Request)
	 * performs the login process
	 * @param context - require the servlet context
	 * @param request - the request object
	 * @param response - the response object
	 * @return returns true if the user was logged in
	 */
	public boolean login(ServletContext context,HttpServletRequest request, HttpServletResponse response) {
		/*
		 * this function checks several things before it decides to set the user as logged in user
		 * -the user credentials must be valid
		 * -the user is in the database
		 * -the password that was supplied match the users' password in the database
		 * then, and only then the user is logged in - by loading the users' data to the session variable 'userData'
		 * 
		 * this function also creates new users, depending on the request value that was sent:
		 * login - login as described before
		 * createNewAccount - creates new account with the credentials that were supplied
		 * 
		 * after a successful creation of a new account the new user is also logged in.
		 */
		
		User user = new User();
		//check input
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
		//login user
		if(request.getParameter("login") != null)
		{
			log.info("Requested = 'Login'");
			
			try{
				log.debug("before instance");
				IToDoListDAO model = HibernateToDoListDAO.getInstance();
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
		//new user creation
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
	/**
	 * (generally GET Request)
	 * logs out a user
	 * @param request - request object from the servlet
	 * @param response - response object from the servlet
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * removes the 'userData' attribute from the session.
		 */
		request.getSession().removeAttribute("userData");
	}
	/**
	 * (generally GET Request)
	 * dispatching the login web page
	 * @param context - context object from the servlet
	 * @param request - request object from the servlet
	 * @param response - response object from the servlet
	 */
	public void show(ServletContext context,HttpServletRequest request, HttpServletResponse response) {
		/*
		 * simply dispatching the login.jsp page
		 */
		try {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			log.error(e.getMessage());
		}
	}
}
