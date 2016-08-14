package controller.command.general;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import controller.command.CommandConstant;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that provides localization
 */
public class LocalizationCommand implements Command{
	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResourceBundle bundle = null;
		HttpSession session = request.getSession(true);
		String language = (String) request.getParameter(CommandConstant.PARAMETER_LOCALE);
		if (language.equals(CommandConstant.ENG)) {
			session.setAttribute(CommandConstant.LANG,CommandConstant.EN_US);
		} else {
			session.setAttribute(CommandConstant.LANG, CommandConstant.UK_UA);
		}
		
		return PagesPath.HOME_PAGE;
	}
}
