package il.ac.hit.todolistframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import il.ac.hit.todolistframework.helpers.ConsoleLogger;
import il.ac.hit.todolistframework.model.HibernateToDoListDAO;
import il.ac.hit.todolistframework.model.ToDoListPlatformException;
import il.ac.hit.todolistframework.model.User;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(
		description = "Front Controller Pattern Implementation - Single handler for all kinds of requests coming to the application", 
		urlPatterns = {"/pages","/pages/*"})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ConsoleLogger log = new ConsoleLogger();
	//Controllers:
	private static final ListController listController = new ListController(log);
    private static final LoginController loginController = new LoginController(log);
    
    private NavGuide analyzePath(String url)
    {
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

    private boolean isLoggedInUser(HttpServletRequest request){
    	log.info("");
    	return request.getSession().getAttribute("userData")!=null;
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		switch(analyzePath(request.getRequestURI()))
		{
		case LOGIN_LOGIN:
			log.info("login");
			if(!isLoggedInUser(request)){
				if(!loginController.login(getServletContext(),request,response)){
					loginController.show(getServletContext(),request,response);
					break;
				}
			}
			log.info("user is logged in");
			//logged in already or just succeed to log in go to list
			listController.show(getServletContext(),request,response);
			break;
		case LOGIN_SHOW:
			log.info("welcome page");
		case TODOLIST_SHOW:
			log.info("show my todo list");
			//listController.show(getServletContext(),request,response);
			if(isLoggedInUser(request))
				listController.show(getServletContext(),request,response);
			else
				loginController.show(getServletContext(),request,response);
			break;
		case LOGIN_LOGOUT:
			log.info("logout");
			loginController.logout(request,response);
			loginController.show(getServletContext(),request,response);
			break;
		case PAGE_NOT_EXIST:
			log.info("page not exist");
			getServletContext().getRequestDispatcher("/Error.jsp").forward(request, response);
			break;
		case TODOLIST_ADD:
			log.info("add");
			listController.add(request,response);
			break;
		case TODOLIST_EDIT:
			log.info("edit");
			listController.edit(request,response);
			break;
		case TODOLIST_REMOVE:
			log.info("remove");
			listController.remove(request,response);
			break;
		case TODOLIST_REMOVE_GROUP:
			log.info("remove group");
			listController.removeGroup(getServletContext(), request,response);
			break;
		default:
			log.warning("nothing");
			break;
		}
		
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
