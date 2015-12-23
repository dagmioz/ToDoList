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
import il.ac.hit.todolistframework.model.ToDoListPlatformException;
import il.ac.hit.todolistframework.model.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet({ "/LoginController", "/Login/*", "/login/*" })
public class LoginController extends HttpServlet {
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
	
	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void logoutUser(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("|-logoutUser");
		request.getSession().removeAttribute("userData");
	}
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginController|doGet");
		String url = request.getRequestURI();

		if(isLoggedIn(request,response)){
			User loggedInUser = (User)request.getSession().getAttribute("userData"); // get the user
			System.out.println("User is logged in");
			
			System.out.println("Analyzing url:"+url);
			if(url.endsWith("/Logout") | url.endsWith("/logout")) //logout
				logoutUser(request,response);
			else //redirection to myToDoitems
			{
				System.out.println("Redirecting to /ToDoList/myToDoItems");
		        response.sendRedirect("/ToDoList/MyToDoItems"); // change url
			}
		}
		System.out.println("user is NOT logged in");
		System.out.println("dispatching Login.jsp page");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LoginController|doPost");
		
		
		User user = new User();
		if(!user.setName(request.getParameter("userName")) || !user.setPassword(request.getParameter("password")))
		{
			//input is INvalid
			System.out.println("Login/Create-New-User failed: user and password cannot be empty or null");
			request.setAttribute("errorMessage", "Login/Create-New-User failed: user and password cannot be empty or null");
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request, response);
		}
		else{
			//input is valid
			System.out.println("recieved data: " + user);
			
			if(request.getParameter("login") != null)
			{
				System.out.println("Request: Login");
				
				try{
					User authorizedUser = HibernateToDoListDAO.getInstance().login(user);
					if (authorizedUser!=null){
						request.getSession().setAttribute("userData",authorizedUser);
						System.out.println("Login was successful:" + authorizedUser.getName());
				        
						response.sendRedirect("/ToDoList/MyToDoItems");
						return;
					}
					else
					{
						request.setAttribute("errorMessage", "Login failed: username or password are incorrect");
						
						//return to login page:
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
						dispatcher.forward(request, response);
					}
				}
				catch(ToDoListPlatformException ex){
					request.setAttribute("errorMessage", ex.getMessage());
					
					//return to login page:
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
					dispatcher.forward(request, response);
				} 
			} 
			else if (request.getParameter("createNewAccount") != null)
			{
				System.out.println("Request: Create New Account");
				
				try{
					HibernateToDoListDAO.getInstance().addUser(user);
					User authorizedUser = HibernateToDoListDAO.getInstance().getUser(user.getName());
					if (authorizedUser!=null){
						request.getSession().setAttribute("userData",authorizedUser);
						System.out.println("Login was successful. username: " + authorizedUser.getName());
				       
						response.sendRedirect("/ToDoList/MyToDoItems");
						return;
					}
					else
					{
						System.out.println("Login failed: Unknown reason");
						//return to login page:
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
						dispatcher.forward(request, response);
					}
				}
				catch(ToDoListPlatformException e)
				{
					try{
						if(HibernateToDoListDAO.getInstance().getUser(user.getName())!=null){
							System.out.println("Login failed: User with the same name already exist");
							request.setAttribute("errorMessage","User with the same name already exist");
						}
					}
					catch(ToDoListPlatformException ex)
					{
						System.out.println("Login failed: " + ex.getMessage());
						request.setAttribute("errorMessage", "Login failed: DataBase Error");
					}
				}
				
				//return to login page:
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.jsp");
				dispatcher.forward(request, response);
			}
			else
			{
				doGet(request,response);
			}
		}//else
	}
}
