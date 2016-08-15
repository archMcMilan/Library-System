package controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.command.CommandConstant;
import controller.command.CommandList;
import sun.tools.jar.CommandLine;
import view.PagesPath;

/**
 * Servlet Filter implementation class CheckUserInSessionFilter
 */
@WebFilter("/CheckUserInSessionFilter")
public class CheckUserInSessionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public CheckUserInSessionFilter() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (((HttpServletRequest) request).getSession()
				.getAttribute(CommandConstant.SESSION_USER_ATTR) == null || ((HttpServletRequest) request).getSession()
				.getAttribute(CommandConstant.SESSION_USER_ATTR) == CommandConstant.PARAMETER_ADMIN) {

			HttpServletResponse resp = (HttpServletResponse) response;
			String redirect = ((HttpServletRequest) request).getContextPath() + PagesPath.HOME_PAGE;
			resp.sendRedirect(redirect);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
