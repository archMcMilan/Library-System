package controller.command.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that provides operation go back in the jsp pages
 */
public class GoBackCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		HttpSession session = request.getSession(true);
//		
//		String backPage=(String) session.getAttribute(CommandConstant.PARAMETER_BACK);
//		return backPage;
		return PagesPath.USER_PAGE;
	}

}
