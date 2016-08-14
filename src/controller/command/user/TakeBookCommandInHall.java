package controller.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import controller.command.CommandConstant;
import model.dao.DaoFactory;
import model.entities.Copy;
import model.entities.Reader;
import model.logic.OperationWithBooks;
import model.service.CopyService;
import view.PagesPath;
import view.View;

/**
 * @author Artem Pryzhkov
 * Command that provides operation giving ordered Book to Reader in hall
 */
public class TakeBookCommandInHall implements Command{
	CopyService copyService=CopyService.getInstance();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long bookId = Long.parseLong(request.getParameter(CommandConstant.PARAMETER_BOOK_ID));
		HttpSession session = request.getSession(true);
		long readerId=Long.parseLong((String)session.getAttribute(CommandConstant.SESSION_USER_ATTR));
		
		Copy copy=OperationWithBooks.takeAvailableCopyInHall(bookId);
		OperationWithBooks.distribute(copy, readerId);
		request.setAttribute(CommandConstant.OPERATION_SUCCESS, true);
		return PagesPath.USER_PAGE;
	}
}
