package controller.command.general;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import controller.command.CommandConstant;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that provides logout
 */
public class LogoutCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		//session.setAttribute(CommandConstant.SESSION_USER_ATTR, null);
		session.invalidate();
		return PagesPath.HOME_PAGE;
	}

}
