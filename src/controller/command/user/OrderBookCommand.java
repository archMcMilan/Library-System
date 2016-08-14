package controller.command.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.command.Command;
import controller.command.CommandConstant;
import model.dao.DaoFactory;
import model.entities.Reader;
import view.PagesPath;
import view.View;

/**
 * @author Artem Pryzhkov
 * Command that provides operation ordering Book by Reader
 */
public class OrderBookCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long book_id = Long.parseLong(request.getParameter(CommandConstant.PARAMETER_BOOK_ID));
		HttpSession session = request.getSession(true);
		long readerId=Long.parseLong((String)session.getAttribute(CommandConstant.SESSION_USER_ATTR));
		
		Reader reader=DaoFactory.getFactory().createReaderDao().find(readerId);
		reader.addBookInOrder(DaoFactory.getFactory().createBookDao().find(book_id));
		DaoFactory.getFactory().createReaderDao().updateOrderBooks(reader);
		request.setAttribute(CommandConstant.OPERATION_SUCCESS, true);
		return PagesPath.USER_PAGE;
	}
}
