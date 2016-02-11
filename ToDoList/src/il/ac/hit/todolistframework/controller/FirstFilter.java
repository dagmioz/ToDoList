package il.ac.hit.todolistframework.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class FirstFilter
 */
@WebFilter("/*")
public class FirstFilter implements Filter {
	/*
	 * resources:
	 * as suggested here: http://www.avajava.com/tutorials/lessons/what-is-a-filter-and-how-do-i-use-it.html?page=1
	 * and here: http://stackoverflow.com/questions/870150/how-to-access-static-resources-when-mapping-a-global-front-controller-servlet-on
	 * and here: http://stackoverflow.com/questions/13934909/how-to-let-servlet-mapping-to 
	 */
	
    /**
     * Default constructor. 
     */
    public FirstFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		 * when accessing the ToDoList web application this is the first function that processing the request
		 * and is redirecting the request and response objects to the servlets,
		 * here we use the front controller design pattern.
		 * 
		 * without this filter we encountered a bug when all the requests (/*) were going to the front controller
		 * and wherever a page was dispached (as oppose to redirected to) that caused an unending loop:
		 * request > handle the request > dispatch (same url) > same request > same handling process > dispatch and so on....
		 */
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());

		if (path.startsWith("/static")) {
		    chain.doFilter(request, response); // Goes to default servlet.
		} else {
		    request.getRequestDispatcher("/pages" + path).forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
