package il.ac.hit.todolistframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.helpers.ConsoleLogger;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(
		description = "Front Controller Pattern Implementation - Single handler for all kinds of requests coming to the application", 
		urlPatterns = {"/pages","/pages/*"})
public class FrontController extends HttpServlet {
	/*
	 * This controller with the filter class is following the Front controller design pattern
	 */
	private static final long serialVersionUID = 1L;
	//Logger(print to console):
	private static final ConsoleLogger log = new ConsoleLogger();
	//Controllers:
	private static final ListController listController = new ListController(log);
    private static final LoginController loginController = new LoginController(log);
    
    /**
     * analyzing the given url with regex returning a flag of what was requested
     * @param url
     * @return a flag that tells what was requested.
     */
    private NavGuide analyzePath(String url)
    {
    	/*
    	 * could use startswith() function but. regex is more strict.
    	 */
    	String rUrl = url;
    	log.debug(url);
    	if(!rUrl.matches("^\\/ToDoList/pages.*"))
    		return NavGuide.PAGE_NOT_EXIST;
    	
    	rUrl = rUrl.substring("/ToDoList/pages".length());
    	if(rUrl.matches("^(\\/|)$"))
    		return NavGuide.LOGIN_SHOW; //GET
    	
    	if(rUrl.matches("^\\/login(/|)$"))
    		return NavGuide.LOGIN_LOGIN; //POST login and create new account
    		
    	if(rUrl.matches("^\\/logout(/|)$"))
    		return NavGuide.LOGIN_LOGOUT; //POST logout
    	
    	if(rUrl.matches("^\\/list(|/.*)$"))
    	{
    		rUrl = rUrl.substring("/list".length());
    		if(rUrl.matches("^(\\/|)$"))
    			return NavGuide.TODOLIST_SHOW; //GET
    		if(rUrl.matches("^\\/add"))
    			return NavGuide.TODOLIST_ADD; //GET ?data= 
    		if(rUrl.matches("^\\/edit"))
    			return NavGuide.TODOLIST_EDIT; //GET ?id= &data=
    		if(rUrl.matches("^\\/remove"))
    			return NavGuide.TODOLIST_REMOVE; //GET ?id=
    		if(rUrl.matches("^\\/removeGroup(/|)$"))
    			return NavGuide.TODOLIST_REMOVE_GROUP; //POST
    	}
    	return NavGuide.PAGE_NOT_EXIST;
    }

    /**
     * checked weather a request was sent by a logged in user.
     * @param request - the request object
     * @return returns true of the 'userData' session attribute is not null, false otherwise.
     */
    private boolean isLoggedInUser(HttpServletRequest request){
    	/*
    	 * checks weather the session attribute 'userData' is not null.
    	 */
    	log.info("");
    	return request.getSession().getAttribute("userData")!=null;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * this function was designed to be cleared and strict as possible.
		 * first, analyze the url to determine the next step / next action.
		 * if the page requested does not exist (actually, if the url path string was illegal) go to error page.
		 * afterwards check if the user is logged in. if not, the general direction is to the login page.
		 * if the user is logged in, the general direction is to his TO-DO list and list actions. 
		 */
		NavGuide nav = analyzePath(request.getRequestURI());
		//page does not exist
		if(nav == NavGuide.PAGE_NOT_EXIST)
		{
			log.info("page not exist");
			//request.setAttribute("errorMessage", "Sorry, the page you requested does not exist.");
			throw new ServletException("The page you requested does not exist.");
			//getServletContext().getRequestDispatcher("/Error.jsp").forward(request, response);
			//return;
		}
		//user is not logged in
		if(!isLoggedInUser(request))
		{
			if(nav == NavGuide.LOGIN_LOGIN && loginController.login(getServletContext(),request,response)){
					//login succeed
				listController.show(getServletContext(),request,response);
			}else 	//login failed
				loginController.show(getServletContext(),request,response);
			return;
		}
		//user is logged in
		if(nav == NavGuide.LOGIN_LOGOUT)
		{
			loginController.logout(request,response);
			loginController.show(getServletContext(),request,response);
			return;
		}
		
		switch(nav)
		{
		case TODOLIST_ADD:
			listController.add(request,response);
			break;
		case TODOLIST_EDIT:
			listController.edit(request,response);
			break;
		case TODOLIST_REMOVE:
			listController.remove(request,response);
			break;
		case TODOLIST_REMOVE_GROUP:
			listController.removeGroup(getServletContext(), request,response);
			break;
		case LOGIN_LOGIN: 	//was taken care of before
		case LOGIN_LOGOUT: 	//was taken care of before
		case PAGE_NOT_EXIST://was taken care of before
		case LOGIN_SHOW: 	//logged in user must log out first
		case TODOLIST_SHOW: //directed after the switch statement
		default:
			break;		
		}
		
		listController.show(getServletContext(),request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * all requests (GET and POST) are handled in the doGet() function 
		 */
		doGet(request, response);
	}

}
