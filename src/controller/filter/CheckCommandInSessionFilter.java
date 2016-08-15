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
import view.PagesPath;
import view.View;

/**
 * Servlet Filter implementation class CheckCommandInSessionFilter
 */
@WebFilter("/CheckCommandInSessionFilter")
public class CheckCommandInSessionFilter implements Filter {


	/**
	 * Default constructor.
	 */
	public CheckCommandInSessionFilter() {
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
		List<CommandList> adminCommands = new ArrayList<>();
		adminCommands.add(CommandList.FIND_OVERDUE_COPIES);
		adminCommands.add(CommandList.GIVE_BOOK_IN_ARMS);
		adminCommands.add(CommandList.GIVE_BOOK_IN_HALL);
		adminCommands.add(CommandList.SHOW_ORDER_LIST);
		
		List<CommandList> userCommands = new ArrayList<>();
		userCommands.add(CommandList.BRING_BACK);
		userCommands.add(CommandList.FIND_BOOK_BY_AUTHOR);
		userCommands.add(CommandList.FIND_BOOK_BY_CATALOG);
		userCommands.add(CommandList.FIND_BOOK_BY_NAME);
		userCommands.add(CommandList.ORDER_BOOK);
		userCommands.add(CommandList.READER_BOOKS);
		userCommands.add(CommandList.TAKE_BOOK_IN_ARMS);
		userCommands.add(CommandList.TAKE_BOOK_IN_HALL);

		String commandName=request.getParameter(View.COMMAND);
		if (((HttpServletRequest) request).getSession()
				.getAttribute(CommandConstant.SESSION_USER_ATTR) == CommandConstant.PARAMETER_ADMIN) {
			if(!adminCommands.contains(commandName)){
				HttpServletResponse resp = (HttpServletResponse) response;
				String redirect = ((HttpServletRequest) request).getContextPath() + PagesPath.HOME_PAGE;
				resp.sendRedirect(redirect);
				return;
			}	
		}else if (((HttpServletRequest) request).getSession()
				.getAttribute(CommandConstant.SESSION_USER_ATTR) != null && ((HttpServletRequest) request).getSession()
				.getAttribute(CommandConstant.SESSION_USER_ATTR) != CommandConstant.PARAMETER_ADMIN) {

			if(!userCommands.contains(commandName)){
				HttpServletResponse resp = (HttpServletResponse) response;
				String redirect = ((HttpServletRequest) request).getContextPath() + PagesPath.HOME_PAGE;
				resp.sendRedirect(redirect);
				return;
			}	
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
