package controller.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.command.Command;
import controller.command.CommandConstant;
import model.service.CopyService;
import view.PagesPath;

/**
 * @author Artem Pryzhkov
 * Command that provides operation bring back book by reader
 */
public class BringBackCommand implements Command{
	CopyService copyService= CopyService.getInstance();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		copyService.bringBack(Long.parseLong(request.getParameter(CommandConstant.PARAMETER_COPY_ID)));
		return PagesPath.READER_BOOKS_PAGE_COMMAND;
	}

}
