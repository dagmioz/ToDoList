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
 * as suggested here: http://www.avajava.com/tutorials/lessons/what-is-a-filter-and-how-do-i-use-it.html?page=1
 * and here: http://stackoverflow.com/questions/870150/how-to-access-static-resources-when-mapping-a-global-front-controller-servlet-on
 * and here: http://stackoverflow.com/questions/13934909/how-to-let-servlet-mapping-to
 */
@WebFilter("/*")
public class FirstFilter implements Filter {

    /**
     * Default constructor. 
     */
    public FirstFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(req.getContextPath().length());

		if (path.startsWith("/static")) {
		    chain.doFilter(request, response); // Goes to default servlet.
		} else {
		    request.getRequestDispatcher("/pages" + path).forward(request, response);
		}
		// pass the request along the filter chain
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
